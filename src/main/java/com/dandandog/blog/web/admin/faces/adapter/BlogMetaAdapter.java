package com.dandandog.blog.web.admin.faces.adapter;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dandandog.common.adapter.DefaultPageAdapter;
import com.dandandog.modules.blog.entity.BlogMeta;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/10 11:46
 */
public class BlogMetaAdapter extends DefaultPageAdapter<BlogMeta> {

    private final String parentId;

    public BlogMetaAdapter(String parentId) {
        this.parentId = parentId;
    }

    @Override
    public void conditions(QueryWrapper<BlogMeta> queryWrapper) {
        queryWrapper.lambda().eq(StrUtil.isNotBlank(parentId), BlogMeta::getParentId, parentId);
    }

}
