package com.dandandog.blog.modules.admin.website.web.faces;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.dandandog.blog.modules.admin.website.entity.DictNode;
import com.dandandog.blog.modules.admin.website.entity.DictValue;
import com.dandandog.blog.modules.admin.website.service.DictNodeService;
import com.dandandog.blog.modules.admin.website.service.DictValueService;
import com.dandandog.blog.modules.admin.website.web.faces.vo.DictVo;
import com.dandandog.framework.core.annotation.Facet;
import com.dandandog.framework.core.entity.BaseEntity;
import com.dandandog.framework.mapstruct.MapperUtil;
import com.google.common.collect.Multimap;
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
@Facet
public class DictFaces {

    @Resource
    private DictNodeService dictNodeService;

    @Resource
    private DictValueService dictValueService;

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
