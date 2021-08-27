package com.dandandog.blog.modules.system.web.facet;

import cn.hutool.core.collection.CollUtil;
import com.dandandog.blog.modules.system.entity.AuthRole;
import com.dandandog.blog.modules.system.entity.AuthUser;
import com.dandandog.blog.modules.system.entity.AuthUserRole;
import com.dandandog.blog.modules.system.service.AuthRoleService;
import com.dandandog.blog.modules.system.service.AuthUserRoleService;
import com.dandandog.blog.modules.system.service.AuthUserService;
import com.dandandog.blog.modules.system.web.facet.vo.AuthUserVo;
import com.dandandog.framework.core.annotation.Facet;
import com.dandandog.framework.mapstruct.MapperUtil;
import com.dandandog.framework.mapstruct.context.BaseContext;
import org.primefaces.model.DualListModel;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/8/27 11:11
 */
@Facet
public class AuthUserFaces {

    @Resource
    private AuthUserService userService;

    @Resource
    private AuthRoleService roleService;

    @Resource
    private AuthUserRoleService userRoleService;


    public Optional<AuthUserVo> getOptById(String id) {
        return Optional.ofNullable(userService.getById(id)).map(entity -> MapperUtil.map(entity, AuthUserVo.class, info()));
    }

    public void removeByIds(String[] ids) {
        userService.removeByIds(Arrays.asList(ids));
    }

    @Transactional
    public void saveOrUpdate(AuthUserVo vo) {
        AuthUser entity = MapperUtil.map(vo, AuthUser.class);
        List<AuthRole> roles = vo.getRoles().getTarget();
        if (userService.saveOrUpdate(entity)) {
            userRoleService.lambdaUpdate().eq(AuthUserRole::getUserId, entity.getId()).remove();
            List<AuthUserRole> userRoles = CollUtil.emptyIfNull(roles).stream()
                    .map(role -> new AuthUserRole(entity.getId(), role.getId(), role.getCode()))
                    .collect(Collectors.toList());
            userRoleService.saveBatch(userRoles);
        }
    }

    private BaseContext<AuthUserVo> info() {
        return new BaseContext<AuthUserVo>() {
            @Override
            protected void after(AuthUserVo vo, Class<AuthUserVo> t) {
                List<AuthRole> source = roleService.list();
                List<AuthRole> target = roleService.findByUser(vo.getId());
                vo.setRoles(new DualListModel<>(source, target));
            }
        };
    }


}
