package com.dandandog.blog.modules.admin.content.web.faces.adapter;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dandandog.blog.common.adapter.AbstractPageAdapter;
import com.dandandog.blog.modules.admin.content.entity.BlogComments;
import com.dandandog.blog.modules.admin.content.entity.BlogContents;
import com.dandandog.framework.core.entity.AuditableEntity;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

import java.util.Map;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/16 15:23
 */
public class CommentAdapter extends AbstractPageAdapter<BlogComments> {
    @Override
    public void conditions(QueryWrapper<BlogComments> queryWrapper, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        queryWrapper.lambda().orderByDesc(AuditableEntity::getCreatedTime);
    }
}
