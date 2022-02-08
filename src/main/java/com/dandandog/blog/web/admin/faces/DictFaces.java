package com.dandandog.blog.web.admin.faces;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.dandandog.blog.web.admin.faces.adapter.DictNodeAdapter;
import com.dandandog.blog.web.admin.faces.vo.DictNodeVo;
import com.dandandog.blog.web.admin.faces.vo.DictValueVo;
import com.dandandog.common.model.MapperTreeDataModel;
import com.dandandog.framework.common.model.IEntity;
import com.dandandog.framework.faces.annotation.Faces;
import com.dandandog.framework.faces.model.tree.TreeDataModel;
import com.dandandog.framework.faces.model.tree.TreeNodeState;
import com.dandandog.framework.mapstruct.utils.MapperUtil;
import com.dandandog.modules.config.entity.DictNode;
import com.dandandog.modules.config.entity.DictValue;
import com.dandandog.modules.config.service.DictNodeService;
import com.dandandog.modules.config.service.DictValueService;
import com.google.common.collect.Multimap;
import org.primefaces.model.TreeNode;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/6 13:09
 */
@Faces
public class DictFaces {

    @Resource
    private DictNodeService dictNodeService;

    @Resource
    private DictValueService dictValueService;


    public TreeDataModel findDataModel() {
        return MapperTreeDataModel.getInstance(new DictNodeAdapter(), DictNodeVo.class);
    }

    public TreeNode initNodeTree(TreeDataModel dataModel, TreeNodeState state) {
        if (dataModel == null) {
            dataModel = findDataModel();
        }
        return dataModel.createRoot(state);
    }


    public Collection<DictValueVo> list(String nodeId) {
        List<DictValue> list = dictValueService.lambdaQuery().eq(DictValue::getNodeId, nodeId).orderByAsc(DictValue::getSeq).list();
        return MapperUtil.mapToAll(list, DictValueVo.class);
    }


    public Optional<DictNodeVo> getNodeById(String id) {
        return Optional.ofNullable(dictNodeService.getById(id)).map(entity -> MapperUtil.map(entity, DictNodeVo.class));
    }

    public Optional<DictValueVo> getOptById(String id) {
        return Optional.ofNullable(dictValueService.getById(id)).map(entity -> MapperUtil.map(entity, DictValueVo.class));
    }

    public Multimap<String, DictValue> getValueByCodes(String... codes) {
        return dictValueService.findByNodeCodes(codes);
    }

    public void saveOrUpdateNode(DictNodeVo node) {
        DictNode entity = MapperUtil.map(node, DictNode.class);
        dictNodeService.saveOrUpdate(entity);
    }

    @Transactional
    public void saveOrUpdateValue(DictValueVo value) {
        DictValue entity = MapperUtil.map(value, DictValue.class);
        dictValueService.saveOrUpdate(entity);
    }

    @Transactional
    public void removeByNodeIds(String... ids) {
        for (String nodeId : ids) {
            dictValueService.lambdaUpdate().eq(DictValue::getNodeId, nodeId).remove();
            dictNodeService.removeById(nodeId);
        }
    }

    @Transactional
    public void removeByIds(String... ids) {
        dictValueService.removeByIds(Arrays.asList(ids));
    }

    public void updateByFiled(String rowKey, String field, Object newValue) {
        dictValueService.update(new UpdateWrapper<DictValue>().set(field, newValue).eq("id", rowKey));
    }

    public void removeByNode(TreeNode... selectedNode) {
        Collection<TreeNode> removeNode = findRemoveNodeId(new ArrayList<>(Arrays.asList(selectedNode)));
        String[] removeIds = removeNode.stream().map(node -> (IEntity) node.getData()).map(IEntity::getId).toArray(String[]::new);
        removeByNodeIds(removeIds);
    }

    private Collection<TreeNode> findRemoveNodeId(List<TreeNode> nodes) {
        return CollUtil.addAll(nodes, nodes.stream()
                .flatMap(node -> findRemoveNodeId(node.getChildren()).stream()).collect(Collectors.toList()));
    }

}
