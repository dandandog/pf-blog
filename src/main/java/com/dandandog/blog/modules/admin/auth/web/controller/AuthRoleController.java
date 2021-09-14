package com.dandandog.blog.modules.admin.auth.web.controller;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dandandog.blog.common.adapter.DefaultTreeAdapter;
import com.dandandog.blog.common.model.MapperPageDataModel;
import com.dandandog.blog.modules.admin.auth.entity.AuthResource;
import com.dandandog.blog.modules.admin.auth.web.faces.AuthRoleFaces;
import com.dandandog.blog.modules.admin.auth.web.faces.adapter.AuthRolePageAdapter;
import com.dandandog.blog.modules.admin.auth.web.faces.vo.AuthRoleVo;
import com.dandandog.framework.faces.annotation.MessageRequired;
import com.dandandog.framework.faces.annotation.MessageSeverity;
import com.dandandog.framework.faces.annotation.MessageType;
import com.dandandog.framework.faces.controller.FacesController;
import com.dandandog.framework.faces.exception.MessageResolvableException;
import com.dandandog.framework.mapstruct.model.MapperVo;
import com.google.common.collect.Lists;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.TreeNode;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author JohnnyLiu
 */
@Controller("/admin/auth/role.faces")
public class AuthRoleController extends FacesController {

    @Resource
    private AuthRoleFaces roleFaces;

    @Override
    public void onEntry() {
        putViewScope("adapter", new DefaultTreeAdapter<>(AuthResource.class));

        putViewScope("vo", new AuthRoleVo());
        putViewScope("sinSelected", null);
        putViewScope("mulSelected", Lists.newArrayList());
        putViewScope("dataModel", MapperPageDataModel.getInstance(new AuthRolePageAdapter(), AuthRoleVo.class));
    }

    public LazyDataModel<AuthRoleVo> getDataModel() {
        return getViewScope("dataModel");
    }

    public void add() {
        putViewScope("vo", new AuthRoleVo());
        putViewScope("rootTree", getTreeModel());
    }

    @MessageRequired(type = MessageType.OPERATION, severity = MessageSeverity.ERROR)
    public void edit() {
        AuthRoleVo selected = getViewScope("sinSelected");
        AuthRoleVo vo = roleFaces.getOptById(selected.getId())
                .orElseThrow(() -> new MessageResolvableException("error", "dataNotFound"));
        putViewScope("vo", vo);
        putViewScope("rootTree", getTreeModel(vo.getResources().toArray(new AuthResource[0])));
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
                .stream().map(MapperVo::getId).toArray(String[]::new);
        roleFaces.removeByIds(idList);
    }

    private TreeNode getTreeModel(AuthResource... selected) {
        DefaultTreeAdapter<AuthResource> treeAdapter = getViewScope("adapter");
        return treeAdapter.getRootTree(Wrappers.emptyWrapper(), true, null, selected);
    }
}
