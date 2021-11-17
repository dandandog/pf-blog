package com.dandandog.blog.common.model;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dandandog.blog.common.utils.TreeUtil;
import com.dandandog.framework.faces.model.tree.TreeEntity;
import com.dandandog.framework.mybatis.utils.MybatisUtil;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author JohnnyLiu
 */
@Slf4j
public class TreeDataModel<T extends TreeEntity> {

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
        Multimap<String, T> idMap = TreeUtil.parentMap(sources);
        Multimap<T, T> objMap = ArrayListMultimap.create();
        idMap.keySet().forEach(id -> sources.forEach(t -> {
            if (ObjectUtil.equal(id, t.getId())) {
                objMap.putAll(t, idMap.get(id));
            }
        }));
        objMap.putAll(null, idMap.get(null));
        return objMap;
    }
}
