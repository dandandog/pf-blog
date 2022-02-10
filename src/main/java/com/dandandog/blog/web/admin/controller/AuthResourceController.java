package com.dandandog.blog.web.admin.controller;


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
import org.primefaces.event.CellEditEvent;
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
        putViewScope("rootTable", getDataModel());

        putViewScope("vo", new AuthResourceVo());
        putViewScope("sinSelected", null);
        putViewScope("mulSelected", new TreeNode[0]);

        putViewScope("types", ResourceType.values());
        putViewScope("targets", ResourceTarget.values());
        putViewScope("icons", IconUtil.findAll());
    }

    public void initTreeState() {
        TreeNodeState state = getViewScope("treeState");
        if (state == null) {
            state = TreeNodeState.builder().build();
        }
        putViewScope("treeState", state);
    }

    public TreeNode getDataModel() {
        TreeDataModel dataModel = getViewScope("dataModel");
        TreeNodeState state = getViewScope("treeState");
        return resourceFaces.initNodeTree(dataModel, state);
    }

    public void add() {
        AuthResourceVo vo = new AuthResourceVo();
        putViewScope("vo", vo);
        putViewScope("rootTree", getDataModel());
    }

    public void edit() {
        TreeNode selectedNode = getViewScope("sinSelected");
        AuthResourceVo data = (AuthResourceVo) selectedNode.getData();
        AuthResourceVo vo = resourceFaces.getOptById(data.getId())
                .orElseThrow(() -> new MessageResolvableException("error.dataNotFound"));
        putViewScope("treeState", TreeNodeState.builder().selectedNodes(new TreeNode[] {selectedNode}).edit(true).build());
        putViewScope("vo", vo);
        putViewScope("rootTree", getDataModel());
    }

    @MessageRequired(type = MessageType.SAVE)
    public void save() {
        AuthResourceVo vo = getViewScope("vo");
        resourceFaces.saveOrUpdate(vo);
        onEntry();
    }

    @MessageRequired(type = MessageType.DELETE)
    public void delete() {
        TreeNode selected = getViewScope("sinSelected");
        TreeNode[] mulSelected = getViewScope("mulSelected");
        String[] idList = TreeUtil.selectedId(mulSelected, selected);
        resourceFaces.removeByIds(idList);
        onEntry();
    }

    public void cellEdit(CellEditEvent<AuthResourceVo> event) {
        resourceFaces.updateByFiled(event.getRowKey(), event.getColumn().getField(), event.getNewValue());
    }

}