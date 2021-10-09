package com.dandandog.blog.security.mapper;

import com.dandandog.blog.modules.admin.auth.entity.AuthUser;
import com.dandandog.framework.mapstruct.IMapper;
import com.dandandog.framework.mapstruct.qualifier.QualifierUrl;
import org.mapstruct.Mapper;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/10/9 11:21
 */

@Mapper(uses = {QualifierUrl.class})
public interface UserCredentialsMapper extends IMapper<AuthUser, UserCredentials> {
}
