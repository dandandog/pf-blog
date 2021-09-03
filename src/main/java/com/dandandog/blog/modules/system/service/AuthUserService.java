package com.dandandog.blog.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dandandog.blog.modules.system.entity.AuthRole;
import com.dandandog.blog.modules.system.entity.AuthUser;
import com.google.common.collect.Multimap;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

/**
 * 系统用户表(AuthUser)表服务接口
 *
 * @author JohnnyLiu
 * @since 2020-09-06 22:06:06
 */
//
public interface AuthUserService extends IService<AuthUser> {

    Optional<AuthUser> findByUsername(String username);

}