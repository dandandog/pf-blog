package com.dandandog.common.utils;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.dandandog.framework.common.model.ITree;
import com.dandandog.framework.common.model.IVo;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import org.primefaces.model.TreeNode;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public static <T extends IVo> String[] selectedId(TreeNode[] mulSelected, TreeNode... sinSelected) {
        TreeNode[] newArr = ArrayUtil.append(mulSelected, sinSelected);
        List<T> selectedList = Optional.ofNullable(newArr).map(treeNodes ->
                Arrays.stream(treeNodes)
                        .map(TreeNode::getData)
                        .map(o -> ((T) o))
                        .collect(Collectors.toList())).orElse(Lists.newArrayList());
        return selectedList.stream().map(IVo::getId).toArray(String[]::new);
    }


}
