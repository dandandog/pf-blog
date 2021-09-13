package com.dandandog.blog.modules.system.auth.service.impl;

import com.dandandog.blog.modules.system.auth.dao.AuthUserDao;
import com.dandandog.blog.modules.system.auth.entity.AuthUser;
import com.dandandog.blog.modules.system.auth.service.AuthUserService;
import com.dandandog.framework.core.service.impl.BaseServiceImpl;
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

}