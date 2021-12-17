package com.dandandog.blog.web.admin.faces.mapper;

import com.dandandog.blog.web.admin.faces.vo.AuthUserVo;
import com.dandandog.framework.mapstruct.IMapper;
import com.dandandog.framework.mapstruct.qualifier.QualifierUrl;
import com.dandandog.modules.auth.entity.AuthUser;
import org.mapstruct.Mapper;

/**
 * @author Johnny
 */
@Mapper(uses = {QualifierUrl.class})
public interface AuthUserMapper extends IMapper<AuthUser, AuthUserVo> {


}
