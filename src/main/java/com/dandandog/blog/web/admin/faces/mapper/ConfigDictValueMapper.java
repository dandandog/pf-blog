package com.dandandog.blog.web.admin.faces.mapper;

import com.dandandog.blog.web.admin.faces.vo.ConfigDictNodeVo;
import com.dandandog.blog.web.admin.faces.vo.ConfigDictValueVo;
import com.dandandog.framework.common.model.IEntity;
import com.dandandog.framework.mapstruct.IMapper;
import com.dandandog.framework.mapstruct.utils.MapperUtil;
import com.dandandog.modules.config.entity.ConfigDictNode;
import com.dandandog.modules.config.entity.ConfigDictValue;
import com.dandandog.modules.config.service.ConfigDictNodeService;
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
public abstract class ConfigDictValueMapper implements IMapper<ConfigDictValue, ConfigDictValueVo> {

    @Resource
    private ConfigDictNodeService nodeService;

    @Override
    @Mapping(target = "node", source = "nodeId", qualifiedByName = "findNodeById")
    public abstract ConfigDictValueVo mapTo(ConfigDictValue setDictValue);


    @Named("findNodeById")
    public TreeNode findNodeById(String nodeId) {
        ConfigDictNode entity = nodeService.getById(nodeId);
        return new DefaultTreeNode(MapperUtil.map(entity, ConfigDictNodeVo.class));
    }

    @Named("findNodeById")
    public String findNodeById(TreeNode node) {
        return Optional.ofNullable(node).map(n -> (IEntity) n.getData()).map(IEntity::getId).orElse(null);
    }


}
