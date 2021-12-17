package com.dandandog.blog.web.admin.converter;

import com.dandandog.modules.blog.entity.enums.ContentState;
import com.dandandog.framework.faces.converter.GenericEnumConverter;
import org.springframework.stereotype.Component;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/15 11:58
 */
@Component("contentStateConverter")
public class ContentStateConverter extends GenericEnumConverter<ContentState> {
}
