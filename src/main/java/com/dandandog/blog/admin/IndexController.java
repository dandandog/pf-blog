package com.dandandog.blog.admin;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.DruidDataSourceCreator;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.dandandog.blog.common.database.DataSourceDTO;
import com.dandandog.framework.faces.controller.FacesController;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/8/19 15:26
 */
@Controller("/index.faces")
public class IndexController extends FacesController {

    @Resource
    private DruidDataSourceCreator druidDataSourceCreator;

    @Resource
    private DataSource dataSource;

    @Override
    @SneakyThrows
    public void onEntry() {
        ClassPathResource classPathResource = new ClassPathResource("database.properties");
        File file = classPathResource.getFile();
        if (file.exists()) {
            InputStream inputStream = classPathResource.getStream();
            Properties properties = new Properties();
            properties.load(inputStream);
            Map<String, String> collect = properties.entrySet().stream().collect(Collectors.toMap(prop -> (String) prop.getKey(), prop -> (String) prop.getValue()));
            DataSourceDTO dto = BeanUtil.mapToBean(collect, DataSourceDTO.class, false, CopyOptions.create());
            addDataSource(dto);
            inputStream.close();
            putViewScope("datasource", dto);
        }
    }

    public void testConnection() throws SQLException {
        DataSourceDTO datasource = getViewScope("datasource");
        DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
        DataSource dataSource = ds.getDataSource(datasource.getPoolName());
        Connection connection = dataSource.getConnection();
        if (connection == null) {
            errorMessages("blog", "connectionFails");
        } else {
            infoMessages("blog", "connectionSuccess");
        }
    }

    public void addDataSource(DataSourceDTO dto) {
        DataSourceProperty dataSourceProperty = new DataSourceProperty();
        BeanUtils.copyProperties(dto, dataSourceProperty);
        DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
        DataSource dataSource = druidDataSourceCreator.createDataSource(dataSourceProperty);
        ds.addDataSource(dto.getPoolName(), dataSource);
    }
}
