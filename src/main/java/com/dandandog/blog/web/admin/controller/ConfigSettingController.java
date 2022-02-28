package com.dandandog.blog.web.admin.controller;

import cn.hutool.core.collection.CollUtil;
import com.dandandog.blog.web.admin.faces.ConfigSettingFaces;
import com.dandandog.blog.web.admin.faces.vo.ConfigSettingVo;
import com.dandandog.framework.common.model.IVo;
import com.dandandog.framework.faces.annotation.MessageRequired;
import com.dandandog.framework.faces.annotation.MessageType;
import com.dandandog.framework.faces.controller.FacesController;
import com.dandandog.framework.faces.exception.MessageResolvableException;
import com.dandandog.modules.config.entity.ConfigDictNode;
import com.dandandog.modules.config.entity.enums.InputType;
import com.dandandog.modules.config.service.ConfigDictNodeService;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: JohnnyLiu
 * @Date: 2022/2/24 17:46
 */
@Controller("/admin/config/setting.faces")
public class ConfigSettingController extends FacesController {


    @Resource
    private ConfigSettingFaces settingFaces;

    @Resource
    private ConfigDictNodeService dictNodeService;


    @Override
    public void onEntry() {
        putViewScope("vo", new ConfigSettingVo());
        putViewScope("list", settingFaces.list());
        putViewScope("sinSelected", null);
        putViewScope("mulSelected", Lists.newArrayList());

        putViewScope("types", InputType.values());
        putViewScope("dictNodes", dictNodeService.lambdaQuery().isNull(ConfigDictNode::getParentId).list());
    }

    public void add() {
        ConfigSettingVo vo = new ConfigSettingVo();
        putViewScope("vo", vo);
    }

    public void edit() {
        ConfigSettingVo selected = getViewScope("sinSelected");
        ConfigSettingVo vo = settingFaces.getOptById(selected.getId())
                .orElseThrow(() -> new MessageResolvableException("error.dataNotFound"));
        putViewScope("vo", vo);
    }

    @MessageRequired(type = MessageType.SAVE)
    public void save() {
        ConfigSettingVo vo = getViewScope("vo");
        settingFaces.saveOrUpdate(vo);
        onEntry();
    }

    @MessageRequired(type = MessageType.DELETE)
    public void delete() {
        ConfigSettingVo selected = getViewScope("sinSelected");
        List<ConfigSettingVo> selectedList = getViewScope("mulSelected");
        String[] idList = CollUtil.defaultIfEmpty(selectedList, Lists.newArrayList(selected))
                .stream().map(IVo::getId).toArray(String[]::new);
        settingFaces.removeByIds(idList);
        onEntry();
    }
}
