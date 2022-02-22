package com.dandandog.modules.auth.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dandandog.modules.auth.entity.AuthUser;

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

    IPage<AuthUser> pageJoinByRole(Page<AuthUser> page, Wrapper<AuthUser> wrapper);

}