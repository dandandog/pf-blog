package com.dandandog.blog.web.admin.faces.mapper;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.dandandog.blog.web.admin.faces.vo.DictValueVo;
import com.dandandog.blog.web.admin.faces.vo.InputItemVo;
import com.dandandog.framework.mapstruct.IMapper;
import com.dandandog.modules.sys.entity.DictNode;
import com.dandandog.modules.sys.entity.DictValue;
import com.dandandog.modules.sys.service.DictNodeService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/6 13:12
 */
@Mapper
public abstract class DictMapper implements IMapper<DictValue, DictValueVo> {

    @Resource
    private DictNodeService nodeService;

    @Override
    @Mapping(target = "node", source = "nodeId", qualifiedByName = "findByNodeId")
    @Mapping(target = "value", source = "value", qualifiedByName = "buildItem")
    public abstract DictValueVo mapTo(DictValue setDictValue);


    @Named("findByNodeId")
    public DictNode findByNodeId(String nodeId) {
        return nodeService.getById(nodeId);
    }

    @Named("findByNodeId")
    public String findByNodeId(DictNode node) {
        return node.getId();
    }

    @Named("buildItem")
    public String buildItem(List<InputItemVo> value) {
        if (CollUtil.isEmpty(value)) {
            return null;
        }
        return value.stream().map(InputItemVo::getLabel).collect(Collectors.joining(StrUtil.COMMA));
    }

    @Named("buildItem")
    public List<InputItemVo> buildItem(String value) {
        List<String> split = StrUtil.split(value, StrUtil.C_COMMA);
        List<InputItemVo> result = new ArrayList<>();
        for (int i = 0; i < split.size(); i++) {
            result.add(new InputItemVo(i, split.get(i)));
        }
        return result;
    }

}
