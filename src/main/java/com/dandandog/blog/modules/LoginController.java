package com.dandandog.blog.modules;

import com.dandandog.blog.common.database.DataSourceDTO;
import com.dandandog.blog.common.database.DataSourceUtil;
import com.dandandog.blog.common.database.DatabaseDriver;
import com.dandandog.framework.faces.controller.FacesController;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/8/24 9:00
 */
@Controller("/login.faces")
public class LoginController extends FacesController {

    @Resource
    private DataSourceUtil dataSourceUtil;

    @Override
    public void onEntry() {
        boolean isConn = dataSourceUtil.checkConnection();
        if (!isConn) {
            redirectInternal("/index");
        }
    }


}
