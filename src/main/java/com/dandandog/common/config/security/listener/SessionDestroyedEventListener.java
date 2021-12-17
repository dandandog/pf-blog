package com.dandandog.common.config.security.listener;

import com.dandandog.common.config.security.utils.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;
import org.springframework.stereotype.Component;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/6/4 10:41
 */

@Component
@Slf4j
public class SessionDestroyedEventListener implements ApplicationListener<HttpSessionDestroyedEvent> {


    @Override
    public void onApplicationEvent(HttpSessionDestroyedEvent event) {
        try {
            log.info("失效session:{}", event.getSession().getId());
            SessionUtil.delSession(event.getSession());
        } catch (Exception e) {
            log.error(String.format("失效session:[%s]发生异常.", event.getId()), e);
        }
    }
}
