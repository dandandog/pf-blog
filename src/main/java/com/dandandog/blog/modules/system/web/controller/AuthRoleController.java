package com.dandandog.blog.modules.system.web.controller;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dandandog.blog.common.model.MapperPageDataModel;
import com.dandandog.blog.modules.system.entity.AuthResource;
import com.dandandog.blog.modules.system.entity.AuthRole;
import com.dandandog.blog.modules.system.entity.AuthRoleResource;
import com.dandandog.blog.modules.system.service.AuthResourceService;
import com.dandandog.blog.modules.system.service.AuthRoleService;
import com.dandandog.blog.modules.system.service.AuthUserService;
import com.dandandog.blog.modules.system.web.facet.AuthRoleFaces;
import com.dandandog.blog.modules.system.web.facet.adapter.AuthRolePageAdapter;
import com.dandandog.blog.modules.system.web.facet.adapter.AuthUserPageAdapter;
import com.dandandog.blog.modules.system.web.facet.vo.AuthRoleVo;
import com.dandandog.blog.modules.system.web.facet.vo.AuthUserVo;
import com.dandandog.framework.core.entity.BaseEntity;
import com.dandandog.framework.core.utils.MybatisUtil;
import com.dandandog.framework.faces.annotation.MessageRequired;
import com.dandandog.framework.faces.annotation.MessageType;
import com.dandandog.framework.faces.controller.FacesController;
import com.dandandog.framework.faces.exception.MessageResolvableException;
import com.dandandog.framework.mapstruct.MapperUtil;
import com.dandandog.framework.mapstruct.context.BaseContext;
import com.dandandog.framework.mapstruct.model.MapperVo;
import com.google.common.collect.Lists;
import org.primefaces.model.CheckboxTreeNode;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.TreeNode;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author JohnnyLiu
 */
@Controller("/system/auth/role.faces")
public class AuthRoleController extends FacesController {

    @Resource
    private AuthRoleFaces roleFaces;

    @Resource
    private AuthResourceService resourceService;

    @Resource
    private AuthUserService userService;

    @Override
    public void onEntry() {
        putViewScope("vo", new AuthRoleVo());
        putViewScope("sinSelected", null);
        putViewScope("mulSelected", Lists.newArrayList());
    }

    public LazyDataModel<AuthRoleVo> getDataModel() {
        return MapperPageDataModel.getInstance(new AuthRolePageAdapter(), AuthRoleVo.class);
    }

    public void add() {
        putViewScope("vo", new AuthRoleVo());
        putViewScope("rootTree", resourceService.getRootTree(true));
    }

    @MessageRequired(type = MessageType.OPERATION)
    public void edit() {
        AuthRoleVo selected = getViewScope("sinSelected");
        AuthRoleVo vo = roleFaces.getOptById(selected.getId())
                .orElseThrow(() -> new MessageResolvableException("error", "dataNotFound"));
        putViewScope("vo", vo);
        putViewScope("rootTree", resourceService.getRootTree(true, vo.getResources().toArray(new AuthResource[0])));
    }

    @MessageRequired(type = MessageType.SAVE)
    public void save() {
        AuthRoleVo vo = getViewScope("vo");
        AuthRole entity = MapperUtil.map(vo, AuthRole.class);
        roleFaces.saveOrUpdate(entity, vo.getResources());

        userService.reloadUserRole();
    }

    @MessageRequired(type = MessageType.DELETE)
    public void delete() {
        AuthRoleVo selected = getViewScope("sinSelected");
        List<AuthRoleVo> selectedList = getViewScope("mulSelected");
        String[] idList = CollUtil.defaultIfEmpty(selectedList, Lists.newArrayList(selected))
                .stream().map(MapperVo::getId).toArray(String[]::new);
        roleFaces.removeByIds(idList);
    }

}
