package com.dandandog.blog.modules.system.auth.web.converter;

import com.dandandog.blog.modules.system.auth.entity.enums.UserType;
import com.dandandog.framework.faces.converter.GenericEnumConverter;
import org.springframework.stereotype.Component;

@Component("userTypeConverter")
public class UserTypeConverter extends GenericEnumConverter<UserType> {
}
