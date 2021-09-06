package com.dandandog.blog.modules.system.setting.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dandandog.blog.modules.system.setting.entity.SetDictNode;
import org.apache.ibatis.annotations.Mapper;

/**
 * 字典节点(SetDictNode)表数据库访问层
 *
 * @author Johnny
 * @since 2020-11-21 16:40:45
 */
@Mapper
public interface SetDictNodeDao extends BaseMapper<SetDictNode> {

}