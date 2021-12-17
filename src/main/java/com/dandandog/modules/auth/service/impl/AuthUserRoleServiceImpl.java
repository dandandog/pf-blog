package com.dandandog.modules.auth.service.impl;

import com.dandandog.framework.mybatis.service.BaseServiceImpl;
import com.dandandog.modules.auth.dao.AuthUserRoleDao;
import com.dandandog.modules.auth.entity.AuthUserRole;
import com.dandandog.modules.auth.service.AuthUserRoleService;
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