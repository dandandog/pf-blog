package com.dandandog.blog.modules.system.web.converter;


import com.dandandog.blog.modules.system.entity.AuthRole;
import com.dandandog.blog.modules.system.service.AuthRoleService;
import com.dandandog.framework.faces.converter.GenericEntityConverter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

@Component("authRoleConverter")
public class AuthRoleConverter extends GenericEntityConverter<AuthRole> {

    @Resource
    private AuthRoleService authRoleService;


    @Override
    protected AuthRole getEntity(FacesContext facesContext, UIComponent uiComponent, String id) {
        return authRoleService.getById(id);
    }
}
