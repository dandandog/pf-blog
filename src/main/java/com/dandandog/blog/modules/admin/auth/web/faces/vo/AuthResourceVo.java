package com.dandandog.blog.modules.admin.auth.web.faces.vo;

import com.dandandog.blog.modules.admin.auth.entity.enums.ResourceTarget;
import com.dandandog.blog.modules.admin.auth.entity.enums.ResourceType;
import com.dandandog.framework.faces.model.tree.TreeEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@EqualsAndHashCode(callSuper = true)
public class AuthResourceVo extends TreeEntity {

    @NotBlank
    private String title;

    private String path;

    private String perms;

    @NotNull
    private ResourceType type = ResourceType.CATALOG;

    private String icon = "user";

    @Min(0)
    @Max(999)
    private Integer seq = 0;

    private boolean display = false;

    private ResourceTarget target = ResourceTarget.CURR_PAGE;

    private String level = "0";

    private boolean leaf = true;

}
