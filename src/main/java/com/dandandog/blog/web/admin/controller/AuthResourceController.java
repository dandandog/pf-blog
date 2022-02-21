package com.dandandog.blog.web.admin.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.StrUtil;
import com.dandandog.blog.web.admin.faces.AuthResourceFaces;
import com.dandandog.blog.web.admin.faces.vo.AuthResourceVo;
import com.dandandog.common.utils.IconUtil;
import com.dandandog.framework.faces.annotation.MessageRequired;
import com.dandandog.framework.faces.annotation.MessageType;
import com.dandandog.framework.faces.controller.FacesController;
import com.dandandog.framework.faces.exception.MessageResolvableException;
import com.dandandog.framework.faces.model.tree.TreeDataModel;
import com.dandandog.framework.faces.model.tree.TreeNodeState;
import com.dandandog.modules.auth.entity.enums.ResourceTarget;
import com.dandandog.modules.auth.entity.enums.ResourceType;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.event.ReorderEvent;
import org.primefaces.model.TreeNode;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

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
        putViewScope("rootTree", getTreeModel());
        putViewScope("list", getButtonList());
        putViewScope("sinSelected", null);
        putViewScope("mulSelected", Lists.newArrayList());

        putViewScope("types", ResourceType.values());
        putViewScope("targets", ResourceTarget.values());
        putViewScope("icons", IconUtil.findAll());
        putViewScope("path", StrUtil.SLASH);

    }

    public void initTreeState() {
        TreeNodeState state = getViewScope("treeState");
        if (state == null) {
            state = TreeNodeState.builder().build();
        }
        state.setEdit(false);
        state.setExpandAll(true);
        putViewScope("treeState", state);
    }

    public TreeNode getTreeModel() {
        TreeDataModel treeModel = getViewScope("treeModel");
        TreeNodeState state = getViewScope("treeState");
        putPath(state);
        return resourceFaces.initNodeTree(treeModel, state);
    }

    public Collection<AuthResourceVo> getButtonList() {
        TreeNodeState state = getViewScope("treeState");
        if (ArrayUtil.isNotEmpty(state.getSelectedNodes())) {
            TreeNode selectedNode = state.getSelectedNodes()[0];
            return resourceFaces.findButton(selectedNode);
        }
        return null;
    }

    public void add() {
        ResourceType resourceType = getParams("resourceType");
        TreeNodeState state = getViewScope("treeState");

        putViewScope("vo", new AuthResourceVo(resourceType));
        putViewScope("treeState", TreeNodeState.builder().selectedNodes(state.getSelectedNodes())
                .edit(Boolean.FALSE).expandAll(Boolean.FALSE).build());
        putViewScope("rootTree", getTreeModel());
    }

    public void edit() {
        TreeNode selectedNode = getViewScope("selectedNode");
        AuthResourceVo selected = getViewScope("sinSelected");
        ResourceType resourceType = getParams("resourceType");

        AuthResourceVo data = Objects.equals(ResourceType.BUTTON, resourceType) ? selected : (AuthResourceVo) selectedNode.getData();
        AuthResourceVo vo = resourceFaces.getOptById(data.getId())
                .orElseThrow(() -> new MessageResolvableException("error.dataNotFound"));

        putViewScope("vo", vo);
        putViewScope("treeState", TreeNodeState.builder().selectedNodes(new TreeNode[] {selectedNode})
                .edit(!Objects.equals(ResourceType.BUTTON, resourceType)).expandAll(Boolean.FALSE).build());
        putViewScope("rootTree", getTreeModel());
    }


    @MessageRequired(type = MessageType.SAVE)
    public void save() {
        AuthResourceVo vo = getViewScope("vo");
        String path = getViewScope("path");
        if (Objects.equals(ResourceType.BUTTON, vo.getType())) {
            vo.setPerms(path + vo.getCode());
        } else {
            vo.setPath(path + vo.getCode());
        }
        resourceFaces.saveOrUpdate(vo);
        onEntry();
    }

    @MessageRequired(type = MessageType.DELETE)
    public void delete() {
        ResourceType resourceType = getOptParams("resourceType")
                .map(params -> EnumUtil.likeValueOf(ResourceType.class, params)).orElse(ResourceType.BUTTON);
        AuthResourceVo selected;
        List<AuthResourceVo> selectedList = Lists.newArrayList();
        if (Objects.equals(ResourceType.BUTTON, resourceType)) {
            selected = getViewScope("sinSelected");
            selectedList = getViewScope("mulSelected");
        } else {
            TreeNode selectedNode = getViewScope("selectedNode");
            selected = (AuthResourceVo) selectedNode.getData();
        }
        AuthResourceVo[] delVos = CollUtil.defaultIfEmpty(selectedList, Lists.newArrayList(selected))
                .toArray(new AuthResourceVo[0]);
        resourceFaces.remove(delVos);
        onEntry();
    }

    public void cellEdit(CellEditEvent<AuthResourceVo> event) {
        resourceFaces.updateByFiled(event.getRowKey(), event.getColumn().getField(), event.getNewValue());
    }

    public void onNodeSelect(NodeSelectEvent event) {
        TreeNode selectedNode = event.getTreeNode();
        putViewScope("treeState", TreeNodeState.builder().selectedNodes(new TreeNode[] {selectedNode}).build());
        putViewScope("list", getButtonList());
    }

    public void onNodeUnselect(NodeUnselectEvent event) {
        putViewScope("treeState", TreeNodeState.builder().build());
        putViewScope("list", null);
    }

    public void onParentSelect(NodeSelectEvent event) {
        TreeNode selectedNode = event.getTreeNode();
        TreeNodeState state = getViewScope("treeState");
        state.setSelectedNodes(new TreeNode[] {selectedNode});
        putPath(state);
    }

    public void onParentUnselect(NodeUnselectEvent event) {
        TreeNodeState state = getViewScope("treeState");
        state.setSelectedNodes(null);
        putPath(state);
    }

    public void onRowReorder(ReorderEvent event) {
        TreeNode selectedNode = getViewScope("selectedNode");
        List<AuthResourceVo> list = getViewScope("list");
        resourceFaces.updateBySort(list, selectedNode.getRowKey());
    }


    private void putPath(TreeNodeState state) {
        AuthResourceVo vo = getViewScope("vo");
        String path = StrUtil.SLASH;
        if (ArrayUtil.isNotEmpty(state.getSelectedNodes())) {
            TreeNode selectedNode = state.getSelectedNodes()[0];
            AuthResourceVo target = (AuthResourceVo) selectedNode.getData();
            if (state.isEdit()) {
                AuthResourceVo parent = selectedNode.getParent() != null ? (AuthResourceVo) selectedNode.getParent().getData() : null;
                path = parent == null ? StrUtil.SLASH : StrUtil.appendIfMissing(parent.getPath(), StrUtil.SLASH);
            } else {
                if (Objects.equals(ResourceType.BUTTON, vo.getType())) {
                    path = StrUtil.appendIfMissing(target.getCode(), StrUtil.COLON);
                } else {
                    path = StrUtil.appendIfMissing(target.getPath(), StrUtil.SLASH);
                }
            }
        }
        putViewScope("path", path);
    }

    private ResourceType getParams(String key) {
        return getOptParams(key)
                .map(params -> EnumUtil.likeValueOf(ResourceType.class, params)).orElse(ResourceType.BUTTON);
    }

}