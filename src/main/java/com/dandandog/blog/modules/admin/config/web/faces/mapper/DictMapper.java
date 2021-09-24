package com.dandandog.blog.modules.admin.config.web.faces.mapper;

import com.dandandog.blog.modules.admin.config.entity.DictNode;
import com.dandandog.blog.modules.admin.config.entity.DictValue;
import com.dandandog.blog.modules.admin.config.service.DictNodeService;
import com.dandandog.blog.modules.admin.config.web.faces.vo.DictVo;
import com.dandandog.framework.mapstruct.IMapper;
import com.dandandog.framework.mapstruct.qualifier.QualifierArr;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import javax.annotation.Resource;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/6 13:12
 */
@Mapper(uses = {QualifierArr.class})
public abstract class DictMapper implements IMapper<DictValue, DictVo> {

    @Resource
    private DictNodeService nodeService;

    @Override
    @Mapping(target = "node", source = "nodeId", qualifiedByName = "findByNodeId")
    public abstract DictVo mapTo(DictValue setDictValue);


    @Named("findByNodeId")
    public DictNode findByNodeId(String nodeId) {
        return nodeService.getById(nodeId);
    }

    @Named("findByNodeId")
    public String findByNodeId(DictNode node) {
        return node.getId();
    }
}
