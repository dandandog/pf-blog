package com.dandandog.blog.modules.admin.content.web.converter;

import com.dandandog.blog.modules.admin.content.entity.enums.CommentStatus;
import com.dandandog.framework.faces.converter.GenericEnumConverter;
import org.springframework.stereotype.Component;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/17 11:44
 */
@Component("commentStatusConverter")
public class CommentStatusConverter extends GenericEnumConverter<CommentStatus> {
}
