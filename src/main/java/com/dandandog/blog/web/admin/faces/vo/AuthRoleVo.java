package com.dandandog.blog.web.admin.faces.vo;

import com.dandandog.framework.common.model.IVo;
import lombok.Data;
import org.primefaces.model.TreeNode;

import java.time.LocalDateTime;

@Data
public class AuthRoleVo implements IVo {

    private String id;

    private String seq;

    private String name;

    private String code;

    private String remark;

    private LocalDateTime operatedTime;

    private TreeNode[] resourceNodes = new TreeNode[0];

}
