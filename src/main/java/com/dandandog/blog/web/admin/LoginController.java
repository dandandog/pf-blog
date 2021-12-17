package com.dandandog.blog.web.admin;

import com.dandandog.common.database.DataSourceUtil;
import com.dandandog.framework.common.utils.SecurityUtil;
import com.dandandog.framework.faces.controller.FacesController;
import com.dandandog.modules.auth.service.AuthUserService;
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
        checkException();

        boolean isConn = dataSourceUtil.checkConnection();
        if (!isConn) {
            redirectInternal("/install");
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


    public void checkException() {
        String isError = getRequestParameter("error");
        if (isError != null) {
            Object exception = getRequest().getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
            if (exception instanceof UsernameNotFoundException) {
                errorMessages("error.userNotFound", null);
            } else if (exception instanceof LockedException) {
                errorMessages("error.accountLocked", null);
            } else if (exception instanceof AccountExpiredException) {
                errorMessages("error.accountExpired", null);
            } else if (exception instanceof BadCredentialsException) {
                errorMessages("error.passwordExpired", null);
            } else if (exception instanceof Exception) {
                errorMessages("error.exception", null);
            }
        }
    }

}
