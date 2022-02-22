package com.dandandog.blog.web.admin.controller;

import cn.hutool.core.collection.CollUtil;
import com.dandandog.blog.web.admin.faces.AuthRoleFaces;
import com.dandandog.blog.web.admin.faces.AuthUserFaces;
import com.dandandog.blog.web.admin.faces.vo.AuthRoleVo;
import com.dandandog.blog.web.admin.faces.vo.AuthUserVo;
import com.dandandog.framework.common.model.IVo;
import com.dandandog.framework.faces.annotation.MessageRequired;
import com.dandandog.framework.faces.annotation.MessageType;
import com.dandandog.framework.faces.controller.FacesController;
import com.dandandog.framework.faces.exception.MessageResolvableException;
import com.dandandog.modules.auth.entity.enums.UserState;
import com.dandandog.modules.auth.entity.enums.UserType;
import com.dandandog.modules.blog.entity.enums.GenderType;
import com.google.common.collect.Lists;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.LazyDataModel;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/8/25 15:12
 */
@Controller("/admin/auth/user.faces")
public class AuthUserController extends FacesController {

    @Resource
    private AuthUserFaces userFacet;

    @Resource
    private AuthRoleFaces roleFaces;


    @Override
    public void onEntry() {
        putViewScope("vo", new AuthUserVo());
        putViewScope("dataModel", userFacet.findDataModel(null));
        putViewScope("sinSelected", null);
        putViewScope("mulSelected", Lists.newArrayList());
        putViewScope("roles", roleFaces.list());

        putViewScope("genders", GenderType.values());
        putViewScope("statuses", UserState.values());
        putViewScope("types", UserType.values());
    }

    public LazyDataModel<AuthUserVo> getDataModel() {
        return getViewScope("dataModel");
    }

    public void add() {
        AuthUserVo vo = new AuthUserVo();
        vo.setType(UserType.USER);
        putViewScope("vo", vo);
    }

    public void edit() {
        AuthUserVo selected = getViewScope("sinSelected");
        AuthUserVo vo = userFacet.getOptById(selected.getId())
                .orElseThrow(() -> new MessageResolvableException("error.dataNotFound"));
        putViewScope("vo", vo);
    }

    @MessageRequired(type = MessageType.SAVE)
    public void save() {
        AuthUserVo vo = getViewScope("vo");
        userFacet.saveOrUpdate(vo);
    }

    @MessageRequired(type = MessageType.DELETE)
    public void delete() {
        AuthUserVo selected = getViewScope("sinSelected");
        List<AuthUserVo> selectedList = getViewScope("mulSelected");
        String[] idList = CollUtil.defaultIfEmpty(selectedList, Lists.newArrayList(selected))
                .stream().map(IVo::getId).toArray(String[]::new);
        userFacet.removeByIds(idList);
    }


    public void onRowSelect(SelectEvent<AuthRoleVo> event) {
        AuthRoleVo vo = event.getObject();
        putViewScope("dataModel", userFacet.findDataModel(vo.getId()));
    }

    public void onRowUnSelect(UnselectEvent<AuthRoleVo> event) {
        putViewScope("dataModel", userFacet.findDataModel(null));
    }


}
