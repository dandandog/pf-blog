package com.dandandog.common.config.security.dto;

import com.dandandog.framework.mapstruct.IMapper;
import com.dandandog.modules.blog.entity.BlogPersonal;
import org.mapstruct.Mapper;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/12/16 16:47
 */

@Mapper
public interface PersonalCredentialsMapper extends IMapper<BlogPersonal, UserCredentials> {
}
