package com.dandandog.blog.modules.system.auth.web.facet.vo;

import cn.hutool.core.util.ArrayUtil;
import com.dandandog.blog.modules.system.auth.entity.AuthResource;
import com.dandandog.framework.mapstruct.model.MapperVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.primefaces.model.TreeNode;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(callSuper = true)
public class AuthRoleVo extends MapperVo {

    private String name;

    private String code;

    private Integer level;

    private Integer dataScope;

    private String remark;

    private LocalDateTime operatedTime;

    private List<AuthResource> resources;

    private TreeNode[] resourceNodes = new TreeNode[0];

    public void setResourceNodes(TreeNode[] resourceNodes) {
        if (ArrayUtil.isNotEmpty(resourceNodes)) {
            this.resources = Arrays.stream(resourceNodes)
                    .map(treeNode -> (AuthResource) treeNode.getData()).collect(Collectors.toList());
        }
        this.resourceNodes = resourceNodes;
    }

}
