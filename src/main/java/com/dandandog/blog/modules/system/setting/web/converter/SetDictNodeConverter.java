package com.dandandog.blog.modules.system.setting.web.converter;

import com.dandandog.blog.modules.system.auth.entity.AuthRole;
import com.dandandog.blog.modules.system.auth.service.AuthRoleService;
import com.dandandog.blog.modules.system.setting.entity.SetDictNode;
import com.dandandog.blog.modules.system.setting.service.SetDictNodeService;
import com.dandandog.framework.faces.converter.GenericEntityConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.tags.form.SelectTag;

import javax.annotation.Resource;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/7 10:08
 */
@Component("setDictNodeConverter")
public class SetDictNodeConverter extends GenericEntityConverter<SetDictNode> {

    @Resource
    private SetDictNodeService dictNodeService;


    @Override
    protected SetDictNode getEntity(FacesContext facesContext, UIComponent uiComponent, String id) {
        return dictNodeService.getById(id);
    }
}
