package com.dandandog.modules.config.service.impl;

import com.dandandog.modules.config.dao.ConfigDictValueDao;
import com.dandandog.modules.config.entity.ConfigDictValue;
import com.dandandog.modules.config.service.ConfigDictValueService;
import com.dandandog.framework.mybatis.service.BaseServiceImpl;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 字典值(AppDictValue)表服务实现类
 *
 * @author makejava
 * @since 2020-11-21 16:40:42
 */
@Service
public class ConfigDictValueServiceImpl extends BaseServiceImpl<ConfigDictValueDao, ConfigDictValue> implements ConfigDictValueService {

    @Override
    public Multimap<String, ConfigDictValue> findByNodeCodes(String... codes) {
        Multimap<String, ConfigDictValue> result = ArrayListMultimap.create();
        for (String code : codes) {
            List<ConfigDictValue> values = baseMapper.getByNodeCode(code);
            result.putAll(code, values);
        }
        return result;
    }
}