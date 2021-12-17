package com.dandandog.blog.web.admin.converter;

import com.dandandog.modules.sys.entity.enums.InputType;
import com.dandandog.framework.faces.converter.GenericEnumConverter;
import org.springframework.stereotype.Component;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/23 17:39
 */
@Component("inputTypeConverter")
public class InputTypeConverter extends GenericEnumConverter<InputType> {
}
