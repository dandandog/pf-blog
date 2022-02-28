package com.dandandog.blog.web.admin.faces.mapper;

import com.dandandog.blog.web.admin.faces.vo.ConfigSettingVo;
import com.dandandog.framework.mapstruct.IMapper;
import com.dandandog.modules.config.entity.ConfigSetting;
import org.mapstruct.Mapper;

/**
 * @Author: JohnnyLiu
 * @Date: 2022/2/28 14:55
 */
@Mapper
public interface ConfigSettingMapper extends IMapper<ConfigSetting, ConfigSettingVo> {
}
