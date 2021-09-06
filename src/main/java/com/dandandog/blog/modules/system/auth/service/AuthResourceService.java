package com.dandandog.blog.modules.system.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dandandog.blog.modules.system.auth.entity.AuthResource;

import java.util.List;

/**
 * 系统资源表(AuthResource)表服务接口
 *
 * @author JohnnyLiu
 * @since 2020-09-06 22:06:06
 */
public interface AuthResourceService extends IService<AuthResource> {

    List<AuthResource> findByRole(String id);

    List<AuthResource> findByUser(String id);
}