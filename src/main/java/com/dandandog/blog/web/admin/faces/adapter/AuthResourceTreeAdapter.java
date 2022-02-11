package com.dandandog.blog.web.admin.faces.adapter;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dandandog.common.adapter.AbstractTreeAdapter;
import com.dandandog.modules.auth.entity.AuthResource;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/11/18 10:01
 */
public class AuthResourceTreeAdapter extends AbstractTreeAdapter<AuthResource> {

    @Override
    public void conditions(QueryWrapper<AuthResource> queryWrapper) {
        queryWrapper.lambda().orderByAsc(AuthResource::getLevel);
    }
}
