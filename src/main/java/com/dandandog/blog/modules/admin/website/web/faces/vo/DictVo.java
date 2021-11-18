package com.dandandog.blog.modules.admin.website.web.faces.vo;

import com.dandandog.blog.modules.admin.website.entity.DictNode;
import com.dandandog.blog.modules.admin.website.entity.enums.InputType;
import com.dandandog.framework.common.model.IVo;
import lombok.Data;

import java.util.List;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/6 11:54
 */
@Data
public class DictVo implements IVo {

    private String id;

    private DictNode node = new DictNode();

    private String label;

    private String code;

    private List<InputItemVo> value;

    private InputType type;

    private int seq;

    private boolean req;

    private String remark;

}
