package com.dandandog.blog.modules.system.setting.web.facet;

import com.dandandog.blog.modules.system.auth.web.facet.vo.AuthUserVo;
import com.dandandog.blog.modules.system.setting.entity.SetDictNode;
import com.dandandog.blog.modules.system.setting.entity.SetDictValue;
import com.dandandog.blog.modules.system.setting.service.SetDictNodeService;
import com.dandandog.blog.modules.system.setting.service.SetDictValueService;
import com.dandandog.blog.modules.system.setting.web.facet.vo.SetDictVo;
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
public class SetDictFacet {

    @Resource
    private SetDictNodeService dictNodeService;

    @Resource
    private SetDictValueService dictValueService;

    public Collection<SetDictVo> list() {
        List<SetDictValue> list = dictValueService.lambdaQuery().orderByAsc(SetDictValue::getSeq).list();
        return MapperUtil.mapToAll(list, SetDictVo.class);
    }

    public Optional<SetDictVo> getOptById(String id) {
        return Optional.ofNullable(dictValueService.getById(id)).map(entity -> MapperUtil.map(entity, SetDictVo.class));
    }

    @Transactional
    public void saveOrUpdate(SetDictVo selected) {
        if (dictNodeService.saveOrUpdate(selected.getNode())) {
            SetDictValue entity = MapperUtil.map(selected, SetDictValue.class);
            dictValueService.saveOrUpdate(entity);
        }
    }

    @Transactional
    public void removeByIds(SetDictVo[] idList) {
        Collection<SetDictValue> entities = MapperUtil.mapFromAll(Arrays.stream(idList).collect(Collectors.toList()), SetDictValue.class);
        List<String> nodeIds = entities.stream().map(SetDictValue::getNodeId).collect(Collectors.toList());
        List<String> entityIds = entities.stream().map(BaseEntity::getId).collect(Collectors.toList());
        dictValueService.removeByIds(entityIds);
        dictNodeService.removeByIds(nodeIds);
    }

    public Collection<SetDictNode> nodes() {
        return dictNodeService.lambdaQuery().orderByAsc(SetDictNode::getSeq).list();
    }
}
