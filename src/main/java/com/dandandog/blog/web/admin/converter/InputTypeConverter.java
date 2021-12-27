package com.dandandog.blog.web.admin.converter;

import com.dandandog.framework.faces.converter.GenericEnumConverter;
import com.dandandog.modules.config.entity.enums.InputType;

import javax.faces.convert.FacesConverter;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/23 17:39
 */
@FacesConverter("inputTypeConverter")
public class InputTypeConverter extends GenericEnumConverter<InputType> {
}
