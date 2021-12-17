package com.dandandog.blog.web.admin;

import com.dandandog.framework.faces.controller.FacesController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/12/13 17:15
 */
@Controller("/dashboard.faces")
public class DashboardController extends FacesController {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void onEntry() {
    }


}
