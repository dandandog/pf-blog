package com.dandandog.blog.modules.system.auth.service.impl;

import com.dandandog.blog.modules.system.auth.dao.AuthUserRoleDao;
import com.dandandog.blog.modules.system.auth.entity.AuthUserRole;
import com.dandandog.blog.modules.system.auth.service.AuthUserRoleService;
import com.dandandog.framework.core.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 系统用户角色关系表(AuthUserRole)表服务实现类
 *
 * @author JohnnyLiu
 * @since 2020-09-06 22:06:06
 */
@Service
public class AuthUserRoleServiceImpl extends BaseServiceImpl<AuthUserRoleDao, AuthUserRole> implements AuthUserRoleService {

}