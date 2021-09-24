package com.dandandog.blog.modules.admin.config.web.controller;

import com.dandandog.blog.modules.admin.config.entity.BlogConfigs;
import com.dandandog.blog.modules.admin.config.entity.DictValue;
import com.dandandog.blog.modules.admin.config.web.faces.BlogConfigFaces;
import com.dandandog.blog.modules.admin.config.web.faces.DictFaces;
import com.dandandog.blog.modules.admin.config.web.faces.vo.DictVo;
import com.dandandog.framework.faces.annotation.MessageRequired;
import com.dandandog.framework.faces.annotation.MessageType;
import com.dandandog.framework.faces.controller.FacesController;
import com.google.common.collect.Multimap;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/22 10:22
 */
@Controller("/admin/config/basic.faces")
public class ConfigBasicController extends FacesController {


    @Resource
    private DictFaces dictFaces;

    @Resource
    private BlogConfigFaces configFaces;

    private String KEYS = "basic";


    @Override
    public void onEntry() {
        Multimap<String, DictValue> values = dictFaces.getValueByCodes(KEYS);
        Map<DictVo, BlogConfigs> fields = configFaces.findByValue(values.get(KEYS));
        putViewScope("fields", fields);
    }

    @MessageRequired(type = MessageType.SAVE)
    public void save() {
        Map<String, BlogConfigs> fields = getViewScope("fields");
        configFaces.saveOrUpdate(fields.values());
    }


}
