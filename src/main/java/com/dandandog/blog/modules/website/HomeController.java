package com.dandandog.blog.modules.website;

import com.dandandog.blog.modules.admin.auth.web.faces.AuthUserFaces;
import com.dandandog.blog.modules.admin.auth.web.faces.vo.AuthUserVo;
import com.dandandog.framework.faces.controller.FacesController;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.faces.view.facelets.FaceletException;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/10/13 16:26
 */
@Controller("/website/home.faces")
public class HomeController extends FacesController {

    @Resource
    private AuthUserFaces userFaces;

    @Override
    public void onEntry() {
        String username = getOptParams("username").orElseThrow(FaceletException::new);
        AuthUserVo userVo = userFaces.getOptByUserName(username).orElseThrow(FaceletException::new);
        putViewScope("user", userVo);
    }
}
