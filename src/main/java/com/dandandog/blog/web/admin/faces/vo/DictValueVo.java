package com.dandandog.blog.web.admin.faces.vo;

import com.dandandog.framework.common.model.IVo;
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

    private String value;

    private int seq;

    private boolean enable;

    private String remark;

}
