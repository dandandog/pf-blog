package com.dandandog.blog.web.admin.faces.vo;

import com.dandandog.framework.common.model.IVo;
import com.dandandog.framework.faces.model.tree.TreeFaces;
import com.dandandog.modules.auth.entity.enums.ResourceTarget;
import com.dandandog.modules.auth.entity.enums.ResourceType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@EqualsAndHashCode(callSuper = true)
public class AuthResourceVo extends TreeFaces implements IVo {

    @NotBlank
    private String title;

    private String path;

    private String perms;

    @NotNull
    private ResourceType type = ResourceType.MENU;

    private String icon;

    private boolean display = false;

    private ResourceTarget target = ResourceTarget.CURR_PAGE;

    private String level;

    private boolean leaf = true;


    public AuthResourceVo(ResourceType type) {
        this.type = type;
    }

    public AuthResourceVo() {

    }

}
