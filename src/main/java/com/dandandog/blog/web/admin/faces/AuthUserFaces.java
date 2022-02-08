package com.dandandog.blog.web.admin.faces;

import cn.hutool.core.util.RandomUtil;
import com.dandandog.blog.web.admin.faces.adapter.AuthUserPageAdapter;
import com.dandandog.blog.web.admin.faces.vo.AuthUserVo;
import com.dandandog.common.model.MapperPageDataModel;
import com.dandandog.framework.faces.annotation.Faces;
import com.dandandog.framework.mapstruct.context.BaseContext;
import com.dandandog.framework.mapstruct.utils.MapperUtil;
import com.dandandog.modules.auth.entity.AuthRole;
import com.dandandog.modules.auth.entity.AuthUser;
import com.dandandog.modules.auth.service.AuthRoleService;
import com.dandandog.modules.auth.service.AuthUserRoleService;
import com.dandandog.modules.auth.service.AuthUserService;
import com.dandandog.modules.blog.entity.BlogPersonal;
import com.dandandog.modules.blog.service.BlogPersonalService;
import org.primefaces.model.DualListModel;
import org.primefaces.model.LazyDataModel;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

    public LazyDataModel<AuthUserVo> page() {
        return MapperPageDataModel.getInstance(new AuthUserPageAdapter(), AuthUserVo.class, pageInfo());
    }

    public Optional<AuthUserVo> getOptById(String id) {
        return Optional.ofNullable(userService.getById(id)).map(entity -> MapperUtil.map(entity, AuthUserVo.class, pageInfo()));
    }

    public Optional<AuthUserVo> getOptByUserName(String username) {
        return userService.findByUsername(username).map(entity -> MapperUtil.map(entity, AuthUserVo.class));
    }

    public void removeByIds(String[] ids) {
        userService.removeByIds(Arrays.asList(ids));
    }

    public BaseContext<AuthUserVo> pageInfo() {
        return new BaseContext<AuthUserVo>() {
            @Override
            public void after(AuthUserVo target, Class<AuthUserVo> t) {
                BlogPersonal one = personalService.lambdaQuery().eq(BlogPersonal::getUsername, target.getUsername()).one();
                MapperUtil.mergeTo(target, one);
            }
        };
    }

    @Transactional
    public void saveOrUpdate(AuthUserVo vo) {
        AuthUser userEntity = MapperUtil.map(vo, AuthUser.class);
        userService.saveOrUpdate(userEntity);

        BlogPersonal personalEntity = MapperUtil.map(vo, BlogPersonal.class);
        personalService.lambdaUpdate().eq(BlogPersonal::getUsername, userEntity.getUsername()).update(personalEntity);

//        List<AuthRole> roles = vo.getRoles().getTarget();
//        userRoleService.lambdaUpdate().eq(AuthUserRole::getUserId, entity.getId()).remove();
//        List<AuthUserRole> userRoles = CollUtil.emptyIfNull(roles).stream()
//                .map(role -> new AuthUserRole(entity.getId(), role.getId(), role.getCode()))
//                .collect(Collectors.toList());
//        userRoleService.saveBatch(userRoles);
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

    public long count() {
        return userService.count();
    }
}
