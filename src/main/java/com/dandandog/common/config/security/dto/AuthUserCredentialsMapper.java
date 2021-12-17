package com.dandandog.common.config.security.dto;

import com.dandandog.framework.mapstruct.IMapper;
import com.dandandog.modules.auth.entity.AuthUser;
import org.mapstruct.Mapper;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/10/9 11:21
 */

@Mapper
public interface AuthUserCredentialsMapper extends IMapper<AuthUser, UserCredentials> {
}
