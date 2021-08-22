package com.dandandog.blog.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dandandog.blog.modules.system.entity.AuthRole;
import com.dandandog.blog.modules.system.entity.AuthUser;
import com.google.common.collect.Multimap;

import java.util.List;
import java.util.Optional;

/**
 * 系统用户表(AuthUser)表服务接口
 *
 * @author JohnnyLiu
 * @since 2020-09-06 22:06:06
 */
//UserDetailsService
public interface AuthUserService extends IService<AuthUser> {

    List<String> findRoleByUser(String userId);

    void save(AuthUser user, List<AuthRole> roles);

    Optional<AuthUser> findByEmail(String email);

    Multimap<String, String> loadUserRole();

    void reloadUserRole();

    void findUserAuthorities(AuthUser user);

    void sendEmailByUser(AuthUser user, String email, String subject, String text);
}