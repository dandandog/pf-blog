package com.dandandog.blog.modules.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.mail.MailUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dandandog.blog.modules.system.dao.AuthUserDao;
import com.dandandog.blog.modules.system.entity.*;
import com.dandandog.blog.modules.system.entity.enums.UserType;
import com.dandandog.blog.modules.system.service.AuthResourceService;
import com.dandandog.blog.modules.system.service.AuthRoleResourceService;
import com.dandandog.blog.modules.system.service.AuthUserRoleService;
import com.dandandog.blog.modules.system.service.AuthUserService;
import com.dandandog.blog.security.utils.SessionUtil;
import com.dandandog.framework.core.entity.BaseEntity;
import com.dandandog.framework.core.service.impl.BaseServiceImpl;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 系统用户表(AuthUser)表服务实现类
 *
 * @author JohnnyLiu
 * @since 2020-09-06 22:06:06
 */
@Service
public class AuthUserServiceImpl extends BaseServiceImpl<AuthUserDao, AuthUser> implements AuthUserService {

    @Resource
    private AuthUserRoleService roleService;

    @Resource
    private AuthResourceService resourceService;

    @Resource
    private AuthRoleResourceService roleResourceService;

    @Resource
    private SessionRegistry sessionRegistry;

    @Override
    public List<String> findRoleByUser(String userId) {
        List<AuthUserRole> list = roleService.list(new LambdaQueryWrapper<AuthUserRole>().eq(AuthUserRole::getUserId, userId));
        return list.stream().map(AuthUserRole::getRoleId).collect(Collectors.toList());
    }

    @Override
    public Optional<AuthUser> findByEmail(String email) {
        return lambdaQuery().eq(AuthUser::getEmail, email).oneOpt();
    }


    @Override
    public Optional<AuthUser> findByUsername(String username) {
        Optional<AuthUser> authUser = lambdaQuery().eq(AuthUser::getUsername, username)
                .oneOpt();
        return authUser.map(this::findUserAuthorities);
    }

    @Override
    public Multimap<String, String> loadUserRole() {
        List<AuthUserRole> roles = roleService.list();
        Multimap<String, String> result = ArrayListMultimap.create();
        roles.forEach(userRole -> {
            resourceService.findByRole(userRole.getRoleId()).stream()
                    .filter(resource -> StrUtil.isNotBlank(resource.getPath()) && !StrUtil.equals(resource.getPath(), "#"))
                    .forEach(resource -> {
                        result.put(resource.getPerms(), resource.getPath());
                    });
        });
        return result;
    }

    @Override
    public AuthUser findUserAuthorities(AuthUser user) {
        List<AuthUserRole> userRoles = roleService.lambdaQuery().eq(AuthUserRole::getUserId, user.getId()).list();

        String userType = findUserType(user);
        Collection<GrantedAuthority> type = AuthorityUtils.commaSeparatedStringToAuthorityList(userType);
        user.getAuthorities().addAll(type);

        String roleArr = findUserRoles(userRoles);
        Collection<GrantedAuthority> roles = AuthorityUtils.commaSeparatedStringToAuthorityList(roleArr);
        user.getAuthorities().addAll(roles);

        String rolePermArr = findRolePerms(userRoles);
        Collection<GrantedAuthority> perms = AuthorityUtils.commaSeparatedStringToAuthorityList(rolePermArr);
        user.getAuthorities().addAll(perms);

        return user;
    }

    @Override
    @Async
    public void sendEmailByUser(AuthUser user, String email, String subject, String text) {
        String code = RandomUtil.randomString(6);
        LocalDateTime expired = LocalDateTime.now().plus(5, ChronoUnit.MINUTES);
        this.lambdaUpdate().set(AuthUser::getSalt, code)
                .set(AuthUser::getExpiredTime, expired).eq(BaseEntity::getId, user.getId()).update();
        MailUtil.send(email, subject, text + "?code=" + code, false);
    }

    public void reloadUserRole() {
        Iterator<HttpSession> it = SessionUtil.getAllSessions();
        while (it.hasNext()) {
            HttpSession session = it.next();
            SessionInformation sessionInformation = sessionRegistry.getSessionInformation(session.getId());
            AuthUser user = findUserAuthorities((AuthUser) sessionInformation.getPrincipal());
            SessionUtil.refreshSession(session, user);
        }
    }

    private String findUserType(AuthUser user) {
        return "ROLE_" + user.getType().name();
    }

    private String findUserRoles(List<AuthUserRole> userRoles) {
        return userRoles.stream().map(userRole -> "ROLE_" + userRole.getRoleCode()).collect(Collectors.joining(","));
    }

    private String findRolePerms(List<AuthUserRole> userRoles) {
        return userRoles.stream().flatMap(userRole -> {
            List<AuthRoleResource> list = roleResourceService.lambdaQuery().eq(AuthRoleResource::getRoleId, userRole.getRoleId()).list();
            List<String> resIds = list.stream().map(AuthRoleResource::getResId).collect(Collectors.toList());
            return resourceService.lambdaQuery().in(CollUtil.isNotEmpty(resIds), BaseEntity::getId, resIds).list().stream();
        }).map(AuthResource::getPerms).collect(Collectors.joining(","));
    }
}