package com.dandandog.modules.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dandandog.framework.mybatis.service.BaseServiceImpl;
import com.dandandog.modules.auth.dao.AuthUserDao;
import com.dandandog.modules.auth.entity.AuthUser;
import com.dandandog.modules.auth.service.AuthUserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 系统用户表(AuthUser)表服务实现类
 *
 * @author JohnnyLiu
 * @since 2020-09-06 22:06:06
 */
@Service
public class AuthUserServiceImpl extends BaseServiceImpl<AuthUserDao, AuthUser> implements AuthUserService {

    @Override
    public Optional<AuthUser> findByUsername(String username) {
        return lambdaQuery().eq(AuthUser::getUsername, username).oneOpt();
    }

    @Override
    public IPage<AuthUser> pageJoinByRole(Page<AuthUser> page, Wrapper<AuthUser> wrapper) {
        return baseMapper.pageJoinByRole(page, wrapper);
    }

}