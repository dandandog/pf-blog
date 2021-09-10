package com.dandandog.blog.common.model;

import com.dandandog.framework.mapstruct.model.MapperVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.primefaces.model.CheckboxTreeNode;
import org.primefaces.model.TreeNode;


/**
 * @author JohnnyLiu
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class MapperTree extends MapperVo {

    private TreeNode parent = new CheckboxTreeNode();
}
