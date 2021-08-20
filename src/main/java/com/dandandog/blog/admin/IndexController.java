package com.dandandog.blog.admin;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.io.resource.ClassPathResource;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.BasicDataSourceCreator;
import com.baomidou.dynamic.datasource.creator.DruidDataSourceCreator;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.dandandog.blog.common.database.DataBaseDriver;
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
    private DataSource dataSource;

    @Resource
    private BasicDataSourceCreator basicDataSourceCreator;

    @Resource
    private DruidDataSourceCreator druidDataSourceCreator;

    @Override
    public void onEntry() {
        boolean isConnection = checkConnection();
        if (isConnection) {
            redirectInternal("/login");
        }
        putViewScope("dataBaseDrivers", DataBaseDriver.values());
        putViewScope("datasource", new DataSourceDTO());
    }

    public void saveDatabaseConfig(DataSourceDTO dto) {
        ClassPathResource resource = new ClassPathResource("/config/application-datasource.yml");
        FileWriter writer = new FileWriter(resource.getFile());
        Yaml yaml = new Yaml();
        writer.write(yaml.dumpAsMap(dto.getContent()));
    }

    public boolean checkConnection() {
        DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
        return CollUtil.isNotEmpty(ds.getCurrentDataSources());
    }

    private DataSourceProperty copyProperties(DataSourceDTO dto) {
        DataSourceProperty dataSourceProperty = new DataSourceProperty();
        dataSourceProperty.setDriverClassName(dto.getDriverClassName());
        dataSourceProperty.setUrl(dto.getUrl());
        dataSourceProperty.setUsername(dto.getUsername());
        dataSourceProperty.setPassword(dto.getPassword());
        dataSourceProperty.setLazy(false);
        return dataSourceProperty;
    }

    public void onConfirm() {
        DataSourceDTO dto = getViewScope("datasource");
        boolean isConn = testConnection(dto);
        if (isConn) {
            DataSourceProperty dataSourceProperty = copyProperties(dto);
            dataSourceProperty.setSchema("classpath*:db/schema.sql");
            DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
            DataSource dataSource = druidDataSourceCreator.createDataSource(dataSourceProperty);
            ds.addDataSource(dto.getPollName(), dataSource);
            saveDatabaseConfig(dto);
        } else {
            errorMessages("blog", "connectionFails");
        }
    }

    public void onCancel() {
        putViewScope("datasource", new DataSourceDTO());
    }

    private boolean testConnection(DataSourceDTO dto) {
        DataSourceProperty dataSourceProperty = copyProperties(dto);
        dataSourceProperty.getDruid().setConnectionErrorRetryAttempts(3);
        DataSource dataSource = basicDataSourceCreator.createDataSource(dataSourceProperty);
        try {
            Connection connection = dataSource.getConnection();
            return connection != null;
        } catch (SQLException ignored) {
        }
        return false;
    }


    public void onTest() {
        DataSourceDTO dto = getViewScope("datasource");
        boolean isConn = testConnection(dto);
        if (isConn) {
            infoMessages("blog", "connectionSuccess");
        } else {
            errorMessages("blog", "connectionFails");
        }
    }
}
