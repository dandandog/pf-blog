package com.dandandog.common.aspect;

import com.dandandog.framework.faces.aspect.AbstractFacesErrorAspect;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class FacesErrorAspect extends AbstractFacesErrorAspect {

    @Override
    public String noticeSuccessClientId() {
        return null;
    }

    @Override
    public String noticeFailureClientId() {
        return "dialogMessages";
    }
}
