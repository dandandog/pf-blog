package com.dandandog.blog.web.admin.faces;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.dandandog.blog.web.admin.faces.vo.AuthRoleVo;
import com.dandandog.framework.faces.annotation.Faces;
import com.dandandog.framework.mapstruct.context.BaseContext;
import com.dandandog.framework.mapstruct.utils.MapperUtil;
import com.dandandog.framework.mybatis.entity.AuditableEntity;
import com.dandandog.modules.auth.entity.AuthResource;
import com.dandandog.modules.auth.entity.AuthRole;
import com.dandandog.modules.auth.entity.AuthRoleResource;
import com.dandandog.modules.auth.service.AuthResourceService;
import com.dandandog.modules.auth.service.AuthRoleResourceService;
import com.dandandog.modules.auth.service.AuthRoleService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
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
        List<AuthResource> resources = vo.getResources();
        roleService.saveOrUpdate(entity);
        roleResourceService.lambdaUpdate().eq(AuthRoleResource::getRoleId, entity.getId()).remove();
        List<AuthRoleResource> roleResources = CollUtil.emptyIfNull(resources).stream()
                // 有权限的才保存到数据库中，主要是解决在 tree 上显示的问题
                .filter(resource -> StringUtils.isNotBlank(resource.getPerms()))
                .map(resource -> new AuthRoleResource(entity.getId(), entity.getCode(), resource.getId()))
                .collect(Collectors.toList());
        roleResourceService.saveBatch(roleResources);

    }

    public void removeByIds(String[] ids) {
        roleService.removeByIds(Arrays.asList(ids));
    }

    private BaseContext<AuthRoleVo> voDetail() {
        return new BaseContext<AuthRoleVo>() {
            @Override
            protected void after(AuthRoleVo target, Class<AuthRoleVo> t) {
                List<AuthResource> resources = resourceService.findByRole(target.getId());
                target.setResources(resources);
            }
        };
    }


}
