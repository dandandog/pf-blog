package com.dandandog.blog.web.admin.faces.adapter;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dandandog.common.adapter.AbstractTreeAdapter;
import com.dandandog.framework.mybatis.entity.AuditableEntity;
import com.dandandog.modules.config.entity.ConfigDictNode;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/12/17 16:42
 */
public class DictNodeAdapter extends AbstractTreeAdapter<ConfigDictNode> {
    @Override
    public void conditions(QueryWrapper<ConfigDictNode> queryWrapper) {
        queryWrapper.lambda().orderByAsc(AuditableEntity::getCreatedTime);
    }
}
