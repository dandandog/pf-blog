package com.dandandog.blog.modules.system.auth.web.faces.mapper;

import com.dandandog.blog.modules.system.auth.entity.AuthUser;
import com.dandandog.blog.modules.system.auth.web.faces.vo.AuthUserVo;
import com.dandandog.framework.mapstruct.IMapper;
import com.dandandog.framework.mapstruct.qualifier.QualifierUrl;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author Johnny
 */
@Mapper(uses = {QualifierUrl.class})
public interface AuthUserMapper extends IMapper<AuthUser, AuthUserVo> {

    @Override
    @Mapping(target = "operatedTime", ignore = true)
    AuthUser mapFrom(AuthUserVo authUserVo);
}
