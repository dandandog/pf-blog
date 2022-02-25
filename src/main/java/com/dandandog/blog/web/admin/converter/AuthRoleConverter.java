package com.dandandog.blog.web.admin.converter;


import com.dandandog.framework.faces.converter.GenericEntityConverter;
import com.dandandog.modules.auth.entity.AuthRole;
import com.dandandog.modules.auth.service.AuthRoleService;

import javax.annotation.Resource;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

@FacesConverter("authRoleConverter")
public class AuthRoleConverter extends GenericEntityConverter<AuthRole> {

    @Resource
    private AuthRoleService authRoleService;


    @Override
    protected AuthRole getEntity(FacesContext facesContext, UIComponent uiComponent, String id) {
        return authRoleService.getById(id);
    }
}
