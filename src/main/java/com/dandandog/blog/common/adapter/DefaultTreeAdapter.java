package com.dandandog.blog.common.adapter;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dandandog.framework.mybatis.entity.BaseEntity;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/10/30 9:34
 */
public class DefaultTreeAdapter<T extends BaseEntity> extends AbstractTreeAdapter<T> {

    @Override
    public void conditions(QueryWrapper<T> queryWrapper) {

    }
}
