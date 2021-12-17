package com.dandandog.blog.web.admin.converter;

import com.dandandog.modules.blog.entity.enums.CommentState;
import com.dandandog.framework.faces.converter.GenericEnumConverter;
import org.springframework.stereotype.Component;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/17 11:44
 */
@Component("commentStateConverter")
public class CommentStateConverter extends GenericEnumConverter<CommentState> {
}
