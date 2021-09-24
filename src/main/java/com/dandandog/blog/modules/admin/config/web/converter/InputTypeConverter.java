package com.dandandog.blog.modules.admin.config.web.converter;

import com.dandandog.blog.modules.admin.config.entity.enums.InputType;
import com.dandandog.framework.faces.converter.GenericEnumConverter;
import org.springframework.stereotype.Component;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/23 17:39
 */
@Component("inputTypeConverter")
public class InputTypeConverter extends GenericEnumConverter<InputType> {
}
