package com.dandandog.blog.modules.content.web.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dandandog.blog.common.adapter.DefaultTreeAdapter;
import com.dandandog.blog.common.utils.TreeUtil;
import com.dandandog.blog.modules.content.entity.BlogMetas;
import com.dandandog.blog.modules.content.entity.enums.MetaType;
import com.dandandog.blog.modules.content.web.faces.MetasFaces;
import com.dandandog.blog.modules.content.web.faces.vo.CategoryVo;
import com.dandandog.framework.core.entity.AuditableEntity;
import com.dandandog.framework.faces.annotation.MessageRequired;
import com.dandandog.framework.faces.annotation.MessageType;
import com.dandandog.framework.faces.controller.FacesController;
import com.dandandog.framework.faces.exception.MessageResolvableException;
import com.dandandog.framework.mapstruct.MapperUtil;
import com.dandandog.framework.mapstruct.model.MapperVo;
import org.primefaces.model.TreeNode;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * 元数据(BlogMetas)表控制层
 *
 * @author Johnny
 * @since 2021-09-09 14:41:00
 */
@Controller("/content/category.faces")
public class ContentCategoryController extends FacesController {


    @Resource
    private MetasFaces metasFaces;


    @Override
    public void onEntry() {
        putViewScope("root", buildTree(null));

        putViewScope("vo", new CategoryVo());
        putViewScope("sinSelected", null);
        putViewScope("mulSelected", new TreeNode[0]);
    }

    public void add() {
        CategoryVo vo = new CategoryVo();
        putViewScope("vo", vo);
        putViewScope("rootTree", buildTree(vo));
    }

    public void edit() {
        BlogMetas selected = getViewScope("sinSelected");
        MapperVo vo = metasFaces.getOptById(selected.getId(), MetaType.CATEGORY)
                .orElseThrow(() -> new MessageResolvableException("error", "dataNotFound"));
        putViewScope("vo", vo);
        putViewScope("rootTree", buildTree((CategoryVo) vo));
    }

    @MessageRequired(type = MessageType.SAVE)
    public void save() {
        CategoryVo vo = getViewScope("vo");
        metasFaces.saveOrUpdate(vo);
        onEntry();
    }

    @MessageRequired(type = MessageType.DELETE)
    public void delete() {
        BlogMetas selected = getViewScope("sinSelected");
        TreeNode[] mulSelected = getViewScope("mulSelected");
        String[] idList = TreeUtil.selectedId(mulSelected, selected);
        metasFaces.removeByIds(idList);
        onEntry();
    }

    private TreeNode buildTree(CategoryVo vo) {
        DefaultTreeAdapter<BlogMetas> adapter = getViewScope("adapter");
        if (adapter == null) {
            adapter = new DefaultTreeAdapter<>(BlogMetas.class);
            putViewScope("adapter", adapter);
        }
        return metasFaces.findDataModel(adapter, vo);
    }


}
