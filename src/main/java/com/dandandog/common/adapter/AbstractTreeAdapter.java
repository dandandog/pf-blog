package com.dandandog.common.adapter;

import cn.hutool.core.util.ClassUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dandandog.framework.common.model.ITree;
import com.dandandog.framework.faces.model.tree.TreeFaces;
import com.dandandog.framework.mybatis.service.BaseServiceImpl;
import com.dandandog.framework.mybatis.utils.MybatisUtil;
import com.google.common.base.Objects;
import lombok.Getter;

import java.util.List;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/10/29 16:26
 */
public abstract class AbstractTreeAdapter<T extends ITree> {

    @Getter
    private final BaseServiceImpl<BaseMapper<T>, T> baseService;

    public AbstractTreeAdapter() {
        Class<T> tClass = (Class<T>) ClassUtil.getTypeArgument(this.getClass(), 0);
        baseService = MybatisUtil.getOneServiceByModelClass(tClass);
    }

    public final List<T> queryList() {
        QueryWrapper<T> queryWrapper = queryConditions();
        return finding(queryWrapper);
    }

    protected List<T> finding(QueryWrapper<T> queryWrapper) {
        return baseService.list(queryWrapper);
    }

    private QueryWrapper<T> queryConditions() {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        conditions(queryWrapper);
        return queryWrapper;
    }

    public abstract void conditions(QueryWrapper<T> queryWrapper);

    public <F extends TreeFaces> void updateLevel(String rowKey, F resource) {
        if (!Objects.equal(rowKey, resource.getLevel())) {
            resource.setLevel(rowKey);
            baseService.update(new UpdateWrapper<T>().set("level", rowKey).eq("id", resource.getId()));
        }
    }
}
