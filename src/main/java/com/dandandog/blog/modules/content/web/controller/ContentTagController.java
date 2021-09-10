package com.dandandog.blog.modules.content.web.controller;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dandandog.blog.common.adapter.DefaultTreeAdapter;
import com.dandandog.blog.common.utils.TreeUtil;
import com.dandandog.blog.modules.content.entity.BlogMetas;
import com.dandandog.blog.modules.content.entity.enums.MetaType;
import com.dandandog.blog.modules.content.web.faces.MetasFaces;
import com.dandandog.blog.modules.content.web.faces.vo.TagVo;
import com.dandandog.blog.modules.system.auth.entity.enums.ResourceType;
import com.dandandog.blog.modules.system.auth.web.faces.vo.AuthRoleVo;
import com.dandandog.framework.core.entity.AuditableEntity;
import com.dandandog.framework.faces.annotation.MessageRequired;
import com.dandandog.framework.faces.annotation.MessageType;
import com.dandandog.framework.faces.controller.FacesController;
import com.dandandog.framework.faces.exception.MessageResolvableException;
import com.dandandog.framework.mapstruct.MapperUtil;
import com.dandandog.framework.mapstruct.model.MapperVo;
import com.google.common.collect.Lists;
import org.primefaces.model.TreeNode;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/10 9:46
 */
@Controller("/content/tag.faces")
public class ContentTagController extends FacesController {


    @Resource
    private MetasFaces metasFaces;


    @Override
    public void onEntry() {
        putViewScope("list", list());
        putViewScope("vo", new TagVo());
        putViewScope("sinSelected", null);
        putViewScope("mulSelected", Lists.newArrayList());
    }

    public Collection<?> list() {
        Wrapper<BlogMetas> queryWrapper = new LambdaQueryWrapper<BlogMetas>().eq(BlogMetas::getType, MetaType.TAG)
                .orderByAsc(BlogMetas::getSeq).orderByDesc(AuditableEntity::getOperatedTime);
        return metasFaces.list(queryWrapper, MetaType.TAG);
    }

    public void add() {
        TagVo vo = new TagVo();
        putViewScope("vo", vo);
    }

    public void edit() {
        TagVo selected = getViewScope("sinSelected");
        MapperVo vo = metasFaces.getOptById(selected.getId(), MetaType.TAG)
                .orElseThrow(() -> new MessageResolvableException("error", "dataNotFound"));
        putViewScope("vo", vo);
    }

    @MessageRequired(type = MessageType.SAVE)
    public void save() {
        MapperVo vo = getViewScope("vo");
        metasFaces.saveOrUpdate(vo);
        onEntry();
    }

    @MessageRequired(type = MessageType.DELETE)
    public void delete() {
        TagVo selected = getViewScope("sinSelected");
        List<TagVo> selectedList = getViewScope("mulSelected");
        String[] idList = CollUtil.defaultIfEmpty(selectedList, Lists.newArrayList(selected))
                .stream().map(MapperVo::getId).toArray(String[]::new);
        metasFaces.removeByIds(idList);
        onEntry();
    }
}
