package com.dandandog.blog.common.database;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.io.resource.ClassPathResource;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.BasicDataSourceCreator;
import com.baomidou.dynamic.datasource.creator.DruidDataSourceCreator;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

@Component
public class DataSourceUtil {

    @Resource
    private DataSource dataSource;

    @Resource
    private BasicDataSourceCreator basicDataSourceCreator;

    @Resource
    private DruidDataSourceCreator druidDataSourceCreator;

    @Resource
    private DynamicDataSourceInitializer dataSourceInitializer;


    public boolean checkConnection() {
        DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
        return CollUtil.isNotEmpty(ds.getCurrentDataSources());
    }

    public Connection testConnection(DataSourceDTO dto) throws RuntimeException {
        DataSourceProperty dataSourceProperty = copyProperties(dto);
        dataSourceProperty.getDruid().setConnectionErrorRetryAttempts(3);
        DataSource dataSource = basicDataSourceCreator.createDataSource(dataSourceProperty);
        Connection connection;
        try {
            connection = Optional.ofNullable(dataSource.getConnection()).orElseThrow(RuntimeException::new);
        } catch (SQLException ignored) {
            throw new RuntimeException();
        }
        return connection;
    }

    public void connectionDataSource(DataSourceDTO dto) {
        DataSourceProperty dataSourceProperty = copyProperties(dto);
        DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
        DataSource dataSource = druidDataSourceCreator.createDataSource(dataSourceProperty);
        ds.addDataSource(dto.getPollName(), dataSource);
        //写入数据库配置
        writerDatabaseConfig(dto);
        // 执行sql
        dataSourceInitializer.dataSourceInitializer(dataSource).afterPropertiesSet();
    }


    private void writerDatabaseConfig(DataSourceDTO dto) {
        ClassPathResource resource = new ClassPathResource("/config/application-datasource.yml");
        FileWriter writer = new FileWriter(resource.getFile());
        Yaml yaml = new Yaml();
        writer.write(yaml.dumpAsMap(dto.getContent()));
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

}
