package com.dandandog.blog.web.admin.faces;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.dandandog.blog.web.admin.faces.adapter.AuthRolePageAdapter;
import com.dandandog.blog.web.admin.faces.vo.AuthResourceVo;
import com.dandandog.blog.web.admin.faces.vo.AuthRoleVo;
import com.dandandog.common.model.MapperPageDataModel;
import com.dandandog.framework.faces.annotation.Faces;
import com.dandandog.framework.mapstruct.context.BaseContext;
import com.dandandog.framework.mapstruct.utils.MapperUtil;
import com.dandandog.framework.mybatis.entity.AuditableEntity;
import com.dandandog.framework.mybatis.entity.BaseEntity;
import com.dandandog.modules.auth.entity.AuthResource;
import com.dandandog.modules.auth.entity.AuthRole;
import com.dandandog.modules.auth.entity.AuthRoleResource;
import com.dandandog.modules.auth.entity.enums.ResourceType;
import com.dandandog.modules.auth.service.AuthResourceService;
import com.dandandog.modules.auth.service.AuthRoleResourceService;
import com.dandandog.modules.auth.service.AuthRoleService;
import com.google.common.collect.Lists;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.TreeNode;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/8/30 16:13
 */
@Faces
public class AuthRoleFaces {

    @Resource
    private AuthRoleService roleService;

    @Resource
    private AuthRoleResourceService roleResourceService;

    @Resource
    private AuthResourceService resourceService;

    public LazyDataModel<AuthRoleVo> findDataModel() {
        return MapperPageDataModel.getInstance(new AuthRolePageAdapter(), AuthRoleVo.class);
    }

    public Collection<AuthRoleVo> list() {
        List<AuthRole> list = roleService.lambdaQuery().orderByDesc(AuditableEntity::getCreatedTime).list();
        return MapperUtil.mapToAll(list, AuthRoleVo.class);
    }

    public Optional<AuthRoleVo> getOptById(String id) {
        return Optional.ofNullable(roleService.getById(id)).map(entity -> MapperUtil.map(entity, AuthRoleVo.class, voDetail()));
    }

    @Transactional
    public void saveOrUpdate(AuthRoleVo vo) {
        AuthRole entity = MapperUtil.map(vo, AuthRole.class);
        roleService.saveOrUpdate(entity);

        roleResourceService.lambdaUpdate().eq(AuthRoleResource::getRoleId, entity.getId()).remove();
        List<AuthRoleResource> source = Lists.newArrayList();
        List<AuthResource> resources = nodeConvertedToEntity(vo.getAccesses());
        source.addAll(CollUtil.emptyIfNull(resources).stream()
                .map(resource -> new AuthRoleResource(entity.getId(), entity.getCode(), resource.getId()))
                .collect(Collectors.toList()));

        source.addAll(Arrays.stream(ArrayUtil.nullToEmpty(vo.getOperates()))
                .map(resourceId -> new AuthRoleResource(entity.getId(), entity.getCode(), resourceId))
                .collect(Collectors.toList()));
        roleResourceService.saveBatch(CollUtil.removeNull(source));
    }

    public void removeByIds(String[] ids) {
        roleService.removeByIds(Arrays.asList(ids));
    }

    private BaseContext<AuthRoleVo> voDetail() {
        return new BaseContext<AuthRoleVo>() {
            @Override
            protected void after(AuthRoleVo target, Class<AuthRoleVo> t) {
                List<AuthResource> resources = resourceService.findByRole(target.getId());
                List<AuthResource> menus = resources.stream().filter(resource -> Objects.equals(resource.getType(), ResourceType.MENU)).collect(Collectors.toList());
                target.setAccesses(entityConvertedToNode(menus));
                List<AuthResource> buttons = resources.stream().filter(resource -> Objects.equals(resource.getType(), ResourceType.BUTTON)).collect(Collectors.toList());
                target.setOperates(entityConvertedToIdStr(buttons));
            }
        };
    }

    private List<AuthResource> nodeConvertedToEntity(TreeNode[] nodes) {
        return Arrays.stream(nodes)
                .map(treeNode -> (AuthResourceVo) treeNode.getData())
                .map(data -> MapperUtil.map(data, AuthResource.class))
                .collect(Collectors.toList());
    }


    private TreeNode[] entityConvertedToNode(List<AuthResource> entities) {
        return entities.stream().map(authResource -> {
            String level = authResource.getLevel();
            Integer[] points = indexOf(level, '_');
            TreeNode root = new DefaultTreeNode();
            for (int i : points) {
                String s = StrUtil.subPre(level, i);
                AuthResource one = resourceService.lambdaQuery().eq(AuthResource::getLevel, s).one();
                root = new DefaultTreeNode(MapperUtil.map(one, AuthResourceVo.class), root);
            }
            AuthResource one = resourceService.lambdaQuery().eq(AuthResource::getLevel, level).one();
            return new DefaultTreeNode(MapperUtil.map(one, AuthResourceVo.class), root);
        }).toArray(TreeNode[]::new);
    }

    private String[] entityConvertedToIdStr(List<AuthResource> entities) {
        return entities.stream().map(BaseEntity::getId).toArray(String[]::new);
    }


    private Integer[] indexOf(String str, char searchChar) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < str.length(); i++) {
            if (Objects.equals(str.charAt(i), searchChar)) {
                result.add(i);
            }
        }
        return result.toArray(new Integer[0]);
    }

    public void updateByFiled(String id, String field, Object newValue) {
        roleService.update(new UpdateWrapper<AuthRole>().set(field, newValue).eq("id", id));
    }

    public List<SelectItem> findOperates(TreeNode[] nodes) {
        List<SelectItem> operates = new ArrayList<>();
        List<AuthResourceVo> target = Arrays.stream(ArrayUtil.defaultIfEmpty(nodes, new TreeNode[0]))
                .map(node -> (AuthResourceVo) node.getData())
                .sorted(Comparator.comparing(AuthResourceVo::getLevel))
                .collect(Collectors.toList());
        for (AuthResourceVo resourceVo : target) {
            List<AuthResource> list = resourceService.lambdaQuery().eq(AuthResource::getType, ResourceType.BUTTON)
                    .eq(AuthResource::getParentId, resourceVo.getId()).orderByAsc(AuthResource::getLevel).list();
            if (CollUtil.isNotEmpty(list)) {
                SelectItemGroup group = new SelectItemGroup(resourceVo.getTitle());
                group.setSelectItems(list.stream().map(resource -> new SelectItem(resource.getId(), resource.getTitle())).toArray(SelectItem[]::new));
                operates.add(group);
            }
        }
        return operates;
    }
}
