package com.dandandog.blog.web.admin.controller;

import cn.hutool.core.collection.CollUtil;
import com.dandandog.blog.web.admin.faces.AuthResourceFaces;
import com.dandandog.blog.web.admin.faces.AuthRoleFaces;
import com.dandandog.blog.web.admin.faces.vo.AuthResourceVo;
import com.dandandog.blog.web.admin.faces.vo.AuthRoleVo;
import com.dandandog.framework.common.model.IVo;
import com.dandandog.framework.faces.annotation.MessageRequired;
import com.dandandog.framework.faces.annotation.MessageType;
import com.dandandog.framework.faces.controller.FacesController;
import com.dandandog.framework.faces.exception.MessageResolvableException;
import com.dandandog.framework.faces.model.tree.TreeDataModel;
import com.dandandog.framework.faces.model.tree.TreeNodeState;
import com.google.common.collect.Lists;
import org.primefaces.event.AbstractAjaxBehaviorEvent;
import org.primefaces.event.CellEditEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.TreeNode;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.faces.model.SelectItem;
import java.util.List;

/**
 * @author JohnnyLiu
 */
@Controller("/admin/auth/role.faces")
public class AuthRoleController extends FacesController {

    @Resource
    private AuthRoleFaces roleFaces;

    @Resource
    private AuthResourceFaces resourceFaces;

    @Override
    public void onEntry() {
        initTreeState();
        putViewScope("vo", new AuthRoleVo());
        putViewScope("sinSelected", null);
        putViewScope("mulSelected", Lists.newArrayList());
        putViewScope("operates", Lists.newArrayList());
        putViewScope("dataModel", roleFaces.findDataModel());
    }


    public LazyDataModel<AuthRoleVo> getDataModel() {
        return getViewScope("dataModel");
    }

    public void initTreeState() {
        TreeNodeState state = getViewScope("treeState");
        if (state == null) {
            state = TreeNodeState.builder().build();
        }
        putViewScope("treeState", state);
    }

    public TreeNode getTreeDataModel() {
        TreeDataModel dataModel = getViewScope("treeDataModel");
        TreeNodeState state = getViewScope("treeState");
        return resourceFaces.initNodeTree(dataModel, state);
    }

    public void add() {
        putViewScope("vo", new AuthRoleVo());
        putViewScope("rootTree", getTreeDataModel());
    }

    public void edit() {
        AuthRoleVo selected = getViewScope("sinSelected");
        AuthRoleVo vo = roleFaces.getOptById(selected.getId())
                .orElseThrow(() -> new MessageResolvableException("error.dataNotFound"));
        putViewScope("vo", vo);
    }

    public void auth() {
        List<AuthRoleVo> selectedList = getViewScope("mulSelected");
        AuthRoleVo selected = CollUtil.isNotEmpty(selectedList) ? selectedList.get(0) : new AuthRoleVo();
        AuthRoleVo vo = roleFaces.getOptById(selected.getId())
                .orElseThrow(() -> new MessageResolvableException("error.dataNotFound"));
        putViewScope("vo", vo);

    }

    private void putAuth(AuthRoleVo selected) {


        putViewScope("treeState", TreeNodeState.builder().selectedNodes(selected.getAccesses()).build());
        putViewScope("rootTree", getTreeDataModel());
        putViewScope("sinSelected", selected);
    }


    @MessageRequired(type = MessageType.SAVE)
    public void save() {
        AuthRoleVo vo = getViewScope("vo");
        roleFaces.saveOrUpdate(vo);
    }

    @MessageRequired(type = MessageType.DELETE)
    public void delete() {
        AuthRoleVo selected = getViewScope("sinSelected");
        List<AuthRoleVo> selectedList = getViewScope("mulSelected");
        String[] idList = CollUtil.defaultIfEmpty(selectedList, Lists.newArrayList(selected))
                .stream().map(IVo::getId).toArray(String[]::new);
        roleFaces.removeByIds(idList);
    }

    public void cellEdit(CellEditEvent<AuthResourceVo> event) {
        roleFaces.updateByFiled(event.getRowKey(), event.getColumn().getField(), event.getNewValue());
    }

    public void onSelect(AbstractAjaxBehaviorEvent event) {
        AuthRoleVo selected = getViewScope("sinSelected");
        List<SelectItem> operates = roleFaces.findOperates(selected.getAccesses());
        putViewScope("operates", operates);
    }


}
