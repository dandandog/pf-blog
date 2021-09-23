package com.dandandog.blog.modules.admin.auth.web.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dandandog.blog.common.adapter.DefaultTreeAdapter;
import com.dandandog.blog.common.utils.IconUtil;
import com.dandandog.blog.common.utils.TreeUtil;
import com.dandandog.blog.modules.admin.auth.entity.AuthResource;
import com.dandandog.blog.modules.admin.auth.entity.enums.ResourceTarget;
import com.dandandog.blog.modules.admin.auth.entity.enums.ResourceType;
import com.dandandog.blog.modules.admin.auth.web.faces.AuthResourceFaces;
import com.dandandog.blog.modules.admin.auth.web.faces.vo.AuthResourceVo;
import com.dandandog.framework.faces.annotation.MessageRequired;
import com.dandandog.framework.faces.annotation.MessageSeverity;
import com.dandandog.framework.faces.annotation.MessageType;
import com.dandandog.framework.faces.controller.FacesController;
import com.dandandog.framework.faces.exception.MessageResolvableException;
import com.dandandog.framework.mapstruct.MapperUtil;
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
        String[] idList = TreeUtil.selectedId(mulSelected, selected);
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