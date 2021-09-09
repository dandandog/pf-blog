package com.dandandog.blog.modules.system.setting.web.faces.vo;

import com.dandandog.blog.modules.system.setting.entity.DictNode;
import com.dandandog.framework.mapstruct.model.MapperVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/6 11:54
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DictVo extends MapperVo {

    private DictNode node = new DictNode();

    private String label;

    private String value;

    private int seq;

    private boolean def;

    private String filter;

}
