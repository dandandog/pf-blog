package com.dandandog.blog.modules;

import com.dandandog.blog.modules.system.entity.AuthUser;
import com.dandandog.blog.modules.system.entity.enums.UserType;
import com.dandandog.blog.modules.system.service.AuthUserService;
import com.dandandog.framework.faces.controller.FacesController;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.Optional;

@Controller("/register.faces")
public class RegisterController extends FacesController {

    @Resource
    private AuthUserService userService;

    @Override
    public void onEntry() {
        int count = userService.count();
        UserType type = count == 0 ? UserType.ADMIN : UserType.USER;
        putViewScope("user", new AuthUser(type));
    }


    public void save() {
        AuthUser user = getViewScope("user");
        AuthUser authUser = userService.lambdaQuery().eq(AuthUser::getUsername, user.getUsername()).one();
        if (authUser != null) {
            errorMessages("framework", "existUsername", authUser.getUsername());
        } else {
            userService.save(user);
        }
    }


    public void onCancel() {
        onEntry();
    }

}
