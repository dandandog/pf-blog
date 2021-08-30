package com.dandandog.blog.modules.system.web.converter;

import com.dandandog.blog.modules.system.entity.enums.UserGender;
import com.dandandog.framework.faces.converter.GenericEnumConverter;
import org.springframework.stereotype.Component;

@Component("userGenderConverter")
public class UserGenderConverter extends GenericEnumConverter<UserGender> {
}
