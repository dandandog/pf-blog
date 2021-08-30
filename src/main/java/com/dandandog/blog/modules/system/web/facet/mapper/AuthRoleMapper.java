package com.dandandog.blog.modules.system.web.facet.mapper;

import com.dandandog.blog.modules.system.entity.AuthRole;
import com.dandandog.blog.modules.system.web.facet.vo.AuthRoleVo;
import com.dandandog.framework.mapstruct.IMapper;
import org.mapstruct.Mapper;

/**
 * @author Johnny
 */
@Mapper
public interface AuthRoleMapper extends IMapper<AuthRole, AuthRoleVo> {


}
