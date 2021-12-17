package com.dandandog.blog.web.front;

import com.dandandog.blog.web.admin.faces.AuthUserFaces;
import com.dandandog.blog.web.admin.faces.vo.AuthUserVo;
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
