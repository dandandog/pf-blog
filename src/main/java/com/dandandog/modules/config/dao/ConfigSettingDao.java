package com.dandandog.modules.config.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dandandog.modules.config.entity.ConfigSetting;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: JohnnyLiu
 * @Date: 2022/2/24 17:43
 */
@Mapper
public interface ConfigSettingDao extends BaseMapper<ConfigSetting> {
}
