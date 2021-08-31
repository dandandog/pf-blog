package com.dandandog.blog.modules.system.web.facet.vo;

import com.dandandog.blog.modules.system.entity.AuthResource;
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

    public List<AuthResource> getResources() {
        return Arrays.stream(resourceNodes)
                .map(treeNode -> (AuthResource) treeNode.getData()).collect(Collectors.toList());
    }
}