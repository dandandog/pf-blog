package com.dandandog.blog.modules.admin.content.web.faces.vo;

import com.dandandog.blog.modules.admin.content.entity.enums.MetaType;
import com.dandandog.framework.common.model.IVo;
import lombok.Data;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/10 10:31
 */
@Data
public class TagVo implements IVo {

    private String id;

    private String name;

    private String slug;

    private MetaType type = MetaType.TAG;

    private int count;

}
