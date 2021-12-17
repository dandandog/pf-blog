package com.dandandog.blog.web.admin.converter;

import com.dandandog.framework.faces.converter.GenericEnumConverter;
import com.dandandog.modules.auth.entity.enums.UserType;
import org.springframework.stereotype.Component;

@Component("userTypeConverter")
public class UserTypeConverter extends GenericEnumConverter<UserType> {
}
