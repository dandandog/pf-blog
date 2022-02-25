package com.dandandog.blog.web.admin.converter;

import com.dandandog.framework.faces.converter.GenericEnumConverter;
import com.dandandog.modules.blog.entity.enums.GenderType;

import javax.faces.convert.FacesConverter;

@FacesConverter("genderTypeConverter")
public class GenderTypeConverter extends GenericEnumConverter<GenderType> {
}
