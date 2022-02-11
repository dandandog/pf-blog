package com.dandandog.blog.web.admin.controller;


import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.dandandog.blog.web.admin.faces.AuthResourceFaces;
import com.dandandog.blog.web.admin.faces.vo.AuthResourceVo;
import com.dandandog.common.utils.IconUtil;
import com.dandandog.common.utils.TreeUtil;
import com.dandandog.framework.faces.annotation.MessageRequired;
import com.dandandog.framework.faces.annotation.MessageType;
import com.dandandog.framework.faces.controller.FacesController;
import com.dandandog.framework.faces.exception.MessageResolvableException;
import com.dandandog.framework.faces.model.tree.TreeDataModel;
import com.dandandog.framework.faces.model.tree.TreeNodeState;
import com.dandandog.modules.auth.entity.enums.ResourceTarget;
import com.dandandog.modules.auth.entity.enums.ResourceType;
import lombok.extern.slf4j.Slf4j;
import org.primefaces.event.*;
import org.primefaces.model.TreeNode;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * 系统资源(SysResource)表控制层
 *
 * @author JohnnyLiu
 * @since 2020-09-06 22:06:06
 */
@Slf4j
@Controller("/admin/auth/resource.faces")
public class AuthResourceController extends FacesController {

    /**
     * 服务对象
     */
    @Resource
    private AuthResourceFaces resourceFaces;

    @Override
    public void onEntry() {
        initTreeState();

        putViewScope("vo", new AuthResourceVo());
        putViewScope("rootTree", getDataModel());
        putViewScope("sinSelected", null);
        putViewScope("mulSelected", new TreeNode[0]);

        putViewScope("types", ResourceType.values());
        putViewScope("targets", ResourceTarget.values());
        putViewScope("icons", IconUtil.findAll());
        putViewScope("path", "/");
    }

    public void initTreeState() {
        TreeNodeState state = getSessionScope("treeState");
        if (state == null) {
            state = TreeNodeState.builder().build();
        }
        state.setEdit(false);
        putSessionScope("treeState", state);
    }

    public TreeNode getDataModel() {
        TreeDataModel dataModel = getViewScope("dataModel");
        TreeNodeState state = getSessionScope("treeState");
        if (ArrayUtil.isNotEmpty(state.getSelectedNodes())) {
            TreeNode selectedNode = state.getSelectedNodes()[0];
            AuthResourceVo data = (AuthResourceVo) selectedNode.getData();
            String path = StrUtil.appendIfMissing(data.getPath(), "/");
            putViewScope("path", path);
            AuthResourceVo vo = getViewScope("vo");
            vo.setPath(StrUtil.replace(vo.getPath(), path, ""));
        }
        return resourceFaces.initNodeTree(dataModel, state);
    }

    public void addNode() {
        putViewScope("vo", new AuthResourceVo(ResourceType.MENU));
        putViewScope("rootTree", getDataModel());
    }

    public void editNode() {
        TreeNode selectedNode = getViewScope("selectedNode");
        AuthResourceVo data = (AuthResourceVo) selectedNode.getData();
        AuthResourceVo vo = resourceFaces.getOptById(data.getId())
                .orElseThrow(() -> new MessageResolvableException("error.dataNotFound"));
        putViewScope("treeState", TreeNodeState.builder().selectedNodes(new TreeNode[] {selectedNode}).edit(true).build());
        putViewScope("vo", vo);
        putViewScope("rootTree", getDataModel());
    }

    @MessageRequired(type = MessageType.SAVE)
    public void saveNode() {
        String path = getViewScope("path");
        AuthResourceVo vo = getViewScope("vo");
        vo.setPath(path + vo.getPath());
        resourceFaces.saveOrUpdate(vo);
        onEntry();
    }

    @MessageRequired(type = MessageType.DELETE)
    public void deleteNode() {
        TreeNode selected = getViewScope("sinSelected");
        TreeNode[] mulSelected = getViewScope("mulSelected");
        String[] idList = TreeUtil.selectedId(mulSelected, selected);
        resourceFaces.removeByIds(idList);
        onEntry();
    }

    public void cellEdit(CellEditEvent<AuthResourceVo> event) {
        resourceFaces.updateByFiled(event.getRowKey(), event.getColumn().getField(), event.getNewValue());
    }

    public void onNodeSelect(NodeSelectEvent event) {
        TreeNode selectedNode = event.getTreeNode();

        putSessionScope("treeState", TreeNodeState.builder().selectedNodes(new TreeNode[] {selectedNode}).build());
    }

    public void onNodeUnselect(NodeUnselectEvent event) {
        putViewScope("list", null);
        putSessionScope("treeState", TreeNodeState.builder().build());
    }

    public void onParentSelect(NodeSelectEvent event) {
        TreeNode selectedNode = event.getTreeNode();
        AuthResourceVo data = (AuthResourceVo) selectedNode.getData();
        putViewScope("path", StrUtil.appendIfMissing(data.getPath(), "/"));
    }

    public void onParentUnselect(NodeUnselectEvent event) {
        putViewScope("path", "/");
    }

    public void onNodeExpand(NodeExpandEvent event) {
        TreeNodeState treeState = getSessionScope("treeState");
        TreeNode expandNode = event.getTreeNode();
        TreeNode[] newExpandNodes = ArrayUtil.append(treeState.getExpandNodes(), expandNode);
        putSessionScope("treeState", TreeNodeState.builder().selectedNodes(newExpandNodes).build());
    }

    public void onNodeCollapse(NodeCollapseEvent event) {
        TreeNodeState treeState = getSessionScope("treeState");
        TreeNode expandNode = event.getTreeNode();
        TreeNode[] newExpandNodes = ArrayUtil.removeEle(treeState.getExpandNodes(), expandNode);
        putSessionScope("treeState", TreeNodeState.builder().selectedNodes(newExpandNodes).build());
    }

}