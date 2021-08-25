package com.dandandog.blog.modules;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.io.resource.ClassPathResource;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.BasicDataSourceCreator;
import com.baomidou.dynamic.datasource.creator.DruidDataSourceCreator;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.dandandog.blog.common.database.DataSourceUtil;
import com.dandandog.blog.common.database.DynamicDataSourceInitializer;
import com.dandandog.blog.common.database.DatabaseDriver;
import com.dandandog.blog.common.database.DataSourceDTO;
import com.dandandog.framework.faces.controller.FacesController;
import org.springframework.stereotype.Controller;
import org.yaml.snakeyaml.Yaml;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/8/19 15:26
 */
@Controller("/index.faces")
public class IndexController extends FacesController {

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
            errorMessages("blog", "connectionFails");
        }
    }

    public void onTest() {
        DataSourceDTO dto = getViewScope("datasource");
        try {
            Connection connection = dataSourceUtil.testConnection(dto);
            if (connection != null) {
                infoMessages("blog", "connectionSuccess");
            }
        } catch (Exception e) {
            errorMessages("blog", "connectionFails");
        }
    }

    public void onCancel() {
        putViewScope("datasource", new DataSourceDTO());
    }
}
