package com.dandandog.blog.modules.admin.auth.web.converter;

import com.dandandog.blog.modules.admin.auth.entity.enums.UserStatus;
import com.dandandog.framework.faces.converter.GenericEnumConverter;
import org.springframework.stereotype.Component;

@Component("userStatusConverter")
public class UserStatusConverter extends GenericEnumConverter<UserStatus> {
}
