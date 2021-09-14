package com.dandandog.blog.modules.admin.auth.web.faces.mapper;

import com.dandandog.blog.modules.admin.auth.entity.AuthRole;
import com.dandandog.blog.modules.admin.auth.web.faces.vo.AuthRoleVo;
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
