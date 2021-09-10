package com.dandandog.blog.common.model;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.db.PageResult;
import com.dandandog.blog.common.adapter.AbstractPageAdapter;
import com.dandandog.framework.common.model.IEntity;
import com.dandandog.framework.core.entity.BaseEntity;
import com.dandandog.framework.mapstruct.FromToKey;
import com.dandandog.framework.mapstruct.IMapper;
import com.dandandog.framework.mapstruct.MapperUtil;
import com.dandandog.framework.mapstruct.context.BaseContext;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author JohnyLiu
 */
@Slf4j
public class MapperPageDataModel<F extends BaseEntity, T extends IEntity> extends LazyDataModel<T> {


    @Setter
    @Getter
    private AbstractPageAdapter<F> adapter;

    @Getter
    private final FromToKey fromToKey;

    @Getter
    private final BaseContext<T> context;


    private MapperPageDataModel(Class<F> fClass, Class<T> tClass, BaseContext<T> context) {
        fromToKey = new FromToKey(fClass, tClass);
        this.context = context;
    }

    public static <F extends BaseEntity, T extends IEntity> MapperPageDataModel<F, T> getInstance(AbstractPageAdapter<F> adapter) {
        return getInstance(adapter, null, null);
    }

    public static <F extends BaseEntity, T extends IEntity> MapperPageDataModel<F, T> getInstance(AbstractPageAdapter<F> adapter, Class<T> tClass) {
        return getInstance(adapter, tClass, null);
    }

    public static <F extends BaseEntity, T extends IEntity> MapperPageDataModel<F, T> getInstance(AbstractPageAdapter<F> adapter, Class<T> tClass, BaseContext<T> context) {
        Class<F> fClass = (Class<F>) ClassUtil.getTypeArgument(adapter.getClass(), 0);
        if (tClass == null) {
            tClass = (Class<T>) fClass;
        }
        MapperPageDataModel<F, T> dataModel = new MapperPageDataModel<>(fClass, tClass, context);
        dataModel.setAdapter(adapter);
        return dataModel;
    }

    @Override
    public final List<T> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        PageResult<F> result = adapter.queryPage(first, pageSize, sortBy, filterBy);
        this.setRowCount(result.getTotal());
        return mapperAll(result);
    }

    @Override
    public T getRowData(String rowKey) {
        F result = adapter.queryOne(rowKey);
        return mapperOne(result);
    }

    @Override
    public String getRowKey(T t) {
        return t.getId();
    }


    public List<T> mapperAll(Collection<F> list) {
        return list.stream().map(this::mapperOne).collect(Collectors.toList());
    }

    public T mapperOne(F one) {
        FromToKey fromToKey = getFromToKey();
        if (fromToKey.isFromToSame()) {
            return (T) one;
        }
        IMapper<F, T> mapper = MapperUtil.getMapper(fromToKey);
        return Optional.ofNullable(context).map(a -> mapper.mapContextTo(one, a)).orElse(mapper.mapTo(one));
    }
}
