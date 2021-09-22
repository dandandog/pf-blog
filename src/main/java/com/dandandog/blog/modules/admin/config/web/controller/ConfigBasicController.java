package com.dandandog.blog.modules.admin.config.web.controller;

import com.dandandog.blog.modules.admin.config.web.faces.BlogConfigFaces;
import com.dandandog.blog.modules.admin.setting.entity.DictValue;
import com.dandandog.blog.modules.admin.setting.web.faces.DictFaces;
import com.dandandog.framework.faces.controller.FacesController;
import com.google.common.collect.Multimap;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/22 10:22
 */
@Controller("/admin/config/basic")
public class ConfigBasicController extends FacesController {

    private static String KEYS = "basic";


    @Resource
    private DictFaces dictFaces;

    @Resource
    private BlogConfigFaces configFaces;


    @Override
    public void onEntry() {
        Multimap<String, DictValue> valueByCodes = dictFaces.getValueByCodes(KEYS);



    }


}
