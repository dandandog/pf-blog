package com.dandandog.blog.modules.admin.setting.web.faces.vo;

import com.dandandog.blog.modules.admin.setting.entity.DictNode;
import com.dandandog.blog.modules.admin.setting.entity.enums.InputType;
import com.dandandog.framework.mapstruct.model.MapperVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/6 11:54
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DictVo extends MapperVo {

    private DictNode node = new DictNode();

    private String label;

    private String code;

    private List<String> value;

    private InputType type;

    private int seq;

    private boolean req;

    private String remark;

}
