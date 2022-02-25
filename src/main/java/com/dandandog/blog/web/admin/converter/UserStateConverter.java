package com.dandandog.blog.web.admin.converter;

import com.dandandog.framework.faces.converter.GenericEnumConverter;
import com.dandandog.modules.auth.entity.enums.UserState;

import javax.faces.convert.FacesConverter;

@FacesConverter("userStateConverter")
public class UserStateConverter extends GenericEnumConverter<UserState> {
}
