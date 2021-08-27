package com.dandandog.blog.common.adapter;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dandandog.framework.common.model.IEntity;
import com.dandandog.framework.core.entity.BaseEntity;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

import java.util.Map;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/8/5 14:26
 */
public class DefaultPageAdapter<T extends BaseEntity> extends AbstractPageAdapter<T> {

    @Override
    public void conditions(QueryWrapper<T> queryWrapper, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {

    }

}
