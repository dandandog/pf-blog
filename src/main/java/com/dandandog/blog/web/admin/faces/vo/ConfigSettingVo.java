package com.dandandog.blog.web.admin.faces.vo;

import com.dandandog.framework.common.model.IVo;
import com.dandandog.modules.config.entity.enums.InputType;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: JohnnyLiu
 * @Date: 2022/2/24 18:06
 */

@Getter
@Setter
public class ConfigSettingVo implements IVo {

    private String id;

    private int seq;

    private String code;

    private String name;

    private InputType type;

    private Boolean global;

    private String options;

}
