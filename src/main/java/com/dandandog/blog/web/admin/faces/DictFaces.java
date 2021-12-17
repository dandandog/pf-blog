package com.dandandog.blog.web.admin.faces;

import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.dandandog.blog.web.admin.faces.adapter.DictNodeAdapter;
import com.dandandog.blog.web.admin.faces.vo.DictNodeVo;
import com.dandandog.blog.web.admin.faces.vo.DictVo;
import com.dandandog.common.model.MapperTreeDataModel;
import com.dandandog.framework.faces.annotation.Faces;
import com.dandandog.framework.faces.model.tree.TreeConfig;
import com.dandandog.framework.faces.model.tree.TreeDataModel;
import com.dandandog.framework.faces.model.tree.TreeFaces;
import com.dandandog.framework.mapstruct.utils.MapperUtil;
import com.dandandog.framework.mybatis.entity.BaseEntity;
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


    public Collection<DictVo> list() {
        List<DictValue> list = dictValueService.lambdaQuery().orderByAsc(DictValue::getSeq).list();
        return MapperUtil.mapToAll(list, DictVo.class);
    }

    public Optional<DictVo> getOptById(String id) {
        return Optional.ofNullable(dictValueService.getById(id)).map(entity -> MapperUtil.map(entity, DictVo.class));
    }

    public Multimap<String, DictValue> getValueByCodes(String... codes) {
        return dictValueService.findByNodeCodes(codes);
    }


    @Transactional
    public void saveOrUpdate(DictVo selected) {
        if (dictNodeService.saveOrUpdate(selected.getNode())) {
            DictValue entity = MapperUtil.map(selected, DictValue.class);
            dictValueService.saveOrUpdate(entity);
        }
    }

    @Transactional
    public void removeByIds(DictVo[] idList) {
        Collection<DictValue> entities = MapperUtil.mapFromAll(Arrays.stream(idList).collect(Collectors.toList()), DictValue.class);
        List<String> entityIds = entities.stream().map(BaseEntity::getId).collect(Collectors.toList());
        List<String> nodeIds = entities.stream().map(DictValue::getNodeId).collect(Collectors.toList());
        dictValueService.removeByIds(entityIds);
        for (String nodeId : nodeIds) {
            Integer count = dictValueService.lambdaQuery().eq(DictValue::getNodeId, nodeId).count();
            if (count == 0) {
                dictNodeService.removeById(nodeId);
            }
        }
    }

    public Collection<DictNode> nodes() {
        return dictNodeService.lambdaQuery().orderByAsc(DictNode::getSeq).list();
    }


    public void updateByFiled(String rowKey, String field, Object newValue) {
        dictValueService.update(new UpdateWrapper<DictValue>().set(field, newValue).eq("id", rowKey));
    }


}
