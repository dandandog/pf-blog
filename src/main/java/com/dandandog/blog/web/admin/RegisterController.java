package com.dandandog.blog.web.admin;

import com.dandandog.blog.web.admin.faces.AuthUserFaces;
import com.dandandog.framework.faces.annotation.MessageRequired;
import com.dandandog.framework.faces.annotation.MessageSeverity;
import com.dandandog.framework.faces.annotation.MessageType;
import com.dandandog.framework.faces.controller.FacesController;
import com.dandandog.modules.auth.entity.AuthUser;
import com.dandandog.modules.auth.entity.enums.UserType;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller("/register.faces")
public class RegisterController extends FacesController {

    @Resource
    private AuthUserFaces authUserFaces;

    @Override
    public void onEntry() {
        int count = authUserFaces.count();
        UserType type = count == 0 ? UserType.ADMIN : UserType.USER;
        putViewScope("user", new AuthUser(type));
    }


    @MessageRequired(type = MessageType.OPERATION, severity = MessageSeverity.ERROR)
    public void save() {
        AuthUser user = getViewScope("user");
        authUserFaces.register(user);
        redirectInternal("/login");
    }

    public void onCancel() {
        onEntry();
    }

}
