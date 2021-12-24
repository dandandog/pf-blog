package com.dandandog.blog.web.admin.faces.adapter;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dandandog.common.adapter.AbstractTreeAdapter;
import com.dandandog.framework.mybatis.entity.AuditableEntity;
import com.dandandog.modules.config.entity.DictNode;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/12/17 16:42
 */
public class DictNodeAdapter extends AbstractTreeAdapter<DictNode> {
    @Override
    public void conditions(QueryWrapper<DictNode> queryWrapper) {
        queryWrapper.lambda().orderByAsc(DictNode::getSeq).orderByAsc(AuditableEntity::getCreatedTime);
    }
}
