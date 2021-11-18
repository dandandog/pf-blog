package com.dandandog.blog.modules.admin.content.web.faces.vo;

import com.dandandog.blog.modules.admin.content.entity.enums.MetaType;
import com.dandandog.framework.common.model.IVo;
import com.dandandog.framework.faces.model.tree.TreeFaces;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/10 10:31
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class CategoryVo extends TreeFaces implements IVo {

    private String name;

    private String slug;

    private MetaType type = MetaType.CATEGORY;

    private String description;

    private int count;

    private int seq;
}
