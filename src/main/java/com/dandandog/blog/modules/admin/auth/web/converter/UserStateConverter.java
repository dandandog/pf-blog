package com.dandandog.blog.modules.admin.auth.web.converter;

import com.dandandog.blog.modules.admin.auth.entity.enums.UserState;
import com.dandandog.framework.faces.converter.GenericEnumConverter;
import org.springframework.stereotype.Component;

@Component("userStateConverter")
public class UserStateConverter extends GenericEnumConverter<UserState> {
}
