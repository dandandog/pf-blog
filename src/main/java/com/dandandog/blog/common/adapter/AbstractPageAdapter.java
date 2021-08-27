package com.dandandog.blog.common.adapter;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.PageResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dandandog.framework.common.model.IEntity;
import com.dandandog.framework.core.service.impl.BaseServiceImpl;
import com.dandandog.framework.core.utils.MybatisUtil;
import com.dandandog.framework.faces.adapter.IAdapterKey;
import com.dandandog.framework.faces.adapter.IPageAdapter;
import com.dandandog.framework.mapstruct.FromToKey;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import lombok.Getter;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.MatchMode;
import org.primefaces.model.SortMeta;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/8/5 14:26
 */
public abstract class AbstractPageAdapter<T extends IEntity> extends IPageAdapter<T> {

    private final Class<T> tClass;

    @Getter
    private final BaseServiceImpl<BaseMapper<T>, T> baseService;


    public AbstractPageAdapter() {
        tClass = (Class<T>) ClassUtil.getTypeArgument(this.getClass(), 0);
        baseService = MybatisUtil.getOneServiceByModelClass(tClass);
    }

    @Override
    public final T queryOne(String id) {
        return baseService.getById(id);
    }

    @Override
    public final PageResult<T> queryPage(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        QueryWrapper<T> queryWrapper = queryConditions(sortBy, filterBy);
        IPage<T> page = paging(new Page<>(getPage(first, pageSize), pageSize), queryWrapper);
        PageResult<T> result = new PageResult<T>((int) page.getCurrent(), (int) page.getSize(), (int) page.getTotal());
        result.addAll(page.getRecords());
        return result;
    }

    public IPage<T> paging(Page<T> page, QueryWrapper<T> queryWrapper) {
        return baseService.page(page, queryWrapper);
    }


    public abstract void conditions(QueryWrapper<T> queryWrapper, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy);


    private QueryWrapper<T> queryConditions(Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        sortConditions(queryWrapper, sortBy);
        filterConditions(queryWrapper, filterBy);
        conditions(queryWrapper, sortBy, filterBy);
        return queryWrapper;
    }


    private int getPage(int first, int pageSize) {
        int pageNum = (pageSize > 0) ? (first / pageSize) : 0;
        return pageNum + 1;
    }

    private void sortConditions(QueryWrapper<T> queryWrapper, Map<String, SortMeta> sortBy) {
        Collection<SortMeta> sorts = CollUtil.defaultIfEmpty(sortBy.values(), Lists.newArrayList());
        for (SortMeta sortMeta : sorts) {
            String tableField = getTableField(sortMeta.getField());
            if (StrUtil.isEmpty(tableField)) {
                continue;
            }
            switch (sortMeta.getOrder()) {
                case ASCENDING:
                    if (StrUtil.isNotEmpty(tableField)) {
                        queryWrapper.orderByAsc(tableField);
                    }
                    break;
                case DESCENDING:
                    if (StrUtil.isNotEmpty(tableField)) {
                        queryWrapper.orderByDesc(tableField);
                    }
                    break;
                case UNSORTED:
                default:
                    break;
            }
        }
    }

    private void filterConditions(QueryWrapper<T> queryWrapper, Map<String, FilterMeta> filterBy) {
        Collection<FilterMeta> filters = CollUtil.defaultIfEmpty(filterBy.values(), Lists.newArrayList());
        for (FilterMeta filterMeta : filters) {
            if (ObjectUtil.isNotNull(filterMeta.getField())) {
                String tableField = getTableField(filterMeta.getField());
                if (StrUtil.isEmpty(tableField)) {
                    continue;
                }
                Collection<?> values = judgeClassType(filterMeta.getFilterValue());
                for (Object value : values) {
                    execute(queryWrapper, filterMeta.getMatchMode(), tableField, value);
                }
            }
        }
    }

    private Collection<?> judgeClassType(Object value) {
        if (value.getClass().isArray()) {
            return Arrays.stream((Object[]) value).collect(Collectors.toList());
        }

        if (value instanceof Collection) {
            return (Collection<?>) value;
        }

        return Lists.newArrayList(value);
    }

    private void execute(QueryWrapper<T> queryWrapper, MatchMode matchMode, String tableField, Object value) {
        switch (matchMode) {

            case STARTS_WITH:
                queryWrapper.likeLeft(ObjectUtil.isNotNull(value), tableField, value);
                break;
            case ENDS_WITH:
                queryWrapper.likeRight(ObjectUtil.isNotNull(value), tableField, value);
                break;
            case CONTAINS:
                queryWrapper.like(ObjectUtil.isNotNull(value), tableField, value);
                break;
            case LESS_THAN:
                queryWrapper.lt(ObjectUtil.isNotNull(value), tableField, value);
                break;
            case LESS_THAN_EQUALS:
                queryWrapper.le(ObjectUtil.isNotNull(value), tableField, value);
                break;
            case GREATER_THAN:
                queryWrapper.gt(ObjectUtil.isNotNull(value), tableField, value);
                break;
            case GREATER_THAN_EQUALS:
                queryWrapper.ge(ObjectUtil.isNotNull(value), tableField, value);
                break;
            case EQUALS:
                queryWrapper.eq(ObjectUtil.isNotNull(value), tableField, value);
                break;
            case IN:
                queryWrapper.in(ObjectUtil.isNotNull(value), tableField, value);
                break;
            case RANGE:
            case EXACT:
            case GLOBAL:
            default:
                break;
        }
    }

    private String getTableField(String fieldName) {
        if (fieldName == null) {
            return null;
        }
        Field field = ReflectUtil.getField(tClass, fieldName);
        if (field == null) {
            return null;
        }
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fieldName);
    }


}
