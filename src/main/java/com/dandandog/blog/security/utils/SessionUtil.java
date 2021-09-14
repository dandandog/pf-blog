package com.dandandog.blog.security.utils;

import com.dandandog.blog.modules.admin.auth.entity.AuthUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/6/8 11:44
 */

@Component
public class SessionUtil {


    private static final CopyOnWriteArraySet<HttpSession> sessions = new CopyOnWriteArraySet<>();


    public static synchronized void addSession(HttpSession session) {
        if (session != null) {
            sessions.add(session);
        }
    }

    public static synchronized void delSession(HttpSession session) {
        if (session != null) {
            sessions.remove(session);
        }
    }

    public static synchronized Iterator<HttpSession> getAllSessions() {
        return sessions.iterator();
    }

    public static void refreshSession(HttpSession session, AuthUser user) {
        SecurityContext securityContext = (SecurityContext) session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
        Authentication authentication = securityContext.getAuthentication();

        UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(
                user, authentication.getCredentials(), user.getAuthorities());
        result.setDetails(authentication.getDetails());
        securityContext.setAuthentication(result);
    }
}
