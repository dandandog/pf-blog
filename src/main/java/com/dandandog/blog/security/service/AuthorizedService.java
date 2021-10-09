package com.dandandog.blog.security.service;

import cn.hutool.core.collection.CollUtil;
import com.dandandog.blog.modules.admin.auth.entity.AuthResource;
import com.dandandog.blog.modules.admin.auth.entity.AuthRoleResource;
import com.dandandog.blog.modules.admin.auth.entity.AuthUser;
import com.dandandog.blog.modules.admin.auth.entity.AuthUserRole;
import com.dandandog.blog.modules.admin.auth.service.AuthResourceService;
import com.dandandog.blog.modules.admin.auth.service.AuthRoleResourceService;
import com.dandandog.blog.modules.admin.auth.service.AuthUserRoleService;
import com.dandandog.blog.modules.admin.auth.service.AuthUserService;
import com.dandandog.blog.security.mapper.UserCredentials;
import com.dandandog.blog.security.utils.SessionUtil;
import com.dandandog.framework.core.entity.BaseEntity;
import com.dandandog.framework.mapstruct.MapperUtil;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/8/24 17:40
 */
@Service
public class AuthorizedService implements UserDetailsService {

    @Resource
    private AuthUserService userService;

    @Resource
    private AuthUserRoleService roleService;

    @Resource
    private AuthResourceService resourceService;

    @Resource
    private AuthRoleResourceService roleResourceService;

    @Resource
    private SessionRegistry sessionRegistry;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findUserAuthorities(username);
    }

    public void reloadUserRole() throws UsernameNotFoundException {
        Iterator<HttpSession> it = SessionUtil.getAllSessions();
        while (it.hasNext()) {
            HttpSession session = it.next();
            SessionInformation sessionInformation = sessionRegistry.getSessionInformation(session.getId());
            UserCredentials old = (UserCredentials) sessionInformation.getPrincipal();
            UserCredentials user = findUserAuthorities(old.getUsername());
            SessionUtil.refreshSession(session, user);
        }
    }

    public UserCredentials findUserAuthorities(String username) {
        AuthUser authUser = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("username not found"));

        UserCredentials user = MapperUtil.map(authUser, UserCredentials.class);
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

    private String findUserType(UserCredentials user) {
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
