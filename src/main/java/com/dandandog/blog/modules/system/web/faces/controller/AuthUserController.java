package com.dandandog.blog.modules.system.web.faces.controller;

import com.dandandog.blog.modules.system.entity.AuthUser;
import com.dandandog.blog.modules.system.entity.enums.UserGender;
import com.dandandog.blog.modules.system.entity.enums.UserState;
import com.dandandog.blog.modules.system.entity.enums.UserType;
import com.dandandog.framework.faces.controller.FacesController;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/8/25 15:12
 */
@Controller("/system/auth/user.faces")
public class AuthUserController extends FacesController {

    @Override
    public void onEntry() {
        putViewScope("vo", new AuthUser());
        putViewScope("sinSelected", null);
        putViewScope("mulSelected", new ArrayList<AuthUser>());

        putViewScope("genders", UserGender.values());
        putViewScope("states", UserState.values());
        putViewScope("types", UserType.values());
    }


}
