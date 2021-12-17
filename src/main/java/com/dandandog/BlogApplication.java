package com.dandandog;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;


/**
 * @author Administrator
 */
@SpringBootApplication(scanBasePackages = "com.dandandog.**", exclude = DruidDataSourceAutoConfigure.class)
@MapperScan(basePackages = "com.dandandog.modules.**.dao", annotationClass = Mapper.class)
@EnableAsync
public class BlogApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BlogApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }

}
