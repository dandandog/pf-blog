package com.dandandog.blog.common.aspect;

import com.dandandog.framework.faces.aspect.AbstractMessageAspect;
import com.dandandog.framework.faces.config.properties.MessageProperties;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author JohnnyLiu
 **/

@Aspect
@Component
@EnableConfigurationProperties({MessageProperties.class})
public class MessageAspect extends AbstractMessageAspect {

    @Autowired
    public MessageAspect(MessageProperties properties) {
        super(properties);
    }


    @Override
    public String noticeSuccessClientId() {
        return "globalMessageGrowl";
    }

    @Override
    public String noticeFailureClientId() {
        return "dialogMessages";
    }
}
