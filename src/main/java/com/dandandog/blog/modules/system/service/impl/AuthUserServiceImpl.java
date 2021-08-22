package com.dandandog.blog.modules.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.mail.MailUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dandandog.blog.modules.system.dao.AuthUserDao;
import com.dandandog.blog.modules.system.entity.*;
import com.dandandog.blog.modules.system.service.AuthResourceService;
import com.dandandog.blog.modules.system.service.AuthRoleResourceService;
import com.dandandog.blog.modules.system.service.AuthUserRoleService;
import com.dandandog.blog.modules.system.service.AuthUserService;
import com.dandandog.framework.core.entity.BaseEntity;
import com.dandandog.framework.core.service.impl.BaseServiceImpl;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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

//    @Resource
//    private SessionRegistry sessionRegistry;

    @Override
    public List<String> findRoleByUser(String userId) {
        List<AuthUserRole> list = roleService.list(new LambdaQueryWrapper<AuthUserRole>().eq(AuthUserRole::getUserId, userId));
        return list.stream().map(AuthUserRole::getRoleId).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(AuthUser user, List<AuthRole> roles) {
        if (saveOrUpdate(user)) {
            roleService.lambdaUpdate().eq(AuthUserRole::getUserId, user.getId()).remove();
            List<AuthUserRole> userRoles = CollUtil.emptyIfNull(roles).stream()
                    .map(role -> new AuthUserRole(user.getId(), role.getId(), role.getCode()))
                    .collect(Collectors.toList());
            roleService.saveBatch(userRoles);
        }
    }

    @Override
    public Optional<AuthUser> findByEmail(String email) {
        return lambdaQuery().eq(AuthUser::getEmail, email).oneOpt();
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

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        AuthUser user = lambdaQuery().eq(AuthUser::getUsername, username)
//                .oneOpt().orElseThrow(() -> new UsernameNotFoundException("username not found"));
//        findUserAuthorities(user);
//        return user;
//    }

    @Override
    public void findUserAuthorities(AuthUser user) {
//        List<AuthUserRole> userRoles = roleService.lambdaQuery().eq(AuthUserRole::getUserId, user.getId()).list();
//        List<GrantedAuthority> authorities = Lists.newArrayList();
//
//        String roleArr = findUserRoles(userRoles);
//        List<GrantedAuthority> roles = AuthorityUtils.commaSeparatedStringToAuthorityList(roleArr);
//        authorities.addAll(roles);
//
//        String rolePermArr = findRolePerms(userRoles);
//        List<GrantedAuthority> perms = AuthorityUtils.commaSeparatedStringToAuthorityList(rolePermArr);
//        authorities.addAll(perms);
//
//        user.setAuthorities(authorities);
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
//        Iterator<HttpSession> it = SessionUtil.getAllSessions();
//        while (it.hasNext()) {
//            HttpSession session = it.next();
//            SessionInformation sessionInformation = sessionRegistry.getSessionInformation(session.getId());
//            AuthUser user = (AuthUser) sessionInformation.getPrincipal();
//            findUserAuthorities(user);
//            SessionUtil.refreshSession(session, user);
//        }
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