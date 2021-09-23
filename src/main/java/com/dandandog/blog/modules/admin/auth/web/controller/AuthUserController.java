package com.dandandog.blog.modules.admin.auth.web.controller;

import cn.hutool.core.collection.CollUtil;
import com.dandandog.blog.common.model.MapperPageDataModel;
import com.dandandog.blog.modules.admin.auth.entity.enums.UserGender;
import com.dandandog.blog.modules.admin.auth.entity.enums.UserStatus;
import com.dandandog.blog.modules.admin.auth.entity.enums.UserType;
import com.dandandog.blog.modules.admin.auth.web.faces.AuthUserFaces;
import com.dandandog.blog.modules.admin.auth.web.faces.adapter.AuthUserPageAdapter;
import com.dandandog.blog.modules.admin.auth.web.faces.vo.AuthUserVo;
import com.dandandog.framework.faces.annotation.MessageRequired;
import com.dandandog.framework.faces.annotation.MessageSeverity;
import com.dandandog.framework.faces.annotation.MessageType;
import com.dandandog.framework.faces.controller.FacesController;
import com.dandandog.framework.faces.exception.MessageResolvableException;
import com.dandandog.framework.mapstruct.model.MapperVo;
import com.google.common.collect.Lists;
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


    @Override
    public void onEntry() {
        putViewScope("vo", new AuthUserVo());
        putViewScope("dataModel", MapperPageDataModel.getInstance(new AuthUserPageAdapter(), AuthUserVo.class));
        putViewScope("sinSelected", null);
        putViewScope("mulSelected", Lists.newArrayList());

        putViewScope("genders", UserGender.values());
        putViewScope("states", UserStatus.values());
        putViewScope("types", UserType.values());
    }

    public LazyDataModel<AuthUserVo> getDataModel() {
        return getViewScope("dataModel");
    }

    @MessageRequired(type = MessageType.OPERATION, severity = MessageSeverity.ERROR)
    public void edit() {
        AuthUserVo selected = getViewScope("sinSelected");
        AuthUserVo vo = userFacet.getOptById(selected.getId())
                .orElseThrow(() -> new MessageResolvableException("error", "dataNotFound"));
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
                .stream().map(MapperVo::getId).toArray(String[]::new);
        userFacet.removeByIds(idList);
    }

}