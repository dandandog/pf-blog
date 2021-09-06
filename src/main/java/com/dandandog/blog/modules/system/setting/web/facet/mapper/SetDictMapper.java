package com.dandandog.blog.modules.system.setting.web.facet.mapper;

import com.dandandog.blog.modules.system.setting.entity.SetDictNode;
import com.dandandog.blog.modules.system.setting.entity.SetDictValue;
import com.dandandog.blog.modules.system.setting.service.SetDictNodeService;
import com.dandandog.blog.modules.system.setting.web.facet.vo.SetDictVo;
import com.dandandog.framework.mapstruct.IMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import javax.annotation.Resource;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/6 13:12
 */
@Mapper
public abstract class SetDictMapper implements IMapper<SetDictValue, SetDictVo> {

    @Resource
    private SetDictNodeService nodeService;

    @Override
    @Mapping(target = "node", source = "nodeId", qualifiedByName = "findByNodeId")
    public abstract SetDictVo mapTo(SetDictValue setDictValue);


    @Named("findByNodeId")
    public SetDictNode findByNodeId(String nodeId) {
        return nodeService.getById(nodeId);
    }

    @Named("findByNodeId")
    public String findByNodeId(SetDictNode node) {
        return node.getId();
    }
}
