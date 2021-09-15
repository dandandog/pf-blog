package com.dandandog.blog.modules.admin.content.web.converter;

import com.dandandog.blog.modules.admin.content.entity.enums.ContentStatus;
import com.dandandog.framework.faces.converter.GenericEnumConverter;
import org.springframework.stereotype.Component;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/15 11:58
 */
@Component("contentStatusConverter")
public class ContentStatusConverter extends GenericEnumConverter<ContentStatus> {
}
