package com.dandandog.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dandandog.modules.sys.entity.DictValue;
import com.google.common.collect.Multimap;

/**
 * 字典值(SetDictValue)表服务接口
 *
 * @author makejava
 * @since 2020-11-21 16:40:42
 */
public interface DictValueService extends IService<DictValue> {

    Multimap<String, DictValue> findByNodeCodes(String... codes);


}