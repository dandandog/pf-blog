package com.dandandog.blog.modules.content.web.faces.vo;

import com.dandandog.blog.common.model.MapperTree;
import com.dandandog.blog.modules.content.entity.enums.MetaType;
import com.dandandog.framework.mapstruct.model.MapperVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/10 10:31
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TagVo extends MapperVo {

    private String name;

    private String slug;

    private MetaType type;

    private int count;

    public MetaType getType() {
        return MetaType.TAG;
    }
}
