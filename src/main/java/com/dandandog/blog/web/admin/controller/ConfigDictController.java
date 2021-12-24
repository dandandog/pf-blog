package com.dandandog.blog.web.admin.controller;

import cn.hutool.core.collection.CollUtil;
import com.dandandog.blog.web.admin.faces.DictFaces;
import com.dandandog.blog.web.admin.faces.vo.DictNodeVo;
import com.dandandog.blog.web.admin.faces.vo.DictValueVo;
import com.dandandog.framework.faces.annotation.MessageRequired;
import com.dandandog.framework.faces.annotation.MessageType;
import com.dandandog.framework.faces.controller.FacesController;
import com.dandandog.framework.faces.exception.MessageResolvableException;
import com.dandandog.framework.faces.model.tree.TreeDataModel;
import com.dandandog.framework.faces.model.tree.TreeFaces;
import com.dandandog.modules.config.entity.enums.InputType;
import com.google.common.collect.Lists;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/6 11:48
 */
@Controller("/admin/config/dict.faces")
public class ConfigDictController extends FacesController {

    @Resource
    private DictFaces dictFacet;

    @Override
    public void onEntry() {
        putViewScope("rootTree", getDataModel());
        putViewScope("node", new DictNodeVo());
        putViewScope("value", new DictValueVo());
        putViewScope("sinSelected", null);
        putViewScope("selectedNode", new DefaultTreeNode());
        putViewScope("mulSelected", Lists.newArrayList());
        putViewScope("types", InputType.values());
    }

    public TreeNode getDataModel(TreeFaces... selected) {
        TreeDataModel dataModel = getViewScope("dataModel");
        return dictFacet.initTree(dataModel, selected);
    }

    public void addNode() {
        DictNodeVo node = new DictNodeVo();
        putViewScope("node", node);
        putViewScope("inputTree", getDataModel());
    }

    public void editNode() {
        TreeNode selectedNode = getViewScope("selectedNode");
        DictNodeVo selected = (DictNodeVo) selectedNode.getData();
        DictNodeVo node = dictFacet.getNodeById(selected.getId())
                .orElseThrow(() -> new MessageResolvableException("error.dataNotFound"));
        putViewScope("node", node);
        putViewScope("inputTree", getDataModel(node));
    }

    @MessageRequired(type = MessageType.SAVE)
    public void saveNode() {
        DictNodeVo node = getViewScope("node");
        dictFacet.saveOrUpdateNode(node);
        onEntry();
    }

    @MessageRequired(type = MessageType.DELETE)
    public void deleteNode() {
        TreeNode selectedNode = getViewScope("selectedNode");
        dictFacet.removeByNode(selectedNode);
        onEntry();
    }

    public void onNodeSelect(NodeSelectEvent event) {
        TreeNode selectedNode = event.getTreeNode();
        DictNodeVo node = (DictNodeVo) selectedNode.getData();
        Collection<DictValueVo> list = dictFacet.list(node.getId());
        putViewScope("list", list);
    }

    public void onNodeUnselect(NodeUnselectEvent event) {
        putViewScope("list", null);
    }

    public void add() {
        DictValueVo selected = getViewScope("sinSelected");
        DictValueVo vo = new DictValueVo();
        if (selected != null) {
            vo.setNode(selected.getNode());
        }
        putViewScope("vo", vo);
        putViewScope("inputTree", getDataModel());
    }

    public void edit() {
        DictValueVo selected = getViewScope("sinSelected");
        DictValueVo vo = dictFacet.getOptById(selected.getId())
                .orElseThrow(() -> new MessageResolvableException("error.dataNotFound"));
        putViewScope("vo", vo);
        putViewScope("inputTree", getDataModel((TreeFaces) vo.getNode().getData()));
    }

    @MessageRequired(type = MessageType.SAVE)
    public void save() {
        DictValueVo selected = getViewScope("vo");
        dictFacet.saveOrUpdateValue(selected);
        onEntry();
    }

    @MessageRequired(type = MessageType.DELETE)
    public void delete() {
        DictValueVo selected = getViewScope("sinSelected");
        List<DictValueVo> selectedList = getViewScope("mulSelected");
        String[] delEntity = CollUtil.defaultIfEmpty(selectedList, Lists.newArrayList(selected))
                .stream().map(DictValueVo::getId).toArray(String[]::new);
        dictFacet.removeByIds(delEntity);
        onEntry();
    }

    public void cellEdit(CellEditEvent<DictValueVo> event) {
        dictFacet.updateByFiled(event.getRowKey(), event.getColumn().getField(), event.getNewValue());
    }

}
