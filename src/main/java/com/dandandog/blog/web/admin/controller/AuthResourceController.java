package com.dandandog.blog.web.admin.controller;


import com.dandandog.blog.web.admin.faces.AuthResourceFaces;
import com.dandandog.blog.web.admin.faces.vo.AuthResourceVo;
import com.dandandog.common.utils.IconUtil;
import com.dandandog.common.utils.TreeUtil;
import com.dandandog.framework.faces.annotation.MessageRequired;
import com.dandandog.framework.faces.annotation.MessageType;
import com.dandandog.framework.faces.controller.FacesController;
import com.dandandog.framework.faces.exception.MessageResolvableException;
import com.dandandog.framework.faces.model.tree.TreeConfig;
import com.dandandog.framework.faces.model.tree.TreeDataModel;
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
        putViewScope("rootTable", getDataModel(null));

        putViewScope("vo", new AuthResourceVo());
        putViewScope("sinSelected", null);
        putViewScope("mulSelected", new TreeNode[0]);

        putViewScope("types", ResourceType.values());
        putViewScope("targets", ResourceTarget.values());
        putViewScope("icons", IconUtil.findAll());
    }


    public TreeNode getDataModel(AuthResourceVo vo) {
        TreeDataModel dataModel = getViewScope("dataModel");
        TreeConfig params = new TreeConfig();
        if (dataModel == null) {
            dataModel = resourceFaces.findDataModel();
        }
        if (vo != null) {
            params.setUnSelectable(new String[] {vo.getId()});
            params.setSelected(new String[] {vo.getParentId()});
        }
        putViewScope("dataModel", dataModel);
        return dataModel.createRoot(params);
    }


    public void add() {
        AuthResourceVo vo = new AuthResourceVo();
        putViewScope("vo", vo);
        putViewScope("rootTree", getDataModel(null));
    }

    public void edit() {
        AuthResourceVo selected = getViewScope("sinSelected");
        AuthResourceVo vo = resourceFaces.getOptById(selected.getId())
                .orElseThrow(() -> new MessageResolvableException("error.dataNotFound"));
        putViewScope("vo", vo);
        putViewScope("rootTree", getDataModel(vo));
    }

    @MessageRequired(type = MessageType.SAVE)
    public void save() {
        AuthResourceVo vo = getViewScope("vo");
        resourceFaces.saveOrUpdate(vo);
        onEntry();
    }

    @MessageRequired(type = MessageType.DELETE)
    public void delete() {
        AuthResourceVo selected = getViewScope("sinSelected");
        TreeNode[] mulSelected = getViewScope("mulSelected");
        String[] idList = TreeUtil.selectedId(mulSelected, selected);
        resourceFaces.removeByIds(idList);
        onEntry();
    }

    public void cellEdit(CellEditEvent<AuthResourceVo> event) {
        resourceFaces.updateByFiled(event.getRowKey(), event.getColumn().getField(), event.getNewValue());
    }

}