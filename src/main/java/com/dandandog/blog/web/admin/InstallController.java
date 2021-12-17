package com.dandandog.blog.web.admin;

import com.dandandog.common.database.DataSourceDTO;
import com.dandandog.common.database.DataSourceUtil;
import com.dandandog.common.database.DatabaseDriver;
import com.dandandog.framework.faces.controller.FacesController;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.sql.Connection;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/8/19 15:26
 */
@Controller("/install.faces")
public class InstallController extends FacesController {

    @Resource
    private DataSourceUtil dataSourceUtil;

    @Override
    public void onEntry() {
        boolean isConn = dataSourceUtil.checkConnection();
        if (isConn) {
            redirectInternal("/login");
        }
        putViewScope("databaseDrivers", DatabaseDriver.values());
        putViewScope("datasource", new DataSourceDTO());
    }

    public void onConfirm() {
        DataSourceDTO dto = getViewScope("datasource");
        try {
            Connection connection = dataSourceUtil.testConnection(dto);
            if (connection != null) {
                dataSourceUtil.connectionDataSource(dto);
                redirectInternal("/register");
            }
        } catch (Exception e) {
            errorMessages("blog.connectionFails", null);
        }
    }

    public void onTest() {
        DataSourceDTO dto = getViewScope("datasource");
        try {
            Connection connection = dataSourceUtil.testConnection(dto);
            if (connection != null) {
                infoMessages("blog.connectionSuccess", null);
            }
        } catch (Exception e) {
            errorMessages("blog.connectionFails", null);
        }
    }

    public void onCancel() {
        putViewScope("datasource", new DataSourceDTO());
    }
}
