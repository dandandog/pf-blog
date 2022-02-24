package com.dandandog.modules.config.service.impl;

import com.dandandog.framework.mybatis.service.BaseServiceImpl;
import com.dandandog.modules.config.dao.ConfigSettingDao;
import com.dandandog.modules.config.entity.ConfigSetting;
import com.dandandog.modules.config.service.ConfigSettingService;
import org.springframework.stereotype.Service;

/**
 * @Author: JohnnyLiu
 * @Date: 2022/2/24 17:44
 */
@Service
public class ConfigSettingServiceImpl extends BaseServiceImpl<ConfigSettingDao, ConfigSetting> implements ConfigSettingService {
}
