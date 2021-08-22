package com.dandandog.blog.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dandandog.blog.modules.system.entity.AuthResource;
import com.dandandog.blog.modules.system.entity.AuthRole;
import com.myelephant.runningmom.modules.auth.entity.AuthResource;
import com.myelephant.runningmom.modules.auth.entity.AuthRole;

import java.util.List;

/**
 * 系统角色表(AuthRole)表服务接口
 *
 * @author JohnnyLiu
 * @since 2020-09-06 22:06:06
 */
public interface AuthRoleService extends IService<AuthRole> {


    void saveOrUpdate(AuthRole role, List<AuthResource> resources);

    List<AuthRole> findByUser(String userId);
}