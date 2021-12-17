package com.dandandog.blog.web.admin.faces.adapter;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dandandog.common.adapter.AbstractPageAdapter;
import com.dandandog.modules.blog.entity.BlogComment;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/16 15:23
 */
public class BlogCommentAdapter extends AbstractPageAdapter<BlogComment> {
    @Override
    public void conditions(QueryWrapper<BlogComment> queryWrapper) {
        queryWrapper.lambda().orderByDesc(BlogComment::getCreatedTime);
    }
}
