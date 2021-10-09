package com.dandandog.blog.modules.admin.auth.web.faces.mapper;

import com.dandandog.blog.modules.admin.auth.entity.AuthUser;
import com.dandandog.blog.modules.admin.auth.web.faces.vo.AuthUserVo;
import com.dandandog.framework.mapstruct.IMapper;
import com.dandandog.framework.mapstruct.qualifier.QualifierUrl;
import org.mapstruct.Mapper;

/**
 * @author Johnny
 */
@Mapper(uses = {QualifierUrl.class})
public interface AuthUserMapper extends IMapper<AuthUser, AuthUserVo> {


}
