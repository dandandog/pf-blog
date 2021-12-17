package com.dandandog.modules.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dandandog.modules.blog.entity.BlogMeta;

import java.util.List;

/**
 * 元数据(BlogMetas)表服务接口
 *
 * @author Johnny
 * @since 2021-09-09 14:41:00
 */
public interface BlogMetaService extends IService<BlogMeta> {

    List<BlogMeta> checkAndSaveTags(String... tags);

}
