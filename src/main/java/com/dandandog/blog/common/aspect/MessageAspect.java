package com.dandandog.blog.common.aspect;

import com.dandandog.framework.faces.aspect.AbstractMessageAspect;
import com.dandandog.framework.faces.config.properties.MessageProperties;
import com.dandandog.framework.faces.utils.RequestContextUtil;
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
    protected String globalShow() {
        RequestContextUtil.update("globalMessageGrowl");
        return "globalMessageGrowl";
    }

    @Override
    protected String dialogShow() {
        RequestContextUtil.execute("PF('globalDialog').show()");
        RequestContextUtil.update("globalDialogMessages");
        return "globalDialogMessages";
    }

    @Override
    protected String messageShow() {
        return "messages";
    }
}
