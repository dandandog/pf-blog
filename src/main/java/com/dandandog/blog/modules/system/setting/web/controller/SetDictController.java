package com.dandandog.blog.modules.system.setting.web.controller;

import cn.hutool.core.collection.CollUtil;
import com.dandandog.blog.modules.system.auth.web.facet.vo.AuthResourceVo;
import com.dandandog.blog.modules.system.auth.web.facet.vo.AuthRoleVo;
import com.dandandog.blog.modules.system.setting.web.facet.SetDictFacet;
import com.dandandog.blog.modules.system.setting.web.facet.vo.SetDictVo;
import com.dandandog.framework.faces.annotation.MessageRequired;
import com.dandandog.framework.faces.annotation.MessageSeverity;
import com.dandandog.framework.faces.annotation.MessageType;
import com.dandandog.framework.faces.controller.FacesController;
import com.dandandog.framework.faces.exception.MessageResolvableException;
import com.dandandog.framework.mapstruct.model.MapperVo;
import com.google.common.collect.Lists;
import org.primefaces.model.TreeNode;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/6 11:48
 */
@Controller("/system/set/dict.faces")
public class SetDictController extends FacesController {

    @Resource
    private SetDictFacet dictFacet;

    @Override
    public void onEntry() {
        putViewScope("list", dictFacet.list());
        putViewScope("vo", new SetDictVo());
        putViewScope("sinSelected", null);
        putViewScope("mulSelected", Lists.newArrayList());
    }


    public void add() {
        putViewScope("vo", new SetDictVo());
    }

    @MessageRequired(type = MessageType.OPERATION, severity = MessageSeverity.ERROR)
    public void edit() {
        SetDictVo selected = getViewScope("vo");
        SetDictVo vo = dictFacet.getOptById(selected.getId())
                .orElseThrow(() -> new MessageResolvableException("error", "dataNotFound"));
        putViewScope("vo", vo);
    }

    @MessageRequired(type = MessageType.SAVE)
    public void save() {
        SetDictVo selected = getViewScope("vo");
        dictFacet.saveOrUpdate(selected);
        onEntry();
    }

    @MessageRequired(type = MessageType.DELETE)
    public void delete() {
        SetDictVo selected = getViewScope("sinSelected");
        List<SetDictVo> selectedList = getViewScope("mulSelected");
        SetDictVo[] delEntity = CollUtil.defaultIfEmpty(selectedList, Lists.newArrayList(selected)).toArray(new SetDictVo[0]);
        dictFacet.removeByIds(delEntity);
        onEntry();
    }

}
