package com.dandandog.blog.modules.system.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dandandog.blog.modules.system.auth.entity.AuthUserRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统用户角色关系表(SysUserRole)表数据库访问层
 *
 * @author JohnnyLiu
 * @since 2020-09-06 22:06:06
 */
@Mapper
public interface AuthUserRoleDao extends BaseMapper<AuthUserRole> {

}