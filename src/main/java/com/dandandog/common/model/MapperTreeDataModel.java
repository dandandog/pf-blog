package com.dandandog.common.model;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.dandandog.common.adapter.AbstractTreeAdapter;
import com.dandandog.framework.common.model.ITree;
import com.dandandog.framework.faces.model.tree.TreeDataModel;
import com.dandandog.framework.faces.model.tree.TreeFaces;
import com.dandandog.framework.faces.model.tree.TreeNodeState;
import com.dandandog.framework.faces.utils.TreeUtil;
import com.dandandog.framework.mapstruct.FromToKey;
import com.dandandog.framework.mapstruct.IMapper;
import com.dandandog.framework.mapstruct.context.BaseContext;
import com.dandandog.framework.mapstruct.utils.MapperUtil;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/10/29 17:22
 */
@SuppressWarnings("ALL")
public class MapperTreeDataModel<F extends ITree, T extends TreeFaces> implements TreeDataModel {

    @Setter
    @Getter
    private AbstractTreeAdapter<F> adapter;

    @Getter
    private final FromToKey fromToKey;

    @Getter
    private final BaseContext<T> context;


    private MapperTreeDataModel(Class<F> fClass, Class<T> tClass, BaseContext<T> context) {
        fromToKey = new FromToKey(fClass, tClass);
        this.context = context;
    }

    public static <F extends ITree, T extends TreeFaces> MapperTreeDataModel<F, T> getInstance(AbstractTreeAdapter<F> adapter, Class<T> tClass) {
        return getInstance(adapter, tClass, null);
    }

    public static <F extends ITree, T extends TreeFaces> MapperTreeDataModel<F, T> getInstance(AbstractTreeAdapter<F> adapter, Class<T> tClass, BaseContext<T> context) {
        Class<F> fClass = (Class<F>) ClassUtil.getTypeArgument(adapter.getClass(), 0);
        MapperTreeDataModel<F, T> dataModel = new MapperTreeDataModel<>(fClass, tClass, context);
        dataModel.setAdapter(adapter);
        return dataModel;
    }


    @Override
    public final TreeNode createRoot(TreeNodeState state) {
        List<F> fList = adapter.queryList();
        List<T> tList = mapperAll(fList);
        TreeNode root = new DefaultTreeNode(null, null);
        loadTreeLeaf(root, tList, Optional.ofNullable(state).orElse(TreeNodeState.builder().build()));
        return root;
    }

    public final void loadTreeLeaf(TreeNode root, List<T> source, TreeNodeState state) {
        resetTreeState(root, state);
        for (T resource : source) {
            TreeNode node = new DefaultTreeNode(resource, root);
            adapter.updateLevel(node.getRowKey(), resource);
            loadTreeLeaf(node, resource.getChildren(), state);
            if (!root.isSelectable()) {
                node.setSelectable(root.isSelectable());
            }
        }
    }

    private void resetTreeState(TreeNode root, TreeNodeState state) {
        if (ObjectUtil.isNotNull(state)) {
            if (ArrayUtil.isNotEmpty(state.getSelectedNodes())) {
                for (TreeNode node : state.getSelectedNodes()) {
                    ITree parent = (ITree) root.getData();
                    ITree target = (ITree) node.getData();
                    if (parent == null || target == null) {
                        continue;
                    }
                    // 已选节点不可选
                    if (StrUtil.startWith(parent.getLevel(), target.getLevel())) {
                        root.setSelectable(state.isEdit() ? false : true);
                    }
                    if (state.isEdit()) {
                        ITree targetParent = (ITree) node.getParent().getData();
                        // 已选节点上级节点标记被选
                        if (targetParent != null && StrUtil.equals(parent.getLevel(), targetParent.getLevel())) {
                            root.setSelected(true);
                        }
                    } else {
                        if (StrUtil.equals(parent.getLevel(), target.getLevel())) {
                            root.setSelected(true);
                        }
                    }
                    // 被选节点以及上级节点展开(长的RowKey放前，短的放后)
                    if (StrUtil.startWith(target.getLevel(), parent.getLevel())) {
                        root.setExpanded(true);
                    }
                }
            }
            if (state.isExpandAll()) {
                root.setExpanded(true);
            }
        }
    }


    public List<T> mapperAll(Collection<F> list) {
        List<T> collect = list.stream().map(this::mapperOne).collect(Collectors.toList());
        return collect.stream().filter(m -> m.getParentId() == null).peek(
                (m) -> m.setChildren(TreeUtil.getChildren(m, collect))
        ).collect(Collectors.toList());
    }

    public T mapperOne(F one) {
        FromToKey fromToKey = getFromToKey();
        IMapper<F, T> mapper = MapperUtil.getMapper(fromToKey);
        return Optional.ofNullable(context).map(a -> mapper.mapContextTo(one, a)).orElse(mapper.mapTo(one));
    }

}
