package com.dandandog.blog.web.admin.converter;

import com.dandandog.framework.faces.converter.GenericEnumConverter;
import com.dandandog.modules.auth.entity.enums.UserState;
import org.springframework.stereotype.Component;

@Component("userStateConverter")
public class UserStateConverter extends GenericEnumConverter<UserState> {
}
