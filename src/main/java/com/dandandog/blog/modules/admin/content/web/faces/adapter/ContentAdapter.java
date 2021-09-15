package com.dandandog.blog.modules.admin.content.web.faces.adapter;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dandandog.blog.common.adapter.AbstractPageAdapter;
import com.dandandog.blog.common.adapter.DefaultPageAdapter;
import com.dandandog.blog.modules.admin.content.entity.BlogContents;
import com.dandandog.blog.modules.admin.content.entity.enums.ContentType;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

import java.util.Map;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/10 18:04
 */
public class ContentAdapter extends AbstractPageAdapter<BlogContents> {

    @Override
    public void conditions(QueryWrapper<BlogContents> queryWrapper, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        queryWrapper.lambda().eq(BlogContents::getType, ContentType.POST);
    }
}
