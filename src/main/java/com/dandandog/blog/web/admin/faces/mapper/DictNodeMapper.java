package com.dandandog.blog.web.admin.faces.mapper;

import com.dandandog.blog.web.admin.faces.vo.DictNodeVo;
import com.dandandog.framework.mapstruct.IMapper;
import com.dandandog.modules.sys.entity.DictNode;
import org.mapstruct.Mapper;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/12/17 16:52
 */
@Mapper
public interface DictNodeMapper extends IMapper<DictNode, DictNodeVo> {
}
