package com.dandandog.common.config.security.service;

import cn.hutool.core.util.StrUtil;
import com.dandandog.common.config.security.dto.UserCredentials;
import com.dandandog.common.config.security.utils.SessionUtil;
import com.dandandog.framework.mapstruct.utils.MapperUtil;
import com.dandandog.framework.mybatis.entity.AuditableEntity;
import com.dandandog.modules.auth.entity.AuthResource;
import com.dandandog.modules.auth.entity.AuthRole;
import com.dandandog.modules.auth.entity.AuthUser;
import com.dandandog.modules.auth.service.AuthResourceService;
import com.dandandog.modules.auth.service.AuthRoleService;
import com.dandandog.modules.auth.service.AuthUserService;
import com.dandandog.modules.blog.entity.BlogPersonal;
import com.dandandog.modules.blog.service.BlogPersonalService;
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

/**
 * @Author: JohnnyLiu
 * @Date: 2021/8/24 17:40
 */
@Service
public class AuthorizedService implements UserDetailsService {

    @Resource
    private AuthUserService userService;

    @Resource
    private AuthRoleService roleService;

    @Resource
    private AuthResourceService resourceService;

    @Resource
    private SessionRegistry sessionRegistry;

    @Resource
    private BlogPersonalService personalService;

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
        BlogPersonal personal = personalService.lambdaQuery().eq(AuditableEntity::getCreator, authUser.getUsername()).one();
        UserCredentials user = MapperUtil.mergeTo(UserCredentials.class, authUser, personal);

        Collection<GrantedAuthority> type = findUserType(authUser);
        user.getAuthorities().addAll(type);

        Collection<GrantedAuthority> roles = findUserRoles(authUser);
        user.getAuthorities().addAll(roles);

        Collection<GrantedAuthority> perms = findUserPerms(authUser);
        user.getAuthorities().addAll(perms);

        return user;
    }

    private String buildRole(String roleCode) {
        return StrUtil.addPrefixIfNot(roleCode, "ROLE_");
    }

    private Collection<GrantedAuthority> findUserType(AuthUser user) {
        String userType = buildRole(user.getType().name());
        return AuthorityUtils.createAuthorityList(userType);
    }

    private Collection<GrantedAuthority> findUserRoles(AuthUser user) {
        List<AuthRole> roles = roleService.findByUser(user.getId());
        String[] roleArr = roles.stream().map(r -> buildRole(r.getCode())).toArray(String[]::new);
        return AuthorityUtils.createAuthorityList(roleArr);
    }

    private Collection<GrantedAuthority> findUserPerms(AuthUser user) {
        List<AuthResource> resources = resourceService.findByUser(user.getId());
        String[] permArr = resources.stream().map(AuthResource::getPerms).toArray(String[]::new);
        return AuthorityUtils.createAuthorityList(permArr);
    }

}
