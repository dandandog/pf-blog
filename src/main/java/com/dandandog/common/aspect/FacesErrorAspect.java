package com.dandandog.common.aspect;

import com.dandandog.framework.faces.aspect.AbstractFacesErrorAspect;


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
