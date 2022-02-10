package com.dandandog.blog.web.admin.controller;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dandandog.blog.web.admin.faces.BlogMetasFaces;
import com.dandandog.blog.web.admin.faces.vo.BlogTagVo;
import com.dandandog.framework.common.model.IVo;
import com.dandandog.framework.faces.annotation.MessageRequired;
import com.dandandog.framework.faces.annotation.MessageType;
import com.dandandog.framework.faces.controller.FacesController;
import com.dandandog.framework.faces.exception.MessageResolvableException;
import com.dandandog.framework.mybatis.entity.AuditableEntity;
import com.dandandog.modules.blog.entity.BlogMeta;
import com.dandandog.modules.blog.entity.enums.MetaType;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/10 9:46
 */
@Controller("/admin/content/tag.faces")
public class BlogTagController extends FacesController {


    @Resource
    private BlogMetasFaces metasFaces;


    @Override
    public void onEntry() {
        putViewScope("list", list());
        putViewScope("vo", new BlogTagVo());
        putViewScope("sinSelected", null);
        putViewScope("mulSelected", Lists.newArrayList());
    }

    public Collection<?> list() {
        Wrapper<BlogMeta> queryWrapper = new LambdaQueryWrapper<BlogMeta>().eq(BlogMeta::getType, MetaType.TAG)
                .orderByDesc(AuditableEntity::getOperatedTime);
        return metasFaces.list(queryWrapper, MetaType.TAG);
    }

    public void add() {
        BlogTagVo vo = new BlogTagVo();
        putViewScope("vo", vo);
    }

    public void edit() {
        BlogTagVo selected = getViewScope("sinSelected");
        IVo vo = metasFaces.getOptById(selected.getId(), MetaType.TAG)
                .orElseThrow(() -> new MessageResolvableException("error.dataNotFound"));
        putViewScope("vo", vo);
    }

    @MessageRequired(type = MessageType.SAVE)
    public void save() {
        IVo vo = getViewScope("vo");
        metasFaces.saveOrUpdate(vo);
        onEntry();
    }

    @MessageRequired(type = MessageType.DELETE)
    public void delete() {
        BlogTagVo selected = getViewScope("sinSelected");
        List<BlogTagVo> selectedList = getViewScope("mulSelected");
        String[] idList = CollUtil.defaultIfEmpty(selectedList, Lists.newArrayList(selected))
                .stream().map(IVo::getId).toArray(String[]::new);
        metasFaces.removeByIds(idList);
        onEntry();
    }
}
