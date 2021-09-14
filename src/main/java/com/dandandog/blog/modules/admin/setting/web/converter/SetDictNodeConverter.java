package com.dandandog.blog.modules.admin.setting.web.converter;

import com.dandandog.blog.modules.admin.setting.entity.DictNode;
import com.dandandog.blog.modules.admin.setting.service.DictNodeService;
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
public class SetDictNodeConverter extends GenericEntityConverter<DictNode> {

    @Resource
    private DictNodeService dictNodeService;


    @Override
    protected DictNode getEntity(FacesContext facesContext, UIComponent uiComponent, String id) {
        return dictNodeService.getById(id);
    }
}
