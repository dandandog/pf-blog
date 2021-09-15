package com.dandandog.blog.modules.admin.content.web.faces.vo;

import com.dandandog.blog.modules.admin.content.entity.enums.MetaType;
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

    private MetaType type = MetaType.TAG;

    private int count;

}
