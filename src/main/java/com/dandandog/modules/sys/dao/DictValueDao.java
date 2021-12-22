package com.dandandog.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dandandog.modules.sys.entity.DictValue;
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
public interface DictValueDao extends BaseMapper<DictValue> {

    @Select("SELECT dv.* FROM dict_value dv LEFT JOIN dict_node dn ON dv.node_id = dn.id WHERE dn.code = #{code} ORDER BY dv.seq ASC")
    List<DictValue> getByNodeCode(@Param("code") String code);
}