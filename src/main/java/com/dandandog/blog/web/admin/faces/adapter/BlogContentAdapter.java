package com.dandandog.blog.web.admin.faces.adapter;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dandandog.common.adapter.AbstractPageAdapter;
import com.dandandog.modules.blog.entity.BlogContent;
import com.dandandog.modules.blog.entity.enums.ContentType;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/10 18:04
 */
public class BlogContentAdapter extends AbstractPageAdapter<BlogContent> {

    @Override
    public void conditions(QueryWrapper<BlogContent> queryWrapper) {
        queryWrapper.lambda().eq(BlogContent::getType, ContentType.POST);
    }
}
