package com.dandandog.common.database;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/8/19 16:34
 */
@Data
public class DataSourceDTO implements Serializable {

    private String pollName = "master";

    private String host = "localhost";

    private String port = "3306";

    private String dbname = "pf_blog";

    private DatabaseDriver databaseDriver;

    private String username;

    private String password;

    private String driverClassName;

    private String url;

    public String getDriverClassName() {
        if (databaseDriver != null) {
            return databaseDriver.getDriverClassName();
        }
        return null;
    }

    public String getUrl() {
        if (databaseDriver != null) {
            return StrUtil.indexedFormat(databaseDriver.getUrlFormat(), this.host, this.port, this.dbname);
        }
        return null;
    }

    public String getTestUrl() {
        if (databaseDriver != null) {
            return StrUtil.indexedFormat(databaseDriver.getUrlFormat(), this.host, this.port, "mysql");
        }
        return null;
    }

    public Map<Object, Object> getContent() {
        return MapUtil.builder()
                .put("spring", MapUtil.builder("datasource",
                        MapUtil.builder("dynamic",
                                MapUtil.builder("datasource",
                                        MapUtil.builder(pollName,
                                                MapUtil.builder()
                                                        .put("driver-class-name", getDriverClassName())
                                                        .put("url", getUrl())
                                                        .put("username", getUsername())
                                                        .put("password", getPassword())
                                                        .build()).build()).build()).build()).build()).build();
    }
}
