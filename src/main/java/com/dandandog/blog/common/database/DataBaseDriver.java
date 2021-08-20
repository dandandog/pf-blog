package com.dandandog.blog.common.database;

import lombok.Data;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/8/20 10:39
 */
public enum DataBaseDriver {

    MYSQL("com.mysql.cj.jdbc.Driver", "jdbc:mysql://{0}:{1}/{2}"),

    POSTGRE_SQL("com.mysql.cj.jdbc.Driver", "jdbc:mysql://{0}:{1}/{2}"),

    ORACLE("com.mysql.cj.jdbc.Driver", "jdbc:mysql://{0}:{1}/{2}");

    private final String driverClassName;

    private final String urlFormat;

    DataBaseDriver(String driverClassName, String urlFormat) {
        this.driverClassName = driverClassName;
        this.urlFormat = urlFormat;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public String getUrlFormat() {
        return urlFormat;
    }
}
