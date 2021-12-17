package com.dandandog.modules.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dandandog.modules.blog.entity.BlogMeta;
import com.dandandog.modules.blog.entity.BlogMetasContent;

import java.util.List;

/**
 * 元数据关联内容 (BlogMetasContents)表服务接口
 *
 * @author Johnny
 * @since 2021-09-09 14:41:01
 */
public interface BlogMetaContentsService extends IService<BlogMetasContent> {


    List<BlogMeta> findByContentId(String contentId);


}
