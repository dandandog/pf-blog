package com.dandandog.blog.web.admin.faces.vo;

import com.dandandog.modules.blog.entity.enums.MetaType;
import com.dandandog.framework.common.model.IVo;
import lombok.Data;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/10 10:31
 */
@Data
public class BlogTagVo implements IVo {

    private String id;

    private String name;

    private String slug;

    private MetaType type = MetaType.TAG;

    private int count;

}
