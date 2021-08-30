package com.dandandog.blog.modules.system.web.facet.vo;

import com.dandandog.blog.modules.system.entity.AuthRole;
import com.dandandog.blog.modules.system.entity.enums.UserGender;
import com.dandandog.blog.modules.system.entity.enums.UserState;
import com.dandandog.blog.modules.system.entity.enums.UserType;
import com.dandandog.framework.mapstruct.model.MapperVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.primefaces.model.DualListModel;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/8/27 11:30
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AuthUserVo extends MapperVo {

    @NotEmpty
    private String nickname;

    @NotEmpty
    private String username;

    private String email;

    private String phone;

    private UserState state;

    private UserGender gender;

    private UserType type;

    private String avatarUrl;

    private String remark;

    private LocalDateTime pwdRestTime;

    private LocalDateTime operatedTime;

    private DualListModel<AuthRole> roles = new DualListModel<>();

}
