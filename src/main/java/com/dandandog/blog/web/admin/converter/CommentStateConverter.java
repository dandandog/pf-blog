package com.dandandog.blog.web.admin.converter;

import com.dandandog.framework.faces.converter.GenericEnumConverter;
import com.dandandog.modules.blog.entity.enums.CommentState;

import javax.faces.convert.FacesConverter;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/17 11:44
 */
@FacesConverter("commentStateConverter")
public class CommentStateConverter extends GenericEnumConverter<CommentState> {
}
