package com.dandandog.blog.web.admin.converter;

import com.dandandog.modules.config.entity.ConfigDictNode;
import com.dandandog.modules.config.service.ConfigDictNodeService;
import com.dandandog.framework.faces.converter.GenericEntityConverter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/7 10:08
 */
@Component("setDictNodeConverter")
public class SetDictNodeConverter extends GenericEntityConverter<ConfigDictNode> {

    @Resource
    private ConfigDictNodeService dictNodeService;


    @Override
    protected ConfigDictNode getEntity(FacesContext facesContext, UIComponent uiComponent, String id) {
        return dictNodeService.getById(id);
    }
}
