package com.dandandog.modules.config.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dandandog.modules.config.entity.DictNode;
import org.apache.ibatis.annotations.Mapper;

/**
 * 字典节点(SetDictNode)表数据库访问层
 *
 * @author Johnny
 * @since 2020-11-21 16:40:45
 */
@Mapper
public interface DictNodeDao extends BaseMapper<DictNode> {

}