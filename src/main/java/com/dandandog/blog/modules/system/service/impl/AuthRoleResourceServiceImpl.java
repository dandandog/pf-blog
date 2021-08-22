package com.dandandog.blog.modules.system.service.impl;

import com.dandandog.blog.modules.system.dao.AuthRoleResourceDao;
import com.dandandog.blog.modules.system.entity.AuthRoleResource;
import com.dandandog.blog.modules.system.service.AuthRoleResourceService;
import com.dandandog.framework.core.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 系统角色资源关系表(AuthRoleResource)表服务实现类
 *
 * @author JohnnyLiu
 * @since 2020-09-06 22:06:06
 */
@Service
public class AuthRoleResourceServiceImpl extends BaseServiceImpl<AuthRoleResourceDao, AuthRoleResource> implements AuthRoleResourceService {

}