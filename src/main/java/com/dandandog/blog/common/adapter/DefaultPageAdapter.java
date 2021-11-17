package com.dandandog.blog.common.adapter;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dandandog.framework.mybatis.entity.BaseEntity;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/8/5 14:26
 */
public class DefaultPageAdapter<T extends BaseEntity> extends AbstractPageAdapter<T> {

    @Override
    public void conditions(QueryWrapper<T> queryWrapper) {

    }

}
