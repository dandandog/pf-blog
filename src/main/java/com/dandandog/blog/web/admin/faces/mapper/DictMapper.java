package com.dandandog.blog.web.admin.faces.mapper;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.dandandog.blog.web.admin.faces.vo.DictValueVo;
import com.dandandog.blog.web.admin.faces.vo.InputItemVo;
import com.dandandog.framework.mapstruct.IMapper;
import com.dandandog.framework.mybatis.entity.BaseEntity;
import com.dandandog.modules.sys.entity.DictNode;
import com.dandandog.modules.sys.entity.DictValue;
import com.dandandog.modules.sys.service.DictNodeService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
