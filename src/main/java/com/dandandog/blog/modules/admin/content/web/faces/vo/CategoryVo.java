package com.dandandog.blog.modules.admin.content.web.faces.vo;

import com.dandandog.blog.common.model.MapperTree;
import com.dandandog.blog.modules.admin.content.entity.enums.MetaType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/10 10:31
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CategoryVo extends MapperTree {

    private String parentId;

    private String name;

    private String slug;

    private MetaType type;

    private String description;

    private int count;

    private int seq;

    public MetaType getType() {
        return MetaType.CATEGORY;
    }
}
