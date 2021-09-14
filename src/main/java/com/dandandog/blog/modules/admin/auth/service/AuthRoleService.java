package com.dandandog.blog.modules.admin.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dandandog.blog.modules.admin.auth.entity.AuthRole;

import java.util.List;

/**
 * 系统角色表(AuthRole)表服务接口
 *
 * @author JohnnyLiu
 * @since 2020-09-06 22:06:06
 */
public interface AuthRoleService extends IService<AuthRole> {

    List<AuthRole> findByUser(String userId);
}