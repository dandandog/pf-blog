package com.dandandog.blog.common.database;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/8/19 16:34
 */
@Data
public class DataSourceDTO {

    private String poolName = "master";

    private String driverClassName;

    private String url;

    private String username;

    private String password;

}
