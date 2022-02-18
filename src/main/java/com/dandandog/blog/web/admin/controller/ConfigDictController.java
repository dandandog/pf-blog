package com.dandandog.blog.web.admin.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import com.dandandog.blog.web.admin.faces.DictFaces;
import com.dandandog.blog.web.admin.faces.vo.DictNodeVo;
import com.dandandog.blog.web.admin.faces.vo.DictValueVo;
import com.dandandog.framework.faces.annotation.MessageRequired;
import com.dandandog.framework.faces.annotation.MessageType;
import com.dandandog.framework.faces.controller.FacesController;
import com.dandandog.framework.faces.exception.MessageResolvableException;
import com.dandandog.framework.faces.model.tree.TreeDataModel;
import com.dandandog.framework.faces.model.tree.TreeNodeState;
import com.google.common.collect.Lists;
import org.primefaces.event.*;
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
        initTreeState();
        putViewScope("rootTree", getDataModel());
        putViewScope("list", getValueList());
        putViewScope("node", new DictNodeVo());
        putViewScope("value", new DictValueVo());
        putViewScope("sinSelected", null);
        putViewScope("selectedNode", new DefaultTreeNode());
        putViewScope("mulSelected", Lists.newArrayList());
    }

    public void initTreeState() {
        TreeNodeState state = getViewScope("treeState");
        if (state == null) {
            state = TreeNodeState.builder().build();
        }
        state.setEdit(false);
        putViewScope("treeState", state);
    }

    public TreeNode getDataModel() {
        TreeDataModel dataModel = getViewScope("dataModel");
        TreeNodeState state = getViewScope("treeState");
        return dictFacet.initNodeTree(dataModel, state);
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
        putViewScope("treeState", TreeNodeState.builder().selectedNodes(new TreeNode[] {selectedNode}).edit(true).build());
        putViewScope("node", node);
        putViewScope("inputTree", getDataModel());
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
        DictNodeVo selected = (DictNodeVo) selectedNode.getData();
        dictFacet.removeByNode(selected);
        onEntry();
    }

    public void onNodeSelect(NodeSelectEvent event) {
        TreeNode selectedNode = event.getTreeNode();
        DictNodeVo node = (DictNodeVo) selectedNode.getData();
        Collection<DictValueVo> list = dictFacet.list(node.getId());
        putViewScope("list", list);
        putViewScope("treeState", TreeNodeState.builder().selectedNodes(new TreeNode[] {selectedNode}).build());
    }

    public void onNodeUnselect(NodeUnselectEvent event) {
        putViewScope("list", null);
        putViewScope("treeState", TreeNodeState.builder().build());
    }

    public void onNodeExpand(NodeExpandEvent event) {
        TreeNodeState treeState = getViewScope("treeState");
        TreeNode expandNode = event.getTreeNode();
        TreeNode[] newExpandNodes = ArrayUtil.append(treeState.getExpandNodes(), expandNode);
        putViewScope("treeState", TreeNodeState.builder().selectedNodes(newExpandNodes).build());
    }

    public void onNodeCollapse(NodeCollapseEvent event) {
        TreeNodeState treeState = getViewScope("treeState");
        TreeNode expandNode = event.getTreeNode();
        TreeNode[] newExpandNodes = ArrayUtil.removeEle(treeState.getExpandNodes(), expandNode);
        putViewScope("treeState", TreeNodeState.builder().selectedNodes(newExpandNodes).build());
    }


    public void add() {
        DictValueVo vo = new DictValueVo();
        putViewScope("vo", vo);
        putViewScope("inputTree", getDataModel());
    }

    public void edit() {
        DictValueVo selected = getViewScope("sinSelected");
        DictValueVo vo = dictFacet.getOptById(selected.getId())
                .orElseThrow(() -> new MessageResolvableException("error.dataNotFound"));
        putViewScope("vo", vo);
        putViewScope("inputTree", getDataModel());
    }

    @MessageRequired(type = MessageType.SAVE)
    public void save() {
        DictValueVo vo = getViewScope("vo");
        dictFacet.saveOrUpdateValue(vo);

        TreeNode node = vo.getNode();
        DictNodeVo data = (DictNodeVo) node.getData();
        Collection<DictValueVo> list = dictFacet.list(data.getId());

        putViewScope("rootTree", getDataModel());
        putViewScope("selectedNode", node);
        putViewScope("list", list);
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

    public void onRowReorder(ReorderEvent event) {
        List<DictValueVo> list = getViewScope("list");
        dictFacet.updateBySort(list);
    }

    public Collection<DictValueVo> getValueList() {
        TreeNodeState state = getViewScope("treeState");
        if (ArrayUtil.isNotEmpty(state.getSelectedNodes())) {
            TreeNode selectedNode = state.getSelectedNodes()[0];
            DictNodeVo node = (DictNodeVo) selectedNode.getData();
            return dictFacet.list(node.getId());
        }
        return null;
    }


}
