package com.dandandog.blog.security.service;

import com.dandandog.blog.modules.system.entity.AuthUser;
import com.dandandog.blog.modules.system.service.AuthUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/8/24 17:40
 */
@Service
public class AuthorizedService implements UserDetailsService {

    @Resource
    private AuthUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser user = userService.lambdaQuery().eq(AuthUser::getUsername, username)
                .oneOpt().orElseThrow(() -> new UsernameNotFoundException("username not found"));
        userService.findUserAuthorities(user);
        return user;
    }
}
