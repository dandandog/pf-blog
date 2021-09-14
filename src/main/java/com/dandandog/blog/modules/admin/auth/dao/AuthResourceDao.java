package com.dandandog.blog.modules.admin.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dandandog.blog.modules.admin.auth.entity.AuthResource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 系统资源表(SysResource)表数据库访问层
 *
 * @author JohnnyLiu
 * @since 2020-09-06 22:06:06
 */
@Mapper
public interface AuthResourceDao extends BaseMapper<AuthResource> {

    @Select("select res.* from auth_resource res left join auth_role_resource r on r.res_id = res.id where r.role_id = #{roleId}")
    List<AuthResource> findByRole(String roleId);

}