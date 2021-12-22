package com.dandandog.blog.web.admin.faces;

import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.dandandog.blog.web.admin.faces.adapter.DictNodeAdapter;
import com.dandandog.blog.web.admin.faces.vo.DictNodeVo;
import com.dandandog.blog.web.admin.faces.vo.DictValueVo;
import com.dandandog.common.model.MapperTreeDataModel;
import com.dandandog.framework.faces.annotation.Faces;
import com.dandandog.framework.faces.model.tree.TreeConfig;
import com.dandandog.framework.faces.model.tree.TreeDataModel;
import com.dandandog.framework.faces.model.tree.TreeFaces;
import com.dandandog.framework.mapstruct.utils.MapperUtil;
import com.dandandog.modules.sys.entity.DictNode;
import com.dandandog.modules.sys.entity.DictValue;
import com.dandandog.modules.sys.service.DictNodeService;
import com.dandandog.modules.sys.service.DictValueService;
import com.google.common.collect.Multimap;
import org.primefaces.model.TreeNode;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

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

    public TreeNode initTree(TreeDataModel dataModel, TreeFaces... selected) {
        TreeConfig config = new TreeConfig();
        if (dataModel == null) {
            dataModel = findDataModel();
        }
        if (ArrayUtil.isNotEmpty(selected)) {
            config.setUnSelectable(Arrays.stream(selected).map(TreeFaces::getId).toArray(String[]::new));
            config.setSelected(Arrays.stream(selected).map(TreeFaces::getParentId).toArray(String[]::new));
        }
        return dataModel.createRoot(config);
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
        dictNodeService.save(entity);
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
            dictNodeService.removeById(ids);
        }
    }

    @Transactional
    public void removeByIds(String... ids) {
        dictValueService.removeByIds(Arrays.asList(ids));
    }

    public Collection<DictNode> nodes() {
        return dictNodeService.lambdaQuery().orderByAsc(DictNode::getSeq).list();
    }


    public void updateByFiled(String rowKey, String field, Object newValue) {
        dictValueService.update(new UpdateWrapper<DictValue>().set(field, newValue).eq("id", rowKey));
    }


}