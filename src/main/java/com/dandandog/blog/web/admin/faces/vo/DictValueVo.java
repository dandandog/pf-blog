package com.dandandog.blog.web.admin.faces.vo;

import com.dandandog.framework.common.model.IVo;
import com.dandandog.modules.config.entity.enums.InputType;
import lombok.Data;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/6 11:54
 */
@Data
public class DictValueVo implements IVo {

    private String id;

    private TreeNode node = new DefaultTreeNode();

    private String label;

    private String code;

    private Object value;

    private InputType type;

    private int seq;

    private boolean req;

    private String remark;

}
