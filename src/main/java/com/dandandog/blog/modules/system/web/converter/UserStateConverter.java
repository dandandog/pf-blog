package com.dandandog.blog.modules.system.web.converter;

import com.dandandog.blog.modules.system.entity.enums.UserState;
import com.dandandog.framework.faces.converter.GenericEnumConverter;
import org.springframework.stereotype.Component;

@Component("userStateConverter")
public class UserStateConverter extends GenericEnumConverter<UserState> {
}
