package com.dandandog.blog.security;

import com.dandandog.blog.common.view.MenuView;
import com.dandandog.framework.common.utils.SpringContextUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;

import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FacesSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final String redirectUrl;

    public FacesSuccessHandler(String redirectUrl) {
        Assert.isTrue(UrlUtils.isValidRedirectUrl(redirectUrl), () -> "'" + redirectUrl + "' is not a valid redirectUrl URL");
        this.redirectUrl = redirectUrl;
    }

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Object obj = request.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST");
        String url = request.getServletContext().getContextPath() + redirectUrl;
        MenuView menuView = SpringContextUtil.getBean("menuView", MenuView.class);
        menuView.initialize();
        if (obj != null) {
            url = ((DefaultSavedRequest) obj).getRequestURL();
        }
        response.sendRedirect(url);
    }
}
