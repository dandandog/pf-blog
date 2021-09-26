package com.dandandog.blog.modules.admin.global.web.controller;

import com.dandandog.blog.modules.admin.auth.entity.AuthUser;
import com.dandandog.blog.modules.admin.auth.service.AuthUserService;
import com.dandandog.blog.modules.admin.global.web.faces.vo.UserInfoVo;
import com.dandandog.framework.common.utils.SecurityUtil;
import com.dandandog.framework.faces.controller.FacesController;
import com.dandandog.framework.mapstruct.MapperUtil;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/26 11:34
 */

@Controller("/admin/global/personal.faces")
public class PersonalController extends FacesController {


    @Resource
    private AuthUserService userService;

    @Override
    public void onEntry() {
        AuthUser user = getCurrUser().orElse(null);
        UserInfoVo userVo = MapperUtil.map(user, UserInfoVo.class);
        putViewScope("user", userVo);
    }

    public void save() {
        UserInfoVo vo = getViewScope("user");
    }


    private Optional<AuthUser> getCurrUser() {
        String uniqueId = SecurityUtil.getUniqueId();
        return userService.findByUsername(uniqueId);
    }

}
