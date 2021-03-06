package com.dandandog.modules.config.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dandandog.modules.config.entity.ConfigDictValue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 字典值(SetDictValue)表数据库访问层
 *
 * @author makejava
 * @since 2020-11-21 16:40:42
 */
@Mapper
public interface ConfigDictValueDao extends BaseMapper<ConfigDictValue> {

    @Select("SELECT dv.* FROM dict_value dv LEFT JOIN dict_node dn ON dv.node_id = dn.id WHERE dn.code = #{code} ORDER BY dv.seq ASC")
    List<ConfigDictValue> getByNodeCode(@Param("code") String code);
}