package com.dandandog.modules.config.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dandandog.modules.config.entity.ConfigDictValue;
import com.google.common.collect.Multimap;

/**
 * 字典值(SetDictValue)表服务接口
 *
 * @author makejava
 * @since 2020-11-21 16:40:42
 */
public interface ConfigDictValueService extends IService<ConfigDictValue> {

    Multimap<String, ConfigDictValue> findByNodeCodes(String... codes);


}