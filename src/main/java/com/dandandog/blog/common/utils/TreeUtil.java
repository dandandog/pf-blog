package com.dandandog.blog.common.utils;

import cn.hutool.core.util.StrUtil;
import com.dandandog.framework.common.model.ITree;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.List;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/3 15:07
 */
public class TreeUtil {

    public static <T extends ITree> Multimap<String, T> parentMap(List<T> sources) {
        Multimap<String, T> idMap = ArrayListMultimap.create();
        sources.forEach(t -> idMap.put(StrUtil.isNotBlank(t.getParentId()) ? t.getParentId() : null, t));
        return idMap;
    }


}
