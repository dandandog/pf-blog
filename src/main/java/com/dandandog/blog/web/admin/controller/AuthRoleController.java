package com.dandandog.blog.web.admin.controller;

import cn.hutool.core.collection.CollUtil;
import com.dandandog.blog.web.admin.faces.AuthResourceFaces;
import com.dandandog.blog.web.admin.faces.AuthRoleFaces;
import com.dandandog.blog.web.admin.faces.adapter.AuthRolePageAdapter;
import com.dandandog.blog.web.admin.faces.vo.AuthRoleVo;
import com.dandandog.common.model.MapperPageDataModel;
import com.dandandog.framework.common.model.IVo;
import com.dandandog.framework.faces.annotation.MessageRequired;
import com.dandandog.framework.faces.annotation.MessageSeverity;
import com.dandandog.framework.faces.annotation.MessageType;
import com.dandandog.framework.faces.controller.FacesController;
import com.dandandog.framework.faces.exception.MessageResolvableException;
import com.dandandog.framework.faces.model.tree.TreeConfig;
import com.dandandog.framework.faces.model.tree.TreeDataModel;
import com.dandandog.framework.mybatis.entity.BaseEntity;
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

    @Resource
    private AuthResourceFaces resourceFaces;

    @Override
    public void onEntry() {
        putViewScope("vo", new AuthRoleVo());
        putViewScope("sinSelected", null);
        putViewScope("mulSelected", Lists.newArrayList());
        putViewScope("dataModel", MapperPageDataModel.getInstance(new AuthRolePageAdapter(), AuthRoleVo.class));
    }

    public LazyDataModel<AuthRoleVo> getDataModel() {
        return getViewScope("dataModel");
    }

    public TreeNode getTreeDataModel(AuthRoleVo vo) {
        TreeDataModel dataModel = getViewScope("treeDataModel");
        TreeConfig params = new TreeConfig();
        if (dataModel == null) {
            dataModel = resourceFaces.findDataModel();
        }
        if (vo != null) {
            params.setSelected(vo.getResources().stream().map(BaseEntity::getId).toArray(String[]::new));
        }
        putViewScope("treeDataModel", dataModel);
        return dataModel.createRoot(params);
    }


    public void add() {
        putViewScope("vo", new AuthRoleVo());
        putViewScope("rootTree", getTreeDataModel(null));
    }

    @MessageRequired(type = MessageType.OPERATION, severity = MessageSeverity.ERROR)
    public void edit() {
        AuthRoleVo selected = getViewScope("sinSelected");
        AuthRoleVo vo = roleFaces.getOptById(selected.getId())
                .orElseThrow(() -> new MessageResolvableException("error.dataNotFound"));
        putViewScope("vo", vo);
        putViewScope("rootTree", getTreeDataModel(vo));
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

}
