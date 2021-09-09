package com.dandandog.blog.modules.system.setting.web.faces;

import com.dandandog.blog.modules.system.setting.entity.DictNode;
import com.dandandog.blog.modules.system.setting.entity.DictValue;
import com.dandandog.blog.modules.system.setting.service.DictNodeService;
import com.dandandog.blog.modules.system.setting.service.DictValueService;
import com.dandandog.blog.modules.system.setting.web.faces.vo.DictVo;
import com.dandandog.framework.core.annotation.Facet;
import com.dandandog.framework.core.entity.BaseEntity;
import com.dandandog.framework.mapstruct.MapperUtil;
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
        List<String> nodeIds = entities.stream().map(DictValue::getNodeId).collect(Collectors.toList());
        List<String> entityIds = entities.stream().map(BaseEntity::getId).collect(Collectors.toList());
        dictValueService.removeByIds(entityIds);
        dictNodeService.removeByIds(nodeIds);
    }

    public Collection<DictNode> nodes() {
        return dictNodeService.lambdaQuery().orderByAsc(DictNode::getSeq).list();
    }
}
