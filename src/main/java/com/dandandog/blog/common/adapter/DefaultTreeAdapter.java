package com.dandandog.blog.common.adapter;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dandandog.blog.common.model.TreeDataModel;
import com.dandandog.framework.common.model.ITree;
import com.dandandog.framework.core.entity.BaseEntity;
import com.google.common.collect.Multimap;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.menu.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/5/7 15:39
 */
public class DefaultTreeAdapter<T extends ITree> {

    public Class<T> clazz;

    public DefaultTreeAdapter(Class<T> clazz) {
        this.clazz = clazz;
    }

    @SafeVarargs
    public final TreeNode getRootTree(boolean isExpand, T current, T... selected) {
        return getRootTree(Wrappers.emptyWrapper(), isExpand, current, selected);
    }

    @SafeVarargs
    public final TreeNode getRootTree(Wrapper<T> queryWrapper, boolean isExpand, T current, T... selected) {
        TreeDataModel<T> treeDataModel = new TreeDataModel<>(clazz);
        queryWrapper = Optional.ofNullable(queryWrapper).orElse(new LambdaQueryWrapper<>());
        Multimap<T, T> sources = treeDataModel.getValue(queryWrapper);
        TreeNode root = new DefaultTreeNode(null, null);
        setTreeLeaf(root, sources, isExpand, current, selected);
        return root;
    }

    @SafeVarargs
    public final void setTreeLeaf(TreeNode root, Multimap<T, T> resourceMaps, boolean isExpand, T current, T... selected) {
        root.setExpanded(isExpand);
        root.setSelectable(!(root.getData() != null && current != null && Objects.equals(current.getId(), ((T) root.getData()).getId())));
        root.setSelected(root.getData() != null && selected != null && Arrays.asList(selected).contains(root.getData()));
        if (root.getData() != null && !(root.getData() instanceof ITree)) {
            throw new RuntimeException("data is not extends ITree");
        }
        Collection<T> children = resourceMaps.removeAll(root.getData());
        if (children != null) {
            for (Object resource : children) {
                TreeNode node = new DefaultTreeNode(resource, root);
                setTreeLeaf(node, resourceMaps, isExpand, current, selected);
            }
        }
    }
}
