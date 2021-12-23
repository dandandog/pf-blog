package com.dandandog.blog.web.admin.faces.mapper;

import com.dandandog.blog.web.admin.faces.vo.DictValueVo;
import com.dandandog.framework.mapstruct.IMapper;
import com.dandandog.framework.mybatis.entity.BaseEntity;
import com.dandandog.modules.config.entity.DictNode;
import com.dandandog.modules.config.entity.DictValue;
import com.dandandog.modules.config.service.DictNodeService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/6 13:12
 */
@Mapper()
public abstract class DictMapper implements IMapper<DictValue, DictValueVo> {

    @Resource
    private DictNodeService nodeService;

    @Override
    @Mapping(target = "node", source = "nodeId", qualifiedByName = "findNodeById")
    public abstract DictValueVo mapTo(DictValue setDictValue);


    @Named("findNodeById")
    public DictNode findNodeById(String nodeId) {
        return nodeService.getById(nodeId);
    }

    @Named("findNodeById")
    public String findNodeById(DictNode node) {
        return Optional.ofNullable(node).map(BaseEntity::getId).orElse(null);
    }


}
