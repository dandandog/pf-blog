package com.dandandog.blog.modules.system.web.controller;


import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dandandog.blog.common.adapter.DefaultTreeAdapter;
import com.dandandog.blog.common.utils.IconUtil;
import com.dandandog.blog.modules.system.entity.AuthResource;
import com.dandandog.blog.modules.system.entity.enums.ResourceTarget;
import com.dandandog.blog.modules.system.entity.enums.ResourceType;
import com.dandandog.blog.modules.system.web.facet.AuthResourceFaces;
import com.dandandog.blog.modules.system.web.facet.vo.AuthResourceVo;
import com.dandandog.framework.core.entity.BaseEntity;
import com.dandandog.framework.faces.annotation.MessageRequired;
import com.dandandog.framework.faces.annotation.MessageSeverity;
import com.dandandog.framework.faces.annotation.MessageType;
import com.dandandog.framework.faces.controller.FacesController;
import com.dandandog.framework.faces.exception.MessageResolvableException;
import com.dandandog.framework.mapstruct.MapperUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.primefaces.event.CellEditEvent;
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
@Slf4j
@Controller("/system/auth/resource.faces")
public class AuthResourceController extends FacesController {

    /**
     * 服务对象
     */
    @Resource
    private AuthResourceFaces resourceFaces;

    @Override
    public void onEntry() {
        putViewScope("adapter", new DefaultTreeAdapter<>(AuthResource.class));
        putViewScope("root", getDataModel(new LambdaQueryWrapper<AuthResource>().orderByAsc(AuthResource::getSeq), null));

        putViewScope("vo", new AuthResourceVo());
        putViewScope("sinSelected", null);
        putViewScope("mulSelected", new TreeNode[0]);

        putViewScope("types", ResourceType.values());
        putViewScope("targets", ResourceTarget.values());
        putViewScope("icons", IconUtil.findAll());
    }

    public TreeNode getDataModel(Wrapper<AuthResource> queryWrapper, AuthResource current, AuthResource... selected) {
        DefaultTreeAdapter<AuthResource> treeAdapter = getViewScope("adapter");
        return treeAdapter.getRootTree(queryWrapper, true, current, selected);
    }

    public void add() {
        AuthResourceVo vo = new AuthResourceVo();
        putNodeView(vo);
    }

    @MessageRequired(type = MessageType.OPERATION, severity = MessageSeverity.ERROR)
    public void edit() {
        AuthResource selected = getViewScope("sinSelected");
        AuthResourceVo vo = resourceFaces.getOptById(selected.getId())
                .orElseThrow(() -> new MessageResolvableException("error", "dataNotFound"));
        putNodeView(vo);
    }

    @MessageRequired(type = MessageType.SAVE)
    public void save() {
        AuthResourceVo vo = getViewScope("vo");
        resourceFaces.saveOrUpdate(vo);
        onEntry();
    }

    @MessageRequired(type = MessageType.DELETE)
    public void delete() {
        AuthResource selected = getViewScope("sinSelected");
        TreeNode[] mulSelected = getViewScope("mulSelected");

        List<AuthResource> selectedList = Optional.ofNullable(mulSelected).map(treeNodes ->
                Arrays.stream(treeNodes)
                        .map(TreeNode::getData)
                        .map(o -> ((AuthResource) o))
                        .collect(Collectors.toList())).orElse(Lists.newArrayList());

        String[] idList = CollUtil.defaultIfEmpty(selectedList, Lists.newArrayList(selected))
                .stream().map(BaseEntity::getId).toArray(String[]::new);
        resourceFaces.removeByIds(idList);
        onEntry();
    }

    public void cellEdit(CellEditEvent<AuthResourceVo> event) {
        resourceFaces.updateByFiled(event.getRowKey(), event.getColumn().getField(), event.getNewValue());
    }

    private void putNodeView(AuthResourceVo vo) {
        Wrapper<AuthResource> wrapper = new LambdaQueryWrapper<AuthResource>().ne(AuthResource::getType, ResourceType.BUTTON).orderByAsc(AuthResource::getSeq);
        AuthResource node = MapperUtil.map(vo, AuthResource.class);
        putViewScope("vo", vo);
        putViewScope("rootTree", getDataModel(wrapper, node, (AuthResource) vo.getParent().getData()));
    }
}