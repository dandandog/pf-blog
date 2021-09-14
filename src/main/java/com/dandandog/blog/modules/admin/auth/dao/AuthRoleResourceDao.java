package com.dandandog.blog.modules.admin.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dandandog.blog.modules.admin.auth.entity.AuthRoleResource;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统角色资源关系表(SysRoleResource)表数据库访问层
 *
 * @author JohnnyLiu
 * @since 2020-09-06 22:06:06
 */
@Mapper
public interface AuthRoleResourceDao extends BaseMapper<AuthRoleResource> {


}