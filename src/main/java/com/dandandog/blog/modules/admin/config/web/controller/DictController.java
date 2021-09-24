package com.dandandog.blog.modules.admin.config.web.controller;

import cn.hutool.core.collection.CollUtil;
import com.dandandog.blog.modules.admin.config.entity.DictNode;
import com.dandandog.blog.modules.admin.config.entity.enums.InputType;
import com.dandandog.blog.modules.admin.config.service.DictNodeService;
import com.dandandog.blog.modules.admin.config.web.faces.DictFaces;
import com.dandandog.blog.modules.admin.config.web.faces.vo.DictVo;
import com.dandandog.framework.faces.annotation.MessageRequired;
import com.dandandog.framework.faces.annotation.MessageSeverity;
import com.dandandog.framework.faces.annotation.MessageType;
import com.dandandog.framework.faces.controller.FacesController;
import com.dandandog.framework.faces.exception.MessageResolvableException;
import com.google.common.collect.Lists;
import org.primefaces.event.CellEditEvent;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/6 11:48
 */
@Controller("/admin/config/dict.faces")
public class DictController extends FacesController {

    @Resource
    private DictFaces dictFacet;

    @Resource
    private DictNodeService dictNodeService;

    @Override
    public void onEntry() {
        putViewScope("list", dictFacet.list());
        putViewScope("nodes", dictFacet.nodes());

        putViewScope("vo", new DictVo());
        putViewScope("sinSelected", null);
        putViewScope("mulSelected", Lists.newArrayList());

        putViewScope("types", InputType.values());
    }


    public void add() {
        DictVo selected = getViewScope("sinSelected");
        DictVo vo = new DictVo();
        if (selected != null) {
            vo.setNode(selected.getNode());
        }
        putViewScope("vo", vo);
    }

    @MessageRequired(type = MessageType.OPERATION, severity = MessageSeverity.ERROR)
    public void edit() {
        DictVo selected = getViewScope("sinSelected");
        DictVo vo = dictFacet.getOptById(selected.getId())
                .orElseThrow(() -> new MessageResolvableException("error", "dataNotFound"));
        putViewScope("vo", vo);
    }

    @MessageRequired(type = MessageType.SAVE)
    public void save() {
        DictVo selected = getViewScope("vo");
        dictFacet.saveOrUpdate(selected);
        onEntry();
    }

    @MessageRequired(type = MessageType.DELETE)
    public void delete() {
        DictVo selected = getViewScope("sinSelected");
        List<DictVo> selectedList = getViewScope("mulSelected");
        DictVo[] delEntity = CollUtil.defaultIfEmpty(selectedList, Lists.newArrayList(selected)).toArray(new DictVo[0]);
        dictFacet.removeByIds(delEntity);
        onEntry();
    }

    public void onChangeSeq(DictNode node) {
        dictNodeService.updateById(node);
    }

    public void cellEdit(CellEditEvent<DictVo> event) {
        dictFacet.updateByFiled(event.getRowKey(), event.getColumn().getField(), event.getNewValue());
    }


}
