package com.dandandog.blog.web.admin.faces;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import com.dandandog.blog.web.admin.faces.vo.AuthUserVo;
import com.dandandog.framework.faces.annotation.Faces;
import com.dandandog.framework.mapstruct.context.BaseContext;
import com.dandandog.framework.mapstruct.utils.MapperUtil;
import com.dandandog.modules.auth.entity.AuthRole;
import com.dandandog.modules.auth.entity.AuthUser;
import com.dandandog.modules.auth.entity.AuthUserRole;
import com.dandandog.modules.auth.service.AuthRoleService;
import com.dandandog.modules.auth.service.AuthUserRoleService;
import com.dandandog.modules.auth.service.AuthUserService;
import com.dandandog.modules.blog.entity.BlogPersonal;
import com.dandandog.modules.blog.service.BlogPersonalService;
import org.primefaces.model.DualListModel;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/8/27 11:11
 */
@Faces
public class AuthUserFaces {

    @Resource
    private AuthUserService userService;

    @Resource
    private AuthRoleService roleService;

    @Resource
    private AuthUserRoleService userRoleService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private BlogPersonalService personalService;

    public Optional<AuthUserVo> getOptById(String id) {
        return Optional.ofNullable(userService.getById(id)).map(entity -> MapperUtil.map(entity, AuthUserVo.class, info()));
    }

    public Optional<AuthUserVo> getOptByUserName(String username) {
        return userService.findByUsername(username).map(entity -> MapperUtil.map(entity, AuthUserVo.class));
    }

    public void removeByIds(String[] ids) {
        userService.removeByIds(Arrays.asList(ids));
    }

    @Transactional
    public void saveOrUpdate(AuthUserVo vo) {
        AuthUser entity = MapperUtil.map(vo, AuthUser.class);
        userService.saveOrUpdate(entity);
        List<AuthRole> roles = vo.getRoles().getTarget();
        userRoleService.lambdaUpdate().eq(AuthUserRole::getUserId, entity.getId()).remove();
        List<AuthUserRole> userRoles = CollUtil.emptyIfNull(roles).stream()
                .map(role -> new AuthUserRole(entity.getId(), role.getId(), role.getCode()))
                .collect(Collectors.toList());
        userRoleService.saveBatch(userRoles);
    }

    private BaseContext<AuthUserVo> info() {
        return new BaseContext<AuthUserVo>() {
            @Override
            protected void after(AuthUserVo vo, Class<AuthUserVo> t) {
                List<AuthRole> source = roleService.list();
                List<AuthRole> target = roleService.findByUser(vo.getId());
                source.removeAll(target);
                vo.setRoles(new DualListModel<>(source, target));
            }
        };
    }

    @Transactional
    public void register(AuthUser user) {
        user.setSalt(RandomUtil.randomString(6));
        user.setPassword(passwordEncoder.encode(user.getPassword() + user.getSalt()));
        user.setPwdRestTime(LocalDateTime.now());
        userService.save(user);
        BlogPersonal personal = new BlogPersonal(user.getUsername());
        personalService.save(personal);
    }

    public int count() {
        return userService.count();
    }
}
