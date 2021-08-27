package com.dandandog.blog.modules.system.web.facet.mapper;

import com.dandandog.blog.modules.system.entity.AuthUser;
import com.dandandog.blog.modules.system.web.facet.vo.AuthUserVo;
import com.dandandog.framework.mapstruct.IMapper;
import com.dandandog.framework.mapstruct.qualifier.QualifierUrl;
import org.mapstruct.Mapper;

/**
 * @author Johnny
 */
@Mapper(uses = {QualifierUrl.class})
public abstract class AuthUserMapper implements IMapper<AuthUser, AuthUserVo> {


}
