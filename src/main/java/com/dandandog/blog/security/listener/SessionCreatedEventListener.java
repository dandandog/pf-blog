package com.dandandog.blog.security.listener;

import com.dandandog.blog.security.utils.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.web.session.HttpSessionCreatedEvent;
import org.springframework.stereotype.Component;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/6/4 10:38
 */


@Component
@Slf4j
public class SessionCreatedEventListener implements ApplicationListener<HttpSessionCreatedEvent> {


    @Override
    public void onApplicationEvent(HttpSessionCreatedEvent event) {

        try {
            log.info("新建session:{}", event.getSession().getId());
            SessionUtil.addSession(event.getSession());
        } catch (Exception e) {
            log.info(String.format("添加session:[%s]出现异常.", event.getSession().getId()), e);
        }
    }
}
