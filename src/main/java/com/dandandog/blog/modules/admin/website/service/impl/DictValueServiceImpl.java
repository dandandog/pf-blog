package com.dandandog.blog.modules.admin.website.service.impl;

import com.dandandog.blog.modules.admin.website.dao.DictValueDao;
import com.dandandog.blog.modules.admin.website.entity.DictValue;
import com.dandandog.blog.modules.admin.website.service.DictValueService;
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
public class DictValueServiceImpl extends BaseServiceImpl<DictValueDao, DictValue> implements DictValueService {

    @Override
    public Multimap<String, DictValue> findByNodeCodes(String... codes) {
        Multimap<String, DictValue> result = ArrayListMultimap.create();
        for (String code : codes) {
            List<DictValue> values = baseMapper.getByNodeCode(code);
            result.putAll(code, values);
        }
        return result;
    }
}