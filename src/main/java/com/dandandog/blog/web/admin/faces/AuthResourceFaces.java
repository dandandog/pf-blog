package com.dandandog.blog.web.admin.faces;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.dandandog.blog.web.admin.faces.adapter.AuthResourceTreeAdapter;
import com.dandandog.blog.web.admin.faces.vo.AuthResourceVo;
import com.dandandog.common.model.MapperTreeDataModel;
import com.dandandog.framework.faces.annotation.Faces;
import com.dandandog.framework.faces.model.tree.TreeDataModel;
import com.dandandog.framework.faces.model.tree.TreeNodeState;
import com.dandandog.framework.mapstruct.utils.MapperUtil;
import com.dandandog.framework.mybatis.entity.BaseEntity;
import com.dandandog.modules.auth.entity.AuthResource;
import com.dandandog.modules.auth.entity.enums.ResourceType;
import com.dandandog.modules.auth.service.AuthResourceService;
import org.primefaces.model.TreeNode;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;


/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/1 9:44
 */
@Faces
public class AuthResourceFaces {

    @Resource
    private AuthResourceService resourceService;


    public TreeDataModel findDataModel() {
        return MapperTreeDataModel.getInstance(new AuthResourceTreeAdapter(), AuthResourceVo.class);
    }

    public TreeNode initNodeTree(TreeDataModel dataModel, TreeNodeState state) {
        if (dataModel == null) {
            dataModel = findDataModel();
        }
        return dataModel.createRoot(state);
    }

    public Optional<AuthResourceVo> getOptById(String id) {
        return Optional.ofNullable(resourceService.getById(id)).map(entity -> MapperUtil.map(entity, AuthResourceVo.class));
    }


    @Transactional
    public void saveOrUpdate(AuthResourceVo vo) {
        AuthResource entity = MapperUtil.map(vo, AuthResource.class);
        TreeNode parent = vo.getParent();
        if (entity.getId() == null) {
            if (parent == null) {
                long count = resourceService.lambdaQuery().isNull(AuthResource::getParentId).count();
                entity.setLevel(Long.toString(count));
            } else {
                long count = resourceService.lambdaQuery().likeRight(AuthResource::getLevel, parent.getRowKey()).count();
                entity.setLevel(StrUtil.join(StrUtil.UNDERLINE, parent.getRowKey(), count));
            }
        }
        resourceService.saveOrUpdate(entity);
    }

    public void removeByIds(String[] ids) {
        resourceService.removeByIds(Arrays.asList(ids));
    }

    public void remove(AuthResourceVo[] vos) {
        for (AuthResourceVo vo : vos) {
            resourceService.lambdaUpdate().eq(AuthResource::getType, vo.getType())
                    .likeRight(AuthResource::getLevel, vo.getLevel()).remove();
        }
    }

    public void updateByFiled(String id, String field, Object newValue) {
        resourceService.update(new UpdateWrapper<AuthResource>().set(field, newValue).eq("id", id));
    }

    public Collection<AuthResourceVo> findButton(TreeNode selectedNode) {
        AuthResourceVo node = (AuthResourceVo) selectedNode.getData();
        List<AuthResource> list = resourceService.lambdaQuery().eq(AuthResource::getParentId, node.getId())
                .eq(AuthResource::getType, ResourceType.BUTTON).orderByAsc(AuthResource::getLevel).list();
        Collection<AuthResourceVo> authResourceVos = MapperUtil.mapToAll(list, AuthResourceVo.class);
        updateBySort(authResourceVos, selectedNode.getRowKey());
        return authResourceVos;
    }

    public void updateBySort(Collection<AuthResourceVo> list, String level) {
        for (int i = 0; i < list.size(); i++) {
            AuthResourceVo vo = CollUtil.get(list, i);
            String index = StrUtil.join(StrUtil.UNDERLINE, level, i);
            if (!Objects.equals(index, vo.getLevel())) {
                vo.setLevel(index);
                resourceService.lambdaUpdate().set(AuthResource::getLevel, index).eq(BaseEntity::getId, vo.getId()).update();
            }
        }
    }
}


