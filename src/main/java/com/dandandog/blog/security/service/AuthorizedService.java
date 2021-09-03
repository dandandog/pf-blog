package com.dandandog.blog.security.service;

import cn.hutool.core.collection.CollUtil;
import com.dandandog.blog.common.view.MenuView;
import com.dandandog.blog.modules.system.entity.AuthResource;
import com.dandandog.blog.modules.system.entity.AuthRoleResource;
import com.dandandog.blog.modules.system.entity.AuthUser;
import com.dandandog.blog.modules.system.entity.AuthUserRole;
import com.dandandog.blog.modules.system.service.AuthResourceService;
import com.dandandog.blog.modules.system.service.AuthRoleResourceService;
import com.dandandog.blog.modules.system.service.AuthUserRoleService;
import com.dandandog.blog.modules.system.service.AuthUserService;
import com.dandandog.blog.security.utils.SessionUtil;
import com.dandandog.framework.common.utils.SpringContextUtil;
import com.dandandog.framework.core.entity.BaseEntity;
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
        AuthUser authUser = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("username not found"));
        return findUserAuthorities(authUser);
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
