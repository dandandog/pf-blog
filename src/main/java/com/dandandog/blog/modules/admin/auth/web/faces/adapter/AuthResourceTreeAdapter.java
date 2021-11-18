package com.dandandog.blog.modules.admin.auth.web.faces.adapter;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dandandog.blog.common.adapter.AbstractTreeAdapter;
import com.dandandog.blog.modules.admin.auth.entity.AuthResource;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/11/18 10:01
 */
public class AuthResourceTreeAdapter extends AbstractTreeAdapter<AuthResource> {
    @Override
    public void conditions(QueryWrapper<AuthResource> queryWrapper) {
        queryWrapper.lambda().orderByAsc(AuthResource::getSeq);
    }
}
