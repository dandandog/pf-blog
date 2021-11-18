package com.dandandog.blog.modules.admin.content.web.faces.adapter;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dandandog.blog.common.adapter.AbstractPageAdapter;
import com.dandandog.blog.modules.admin.content.entity.BlogComments;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/16 15:23
 */
public class CommentAdapter extends AbstractPageAdapter<BlogComments> {
    @Override
    public void conditions(QueryWrapper<BlogComments> queryWrapper) {
        queryWrapper.lambda().orderByDesc(BlogComments::getCreatedTime);
    }
}
