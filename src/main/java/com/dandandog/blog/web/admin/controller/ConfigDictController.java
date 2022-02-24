package com.dandandog.blog.web.admin.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import com.dandandog.blog.web.admin.faces.ConfigDictFaces;
import com.dandandog.blog.web.admin.faces.vo.ConfigDictNodeVo;
import com.dandandog.blog.web.admin.faces.vo.ConfigDictValueVo;
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
    private ConfigDictFaces dictFacet;

    @Override
    public void onEntry() {
        initTreeState();
        putViewScope("rootTree", getDataModel());
        putViewScope("list", getValueList());
        putViewScope("node", new ConfigDictNodeVo());
        putViewScope("value", new ConfigDictValueVo());
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
        ConfigDictNodeVo node = new ConfigDictNodeVo();
        putViewScope("node", node);
        putViewScope("inputTree", getDataModel());
    }

    public void editNode() {
        TreeNode selectedNode = getViewScope("selectedNode");
        ConfigDictNodeVo selected = (ConfigDictNodeVo) selectedNode.getData();
        ConfigDictNodeVo node = dictFacet.getNodeById(selected.getId())
                .orElseThrow(() -> new MessageResolvableException("error.dataNotFound"));
        putViewScope("treeState", TreeNodeState.builder().selectedNodes(new TreeNode[] {selectedNode}).edit(true).build());
        putViewScope("node", node);
        putViewScope("inputTree", getDataModel());
    }

    @MessageRequired(type = MessageType.SAVE)
    public void saveNode() {
        ConfigDictNodeVo node = getViewScope("node");
        dictFacet.saveOrUpdateNode(node);
        onEntry();
    }

    @MessageRequired(type = MessageType.DELETE)
    public void deleteNode() {
        TreeNode selectedNode = getViewScope("selectedNode");
        ConfigDictNodeVo selected = (ConfigDictNodeVo) selectedNode.getData();
        dictFacet.removeByNode(selected);
        onEntry();
    }

    public void onNodeSelect(NodeSelectEvent event) {
        TreeNode selectedNode = event.getTreeNode();
        ConfigDictNodeVo node = (ConfigDictNodeVo) selectedNode.getData();
        Collection<ConfigDictValueVo> list = dictFacet.list(node.getId());
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
        ConfigDictValueVo vo = new ConfigDictValueVo();
        putViewScope("vo", vo);
        putViewScope("inputTree", getDataModel());
    }

    public void edit() {
        ConfigDictValueVo selected = getViewScope("sinSelected");
        ConfigDictValueVo vo = dictFacet.getOptById(selected.getId())
                .orElseThrow(() -> new MessageResolvableException("error.dataNotFound"));
        putViewScope("vo", vo);
        putViewScope("inputTree", getDataModel());
    }

    @MessageRequired(type = MessageType.SAVE)
    public void save() {
        ConfigDictValueVo vo = getViewScope("vo");
        dictFacet.saveOrUpdateValue(vo);

        TreeNode node = vo.getNode();
        ConfigDictNodeVo data = (ConfigDictNodeVo) node.getData();
        Collection<ConfigDictValueVo> list = dictFacet.list(data.getId());

        putViewScope("rootTree", getDataModel());
        putViewScope("selectedNode", node);
        putViewScope("list", list);
    }

    @MessageRequired(type = MessageType.DELETE)
    public void delete() {
        ConfigDictValueVo selected = getViewScope("sinSelected");
        List<ConfigDictValueVo> selectedList = getViewScope("mulSelected");
        String[] delEntity = CollUtil.defaultIfEmpty(selectedList, Lists.newArrayList(selected))
                .stream().map(ConfigDictValueVo::getId).toArray(String[]::new);
        dictFacet.removeByIds(delEntity);
        onEntry();
    }

    public void cellEdit(CellEditEvent<ConfigDictValueVo> event) {
        dictFacet.updateByFiled(event.getRowKey(), event.getColumn().getField(), event.getNewValue());
    }

    public void onRowReorder(ReorderEvent event) {
        List<ConfigDictValueVo> list = getViewScope("list");
        dictFacet.updateBySort(list);
    }

    public Collection<ConfigDictValueVo> getValueList() {
        TreeNodeState state = getViewScope("treeState");
        if (ArrayUtil.isNotEmpty(state.getSelectedNodes())) {
            TreeNode selectedNode = state.getSelectedNodes()[0];
            ConfigDictNodeVo node = (ConfigDictNodeVo) selectedNode.getData();
            return dictFacet.list(node.getId());
        }
        return null;
    }


}
