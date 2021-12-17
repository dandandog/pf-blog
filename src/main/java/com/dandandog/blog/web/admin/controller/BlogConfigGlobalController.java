package com.dandandog.blog.web.admin.controller;

import com.dandandog.blog.web.admin.faces.BlogConfigFaces;
import com.dandandog.blog.web.admin.faces.DictFaces;
import com.dandandog.blog.web.admin.faces.vo.DictVo;
import com.dandandog.framework.faces.annotation.MessageRequired;
import com.dandandog.framework.faces.annotation.MessageType;
import com.dandandog.framework.faces.controller.FacesController;
import com.dandandog.modules.blog.entity.BlogConfig;
import com.dandandog.modules.blog.entity.BlogConfigGlobal;
import com.dandandog.modules.sys.entity.DictValue;
import com.google.common.collect.Multimap;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/22 10:22
 */
@Controller("/admin/website/basic.faces")
public class BlogConfigGlobalController extends FacesController {


    @Resource
    private DictFaces dictFaces;

    @Resource
    private BlogConfigFaces configFaces;


    @Override
    public void onEntry() {
        String KEYS = "basic";
        Multimap<String, DictValue> values = dictFaces.getValueByCodes(KEYS);
        Map<DictVo, BlogConfig> fields = configFaces.findByValue(values.get(KEYS));
        putViewScope("fields", fields);
    }

    @MessageRequired(type = MessageType.SAVE)
    public void save() {
        Map<String, BlogConfigGlobal> fields = getViewScope("fields");
        configFaces.saveOrUpdate(fields.values());
    }


}
