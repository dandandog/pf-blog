package com.dandandog.blog.modules.system.web.facet.mapper;

import com.dandandog.blog.modules.system.entity.AuthRole;
import com.dandandog.blog.modules.system.entity.AuthUser;
import com.dandandog.blog.modules.system.web.facet.vo.AuthRoleVo;
import com.dandandog.blog.modules.system.web.facet.vo.AuthUserVo;
import com.dandandog.framework.mapstruct.IMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author Johnny
 */
@Mapper
public interface AuthRoleMapper extends IMapper<AuthRole, AuthRoleVo> {

    @Override
    @Mapping(target = "operatedTime", ignore = true)
    AuthRole mapFrom(AuthRoleVo authRoleVo);
}
