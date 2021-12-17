package com.dandandog.blog.web.admin.faces.vo;

import com.dandandog.framework.common.model.IVo;
import com.dandandog.framework.faces.model.tree.TreeFaces;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/12/17 16:34
 */

@Getter
@Setter
public class DictNodeVo extends TreeFaces implements IVo {

    private int seq = 1;

    private String name;

    private String code;

    private boolean leaf = true;

    private String remark;

}
