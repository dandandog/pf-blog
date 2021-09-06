package com.dandandog.blog.modules;

import cn.hutool.core.util.StrUtil;
import com.dandandog.blog.common.database.DataSourceUtil;
import com.dandandog.blog.modules.system.auth.service.AuthUserService;
import com.dandandog.framework.common.utils.SecurityUtil;
import com.dandandog.framework.faces.controller.FacesController;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/8/24 9:00
 */
@Controller("/login.faces")
public class LoginController extends FacesController {

    @Resource
    private DataSourceUtil dataSourceUtil;

    @Resource
    private AuthUserService userService;

    @Override
    public void onEntry() {
        boolean isConn = dataSourceUtil.checkConnection();
        if (!isConn) {
            redirectInternal("/index");
        } else {
            int count = userService.count();
            if (count == 0) {
                redirectInternal("/register");
            }
            if (SecurityUtil.isLogin()) {
                redirectInternal("/dashboard");
            }
        }
    }


    public void onLogin() {
        String isError = getRequestParameter("error");
        if (StrUtil.isNotEmpty(isError)) {
            Object exception = getRequest().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
            if (exception instanceof UsernameNotFoundException) {
                errorMessages("error", "userNotFound");
            } else if (exception instanceof LockedException) {
                errorMessages("error", "accountLocked");
            } else if (exception instanceof AccountExpiredException) {
                errorMessages("error", "accountExpired");
            } else if (exception instanceof BadCredentialsException) {
                errorMessages("error", "passwordExpired");
            } else if (exception instanceof Exception) {
                errorMessages("error", "exception");
            }
        }
    }

}
