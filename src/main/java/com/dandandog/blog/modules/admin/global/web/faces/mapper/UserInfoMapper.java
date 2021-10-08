package com.dandandog.blog.modules.admin.global.web.faces.mapper;

import com.dandandog.blog.modules.admin.auth.entity.AuthUser;
import com.dandandog.blog.modules.admin.global.web.faces.vo.UserInfoVo;
import com.dandandog.framework.mapstruct.IMapper;
import com.dandandog.framework.mapstruct.qualifier.QualifierUrl;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/26 14:24
 */
@Mapper(uses = {QualifierUrl.class})
public interface UserInfoMapper extends IMapper<AuthUser, UserInfoVo> {

    @Override
    @Mapping(target = "password", ignore = true)
    UserInfoVo mapTo(AuthUser authUser);
}
