package com.dandandog.common.database;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/8/20 10:39
 */
public enum DatabaseDriver {

    MYSQL("com.mysql.cj.jdbc.Driver", "jdbc:mysql://{0}:{1}/{2}?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai"),

    POSTGRE("com.mysql.cj.jdbc.Driver", "jdbc:mysql://{0}:{1}/{2}?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai"),

    ORACLE("com.mysql.cj.jdbc.Driver", "jdbc:mysql://{0}:{1}/{2}?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai");

    private final String driverClassName;

    private final String urlFormat;

    DatabaseDriver(String driverClassName, String urlFormat) {
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
