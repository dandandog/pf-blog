package com.dandandog.blog.modules.admin.content.web.faces.adapter;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dandandog.blog.common.adapter.AbstractPageAdapter;
import com.dandandog.blog.modules.admin.content.entity.BlogContents;
import com.dandandog.blog.modules.admin.content.entity.enums.ContentType;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/10 18:04
 */
public class ContentAdapter extends AbstractPageAdapter<BlogContents> {

    @Override
    public void conditions(QueryWrapper<BlogContents> queryWrapper) {
        queryWrapper.lambda().eq(BlogContents::getType, ContentType.POST);
    }
}
