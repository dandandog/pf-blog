package com.dandandog.blog.web.admin.faces;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.dandandog.blog.web.admin.faces.adapter.DictNodeAdapter;
import com.dandandog.blog.web.admin.faces.vo.ConfigDictNodeVo;
import com.dandandog.blog.web.admin.faces.vo.ConfigDictValueVo;
import com.dandandog.common.model.MapperTreeDataModel;
import com.dandandog.framework.faces.annotation.Faces;
import com.dandandog.framework.faces.model.tree.TreeDataModel;
import com.dandandog.framework.faces.model.tree.TreeNodeState;
import com.dandandog.framework.mapstruct.utils.MapperUtil;
import com.dandandog.framework.mybatis.entity.BaseEntity;
import com.dandandog.modules.config.entity.ConfigDictNode;
import com.dandandog.modules.config.entity.ConfigDictValue;
import com.dandandog.modules.config.service.ConfigDictNodeService;
import com.dandandog.modules.config.service.ConfigDictValueService;
import com.google.common.collect.Multimap;
import org.primefaces.model.TreeNode;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/6 13:09
 */
@Faces
public class ConfigDictFaces {

    @Resource
    private ConfigDictNodeService dictNodeService;

    @Resource
    private ConfigDictValueService dictValueService;


    public TreeDataModel findDataModel() {
        return MapperTreeDataModel.getInstance(new DictNodeAdapter(), ConfigDictNodeVo.class);
    }

    public TreeNode initNodeTree(TreeDataModel dataModel, TreeNodeState state) {
        if (dataModel == null) {
            dataModel = findDataModel();
        }
        return dataModel.createRoot(state);
    }


    public Collection<ConfigDictValueVo> list(String nodeId) {
        List<ConfigDictValue> list = dictValueService.lambdaQuery().eq(ConfigDictValue::getNodeId, nodeId).orderByAsc(ConfigDictValue::getSeq).list();
        Collection<ConfigDictValueVo> dictValueVos = MapperUtil.mapToAll(list, ConfigDictValueVo.class);
        updateBySort(dictValueVos);
        return dictValueVos;
    }

    public void updateBySort(Collection<ConfigDictValueVo> list) {
        for (int i = 0; i < list.size(); i++) {
            ConfigDictValueVo vo = CollUtil.get(list, i);
            if (!Objects.equals(i, vo.getSeq())) {
                vo.setSeq(i);
                dictValueService.lambdaUpdate().set(ConfigDictValue::getSeq, i).eq(BaseEntity::getId, vo.getId()).update();
            }
        }
    }


    public Optional<ConfigDictNodeVo> getNodeById(String id) {
        return Optional.ofNullable(dictNodeService.getById(id)).map(entity -> MapperUtil.map(entity, ConfigDictNodeVo.class));
    }

    public Optional<ConfigDictValueVo> getOptById(String id) {
        return Optional.ofNullable(dictValueService.getById(id)).map(entity -> MapperUtil.map(entity, ConfigDictValueVo.class));
    }

    public Multimap<String, ConfigDictValue> getValueByCodes(String... codes) {
        return dictValueService.findByNodeCodes(codes);
    }

    public void saveOrUpdateNode(ConfigDictNodeVo node) {
        ConfigDictNode entity = MapperUtil.map(node, ConfigDictNode.class);
        dictNodeService.saveOrUpdate(entity);
    }

    @Transactional
    public void saveOrUpdateValue(ConfigDictValueVo value) {
        ConfigDictValue entity = MapperUtil.map(value, ConfigDictValue.class);
        dictValueService.saveOrUpdate(entity);
    }

    @Transactional
    public void removeByNode(ConfigDictNodeVo... selectedNode) {
        for (ConfigDictNodeVo vo : selectedNode) {
            dictNodeService.lambdaUpdate().likeRight(ConfigDictNode::getLevel, vo.getLevel()).remove();
            dictValueService.lambdaUpdate().eq(ConfigDictValue::getNodeId, vo.getId()).remove();
        }
    }

    @Transactional
    public void removeByIds(String... ids) {
        dictValueService.removeByIds(Arrays.asList(ids));
    }

    public void updateByFiled(String rowKey, String field, Object newValue) {
        dictValueService.update(new UpdateWrapper<ConfigDictValue>().set(field, newValue).eq("id", rowKey));
    }


}
