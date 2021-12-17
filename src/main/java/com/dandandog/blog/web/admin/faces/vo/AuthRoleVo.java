package com.dandandog.blog.web.admin.faces.vo;

import cn.hutool.core.util.ArrayUtil;
import com.dandandog.framework.common.model.IVo;
import com.dandandog.framework.mapstruct.utils.MapperUtil;
import com.dandandog.modules.auth.entity.AuthResource;
import com.dandandog.modules.auth.entity.enums.ResourceType;
import lombok.Data;
import org.primefaces.model.TreeNode;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class AuthRoleVo implements IVo {

    private String id;

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
                    .map(treeNode -> (AuthResourceVo) treeNode.getData())
                    .filter(data -> ResourceType.CATALOG.equals(data.getType()))
                    .map(data -> MapperUtil.map(data, AuthResource.class))
                    .collect(Collectors.toList());
        }
        this.resourceNodes = resourceNodes;
    }

}
