package com.dandandog.modules.auth.service.impl;

import com.dandandog.framework.mybatis.service.BaseServiceImpl;
import com.dandandog.modules.auth.dao.AuthRoleResourceDao;
import com.dandandog.modules.auth.entity.AuthRoleResource;
import com.dandandog.modules.auth.service.AuthRoleResourceService;
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