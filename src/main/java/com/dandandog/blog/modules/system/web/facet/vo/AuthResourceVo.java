package com.dandandog.blog.modules.system.web.facet.vo;

import com.dandandog.blog.common.model.MapperTree;
import com.dandandog.blog.modules.system.entity.enums.ResourceTarget;
import com.dandandog.blog.modules.system.entity.enums.ResourceType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@EqualsAndHashCode(callSuper = true)
public class AuthResourceVo extends MapperTree {

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

    @Min(0)
    @Max(999)
    private Integer level = 0;

}
