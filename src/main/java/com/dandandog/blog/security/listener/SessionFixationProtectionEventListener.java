package com.dandandog.blog.security.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.web.authentication.session.SessionFixationProtectionEvent;
import org.springframework.stereotype.Component;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/6/4 10:56
 */

@Slf4j
@Component
public class SessionFixationProtectionEventListener implements ApplicationListener<SessionFixationProtectionEvent> {

    @Override
    public void onApplicationEvent(SessionFixationProtectionEvent event) {
        String oldSessionId = event.getOldSessionId();
        String newSessionId = event.getNewSessionId();
        log.info("更改sessionId的值:{}->{}", oldSessionId, newSessionId);
    }
}
