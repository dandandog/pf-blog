package com.dandandog.blog.web.admin.faces.mapper;

import com.dandandog.blog.web.admin.faces.vo.DictNodeVo;
import com.dandandog.blog.web.admin.faces.vo.DictValueVo;
import com.dandandog.framework.common.model.IEntity;
import com.dandandog.framework.mapstruct.IMapper;
import com.dandandog.framework.mapstruct.utils.MapperUtil;
import com.dandandog.modules.config.entity.DictNode;
import com.dandandog.modules.config.entity.DictValue;
import com.dandandog.modules.config.service.DictNodeService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/6 13:12
 */
@Mapper
public abstract class ConfigDictValueMapper implements IMapper<DictValue, DictValueVo> {

    @Resource
    private DictNodeService nodeService;

    @Override
    @Mapping(target = "node", source = "nodeId", qualifiedByName = "findNodeById")
    public abstract DictValueVo mapTo(DictValue setDictValue);


    @Named("findNodeById")
    public TreeNode findNodeById(String nodeId) {
        DictNode entity = nodeService.getById(nodeId);
        return new DefaultTreeNode(MapperUtil.map(entity, DictNodeVo.class));
    }

    @Named("findNodeById")
    public String findNodeById(TreeNode node) {
        return Optional.ofNullable(node).map(n -> (IEntity) n).map(IEntity::getId).orElse(null);
    }


}
