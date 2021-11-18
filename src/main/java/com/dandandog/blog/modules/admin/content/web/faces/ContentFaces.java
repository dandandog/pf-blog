package com.dandandog.blog.modules.admin.content.web.faces;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import com.dandandog.blog.common.model.MapperPageDataModel;
import com.dandandog.blog.modules.admin.content.entity.BlogContentConfigs;
import com.dandandog.blog.modules.admin.content.entity.BlogContents;
import com.dandandog.blog.modules.admin.content.entity.BlogMetas;
import com.dandandog.blog.modules.admin.content.entity.BlogMetasContents;
import com.dandandog.blog.modules.admin.content.entity.enums.MetaType;
import com.dandandog.blog.modules.admin.content.service.BlogContentConfigsService;
import com.dandandog.blog.modules.admin.content.service.BlogContentsService;
import com.dandandog.blog.modules.admin.content.service.BlogMetasContentsService;
import com.dandandog.blog.modules.admin.content.service.BlogMetasService;
import com.dandandog.blog.modules.admin.content.web.faces.adapter.ContentAdapter;
import com.dandandog.blog.modules.admin.content.web.faces.vo.ArticleVo;
import com.dandandog.blog.modules.admin.content.web.faces.vo.AttachmentVo;
import com.dandandog.framework.faces.annotation.Faces;
import com.dandandog.framework.mapstruct.context.BaseContext;
import com.dandandog.framework.mapstruct.utils.MapperUtil;
import com.dandandog.framework.mybatis.entity.BaseEntity;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.LazyDataModel;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/13 9:58
 */
@Faces
public class ContentFaces {

    @Resource
    private BlogMetasService metasService;

    @Resource
    private BlogContentsService contentsService;

    @Resource
    private BlogMetasContentsService metasContentsService;

    @Resource
    private BlogContentConfigsService contentConfigsService;

    public LazyDataModel<ArticleVo> findDataModel() {
        return MapperPageDataModel.getInstance(new ContentAdapter(), ArticleVo.class);
    }

    public Optional<ArticleVo> getOptById(String id) {
        return Optional.ofNullable(contentsService.getById(id)).map(entity -> MapperUtil.map(entity, ArticleVo.class, entityInfo()));
    }

    @Transactional
    public void saveOrUpdate(ArticleVo vo, Collection<BlogContentConfigs> configs) {
        BlogContents entity = MapperUtil.map(vo, BlogContents.class);
        contentsService.saveOrUpdate(entity);
        // Attachment
        List<BlogContents> attachmentSource = contentsService.lambdaQuery().eq(BlogContents::getParentId, entity.getId()).list();
        Collection<BlogContents> attachmentTarget = MapperUtil.mapFromAll(vo.getAttachments(), BlogContents.class).stream()
                .peek(blogContents -> blogContents.setParentId(entity.getId())).collect(Collectors.toList());
        Collection<String> attachmentRemove = CollUtil.subtractToList(attachmentSource, attachmentTarget)
                .stream().map(BaseEntity::getId).collect(Collectors.toList());
        contentsService.removeByIds(attachmentRemove);
        contentsService.saveOrUpdateBatch(attachmentTarget);

        // tags
        metasContentsService.lambdaUpdate().eq(BlogMetasContents::getContentId, entity.getId()).remove();
        List<BlogMetas> tags = metasService.checkAndSaveTags(ArrayUtil.toArray(vo.getTags(), String.class));
        List<BlogMetasContents> metasContents = tags.stream().map(tag -> new BlogMetasContents(entity.getId(), tag.getId())).collect(Collectors.toList());

        BlogMetas cate = (BlogMetas) vo.getCategoryNode().getData();
        metasContents.add(new BlogMetasContents(entity.getId(), cate.getId()));
        metasContentsService.saveOrUpdateBatch(metasContents);
        // content config
        if (CollUtil.isNotEmpty(configs)) {
            List<BlogContentConfigs> collect = configs.stream().peek(blogContentConfigs -> {
                blogContentConfigs.setContentId(entity.getId());
            }).collect(Collectors.toList());
            contentConfigsService.saveOrUpdateBatch(collect);
        }
    }

    public BaseContext<ArticleVo> entityInfo() {
        return new BaseContext<ArticleVo>() {
            @Override
            protected void after(ArticleVo target, Class<ArticleVo> t) {
                List<BlogContents> list = contentsService.lambdaQuery().eq(BlogContents::getParentId, target.getId()).list();
                target.setAttachments(MapperUtil.mapToAll(list, AttachmentVo.class));

                List<BlogMetas> metas = metasContentsService.findByContentId(target.getId());

                BlogMetas cate = metas.stream().filter(blogMetas -> MetaType.CATEGORY.equals(blogMetas.getType())).findFirst().orElse(null);
                target.setCategoryNode(new DefaultTreeNode(cate));

                List<String> tags = metas.stream().filter(blogMetas -> MetaType.TAG.equals(blogMetas.getType())).map(BlogMetas::getName).collect(Collectors.toList());
                target.setTags(tags);
            }
        };
    }


    public void removeByIds(String[] idList) {
        for (String id : idList) {
            contentsService.lambdaUpdate().eq(BlogContents::getParentId, id).or().eq(BaseEntity::getId, id).remove();
        }
    }


}
