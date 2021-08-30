package com.dandandog.blog.modules.system.web.controller;


import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dandandog.blog.modules.system.entity.AuthResource;
import com.dandandog.blog.modules.system.entity.enums.ResourceTarget;
import com.dandandog.blog.modules.system.entity.enums.ResourceType;
import com.dandandog.blog.modules.system.service.AuthResourceService;
import com.dandandog.blog.modules.system.web.facet.vo.AuthResourceVo;
import com.dandandog.framework.core.entity.BaseEntity;
import com.dandandog.framework.faces.annotation.MessageRequired;
import com.dandandog.framework.faces.annotation.MessageType;
import com.dandandog.framework.faces.controller.FacesController;
import com.dandandog.framework.faces.exception.MessageResolvableException;
import com.dandandog.framework.mapstruct.MapperUtil;
import com.google.common.collect.Lists;
import org.primefaces.model.TreeNode;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 系统资源(SysResource)表控制层
 *
 * @author JohnnyLiu
 * @since 2020-09-06 22:06:06
 */
@Controller("/system/auth/resource.faces")
public class AuthResourceController extends FacesController {

    /**
     * 服务对象
     */
    @Resource
    private AuthResourceService resourceService;

    @Override
    public void onEntry() {
        putViewScope("root", resourceService.getRootTree(true));

        putViewScope("vo", new AuthResourceVo());
        putViewScope("sinSelected", null);
        putViewScope("mulSelected", new TreeNode[0]);

        putViewScope("types", ResourceType.values());
        putViewScope("targets", ResourceTarget.values());
    }

    public void add() {
        AuthResourceVo vo = new AuthResourceVo();
        putResourceView(vo);
    }

    @MessageRequired(type = MessageType.OPERATION)
    public void edit() {
        AuthResource selected = getViewScope("sinSelected");
        AuthResource target = Optional.ofNullable(resourceService.getById(selected.getId()))
                .orElseThrow(() -> new MessageResolvableException("error", "dataNotFound"));
        AuthResourceVo vo = MapperUtil.map(target, AuthResourceVo.class);
        putResourceView(vo);
    }

    @MessageRequired(type = MessageType.SAVE)
    public void save() {
        AuthResourceVo vo = getViewScope("vo");
        AuthResource entity = MapperUtil.map(vo, AuthResource.class);
        resourceService.saveOrUpdate(entity);
        onEntry();
    }

    @MessageRequired(type = MessageType.DELETE)
    public void delete() {
        AuthResource selected = getViewScope("sinSelected");
        TreeNode[] mulSelected = getViewScope("mulSelected");

        List<AuthResource> selectedList = Optional.ofNullable(mulSelected).map(treeNodes ->
                Arrays.stream(mulSelected)
                        .map(TreeNode::getData)
                        .map(o -> ((AuthResource) o))
                        .collect(Collectors.toList())).orElse(Lists.newArrayList());

        List<String> idList = CollUtil.defaultIfEmpty(selectedList, Lists.newArrayList(selected))
                .stream().map(BaseEntity::getId).collect(Collectors.toList());
        resourceService.removeByIds(idList);
        onEntry();
    }

    @MessageRequired(type = MessageType.OPERATION)
    public void onChangeStatus(AuthResource resource) {
        resourceService.updateById(resource);
        onEntry();
    }

    private void putResourceView(AuthResourceVo vo) {
        Wrapper<AuthResource> wrapper = new LambdaQueryWrapper<AuthResource>().ne(AuthResource::getType, ResourceType.BUTTON).orderByAsc(AuthResource::getSeq);
        putViewScope("vo", vo);
        putViewScope("rootTree", resourceService.getRootTree(wrapper, false, (AuthResource) vo.getParent().getData()));
    }
}