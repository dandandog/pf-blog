package com.dandandog.blog.modules.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dandandog.blog.modules.system.entity.AuthResource;
import com.dandandog.blog.modules.system.entity.AuthRole;
import com.dandandog.blog.modules.system.entity.AuthRoleResource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 系统角色资源关系表(SysRoleResource)表数据库访问层
 *
 * @author JohnnyLiu
 * @since 2020-09-06 22:06:06
 */
@Mapper
public interface AuthRoleResourceDao extends BaseMapper<AuthRoleResource> {


}