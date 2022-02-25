package com.dandandog.blog.web.admin.converter;

import com.dandandog.framework.faces.converter.GenericEnumConverter;
import com.dandandog.modules.blog.entity.enums.ContentState;

import javax.faces.convert.FacesConverter;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/15 11:58
 */
@FacesConverter("contentStateConverter")
public class ContentStateConverter extends GenericEnumConverter<ContentState> {
}
