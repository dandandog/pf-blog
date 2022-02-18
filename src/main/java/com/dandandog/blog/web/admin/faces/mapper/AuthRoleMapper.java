package com.dandandog.blog.web.admin.faces.mapper;

import com.dandandog.blog.web.admin.faces.vo.AuthRoleVo;
import com.dandandog.framework.mapstruct.IMapper;
import com.dandandog.modules.auth.entity.AuthRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author Johnny
 */
@Mapper
public interface AuthRoleMapper extends IMapper<AuthRole, AuthRoleVo> {

    @Override
    AuthRole mapFrom(AuthRoleVo authRoleVo);
}
