package com.dandandog.blog.modules.system.auth.service.impl;

import com.dandandog.blog.modules.system.auth.dao.AuthUserDao;
import com.dandandog.blog.modules.system.auth.entity.AuthUser;
import com.dandandog.blog.modules.system.auth.service.AuthResourceService;
import com.dandandog.blog.modules.system.auth.service.AuthUserRoleService;
import com.dandandog.blog.modules.system.auth.service.AuthUserService;
import com.dandandog.framework.core.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

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

    @Override
    public Optional<AuthUser> findByUsername(String username) {
        return lambdaQuery().eq(AuthUser::getUsername, username).oneOpt();
    }

//
//    public List<String> findRoleByUser(String userId) {
//        List<AuthUserRole> list = roleService.list(new LambdaQueryWrapper<AuthUserRole>().eq(AuthUserRole::getUserId, userId));
//        return list.stream().map(AuthUserRole::getRoleId).collect(Collectors.toList());
//    }
//
//    public Multimap<String, String> loadUserRole() {
//        List<AuthUserRole> roles = roleService.list();
//        Multimap<String, String> result = ArrayListMultimap.create();
//        roles.forEach(role -> {
//            resourceService.findByRole(role.getRoleId()).stream()
//                    .filter(resource -> StrUtil.isNotBlank(resource.getPath()) && !StrUtil.equals(resource.getPath(), "#"))
//                    .forEach(resource -> {
//                        result.put(resource.getPerms(), resource.getPath());
//                    });
//        });
//        return result;
//    }
}