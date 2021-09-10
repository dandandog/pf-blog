package com.dandandog.blog.modules.content.web.faces.adapter;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dandandog.blog.common.adapter.DefaultPageAdapter;
import com.dandandog.blog.modules.content.entity.BlogMetas;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.formula.functions.T;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

import java.util.Map;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/10 11:46
 */
public class MetasAdapter extends DefaultPageAdapter<BlogMetas> {

    private final String parentId;

    public MetasAdapter(String parentId) {
        this.parentId = parentId;
    }

    @Override
    public void conditions(QueryWrapper<BlogMetas> queryWrapper, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        queryWrapper.lambda().eq(StrUtil.isNotBlank(parentId), BlogMetas::getParentId, parentId);
    }

}
