package com.dandandog.blog.common.model;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dandandog.framework.common.model.ITree;
import com.dandandog.framework.core.utils.MybatisUtil;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author JohnnyLiu
 */
@Slf4j
public class TreeDataModel<T extends ITree> {

    @Getter
    private ServiceImpl<? extends T, T> baseService;

    public TreeDataModel(Class<T> clazz) {
        try {
            this.baseService = MybatisUtil.getOneServiceByModelClass(clazz);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
    }

    private List<T> load(Wrapper<T> queryWrapper) {
        return getBaseService().list(queryWrapper);
    }

    public Multimap<T, T> getValue(Wrapper<T> queryWrapper) {
        List<T> sources = load(queryWrapper);
        Multimap<String, T> idMap = ArrayListMultimap.create();
        Multimap<T, T> objMap = ArrayListMultimap.create();
        sources.forEach(t -> {
            idMap.put(StrUtil.isNotBlank(t.getParentId()) ? t.getParentId() : null, t);
        });
        idMap.keySet().forEach(id -> {
            sources.forEach(t -> {
                if (ObjectUtil.equal(id, t.getId())) {
                    objMap.putAll(t, idMap.get(id));
                }
            });
        });
        objMap.putAll(null, idMap.get(null));

        return objMap;
    }
}
