package com.dandandog.blog.common.database;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Data;

import java.util.Map;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/8/19 16:34
 */
@Data
public class DataSourceDTO {

    private String pollName = "master";

    private String host = "localhost";

    private String port = "3306";

    private DataBaseDriver dataBaseDriver;

    private String username;

    private String password;

    private String driverClassName;

    private String url;

    public String getDriverClassName() {
        if (dataBaseDriver != null) {
            return dataBaseDriver.getDriverClassName();
        }
        return null;
    }

    public String getUrl() {
        if (dataBaseDriver != null) {
            return StrUtil.indexedFormat(dataBaseDriver.getUrlFormat(), this.host, this.port, "pf_blog");
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
