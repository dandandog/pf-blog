package com.dandandog.common.model;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.dandandog.common.adapter.AbstractTreeAdapter;
import com.dandandog.framework.faces.model.tree.TreeDataModel;
import com.dandandog.framework.faces.model.tree.TreeFaces;
import com.dandandog.framework.faces.model.tree.TreeNodeState;
import com.dandandog.framework.faces.utils.TreeUtil;
import com.dandandog.framework.mapstruct.FromToKey;
import com.dandandog.framework.mapstruct.IMapper;
import com.dandandog.framework.mapstruct.context.BaseContext;
import com.dandandog.framework.mapstruct.utils.MapperUtil;
import com.dandandog.framework.mybatis.entity.BaseEntity;
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
public class MapperTreeDataModel<F extends BaseEntity, T extends TreeFaces> implements TreeDataModel {

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

    public static <F extends BaseEntity, T extends TreeFaces> MapperTreeDataModel<F, T> getInstance(AbstractTreeAdapter<F> adapter, Class<T> tClass) {
        return getInstance(adapter, tClass, null);
    }

    public static <F extends BaseEntity, T extends TreeFaces> MapperTreeDataModel<F, T> getInstance(AbstractTreeAdapter<F> adapter, Class<T> tClass, BaseContext<T> context) {
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
        setTreeLeaf(root, tList, Optional.ofNullable(state).orElse(TreeNodeState.builder().build()));
        return root;
    }

    public final void setTreeLeaf(TreeNode root, List<T> source, TreeNodeState state) {
        setTreeState(root, state);
        for (T resource : source) {
            TreeNode node = new DefaultTreeNode(resource, root);
            setTreeLeaf(node, resource.getChildren(), state);
            if (!root.isSelectable()) {
                node.setSelectable(root.isSelectable());
            }
        }
    }

    private void setTreeState(TreeNode root, TreeNodeState state) {
        if (ObjectUtil.isNotNull(state)) {
            if (ArrayUtil.isNotEmpty(state.getSelectedNodes())) {
                for (TreeNode node : state.getSelectedNodes()) {
                    // 已选节点不可选
                    if (StrUtil.equals(root.getRowKey(), node.getRowKey())) {
                        root.setSelectable(state.isEdit() ? false : true);
                    }
                    if (state.isEdit()) {
                        TreeNode parent = node.getParent();
                        // 已选节点上级节点标记被选
                        if (parent != null && StrUtil.equals(root.getRowKey(), parent.getRowKey())) {
                            root.setSelected(true);
                        }
                    } else {
                        if (StrUtil.equals(root.getRowKey(), node.getRowKey())) {
                            root.setSelected(true);
                        }
                    }
                    // 被选节点以及上级节点展开(长的RowKey放前，短的放后)
                    if (StrUtil.startWith(node.getRowKey(), root.getRowKey())) {
                        root.setExpanded(true);
                    }
                }
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
