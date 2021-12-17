package com.dandandog.blog.web.admin.faces;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import com.dandandog.blog.web.admin.faces.adapter.BlogContentAdapter;
import com.dandandog.blog.web.admin.faces.vo.BlogArticleVo;
import com.dandandog.blog.web.admin.faces.vo.BlogAttachVo;
import com.dandandog.common.model.MapperPageDataModel;
import com.dandandog.framework.faces.annotation.Faces;
import com.dandandog.framework.mapstruct.context.BaseContext;
import com.dandandog.framework.mapstruct.utils.MapperUtil;
import com.dandandog.framework.mybatis.entity.BaseEntity;
import com.dandandog.modules.blog.entity.*;
import com.dandandog.modules.blog.entity.enums.MetaType;
import com.dandandog.modules.blog.service.BlogConfigService;
import com.dandandog.modules.blog.service.BlogContentService;
import com.dandandog.modules.blog.service.BlogMetaContentsService;
import com.dandandog.modules.blog.service.BlogMetaService;
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
public class BlogContentFaces {

    @Resource
    private BlogMetaService metasService;

    @Resource
    private BlogContentService contentsService;

    @Resource
    private BlogMetaContentsService metasContentsService;

    @Resource
    private BlogConfigService configService;

    public LazyDataModel<BlogArticleVo> findDataModel() {
        return MapperPageDataModel.getInstance(new BlogContentAdapter(), BlogArticleVo.class);
    }

    public Optional<BlogArticleVo> getOptById(String id) {
        return Optional.ofNullable(contentsService.getById(id)).map(entity -> MapperUtil.map(entity, BlogArticleVo.class, entityInfo()));
    }

    @Transactional
    public void saveOrUpdate(BlogArticleVo vo, Collection<BlogConfigContent> configs) {
        BlogContent entity = MapperUtil.map(vo, BlogContent.class);
        contentsService.saveOrUpdate(entity);
        // Attachment
        List<BlogContent> attachmentSource = contentsService.lambdaQuery().eq(BlogContent::getParentId, entity.getId()).list();
        Collection<BlogContent> attachmentTarget = MapperUtil.mapFromAll(vo.getAttachments(), BlogContent.class).stream()
                .peek(blogContents -> blogContents.setParentId(entity.getId())).collect(Collectors.toList());
        Collection<String> attachmentRemove = CollUtil.subtractToList(attachmentSource, attachmentTarget)
                .stream().map(BaseEntity::getId).collect(Collectors.toList());
        contentsService.removeByIds(attachmentRemove);
        contentsService.saveOrUpdateBatch(attachmentTarget);

        // tags
        metasContentsService.lambdaUpdate().eq(BlogMetasContent::getContentId, entity.getId()).remove();
        List<BlogMeta> tags = metasService.checkAndSaveTags(ArrayUtil.toArray(vo.getTags(), String.class));
        List<BlogMetasContent> metasContents = tags.stream().map(tag -> new BlogMetasContent(entity.getId(), tag.getId())).collect(Collectors.toList());

        BlogMeta cate = (BlogMeta) vo.getCategoryNode().getData();
        metasContents.add(new BlogMetasContent(entity.getId(), cate.getId()));
        metasContentsService.saveOrUpdateBatch(metasContents);
        // content config
        if (CollUtil.isNotEmpty(configs)) {
            List<BlogConfig> collect = configs.stream().peek(blogContentConfigs ->
                    blogContentConfigs.setContentId(entity.getId())).collect(Collectors.toList());
            configService.saveOrUpdateBatch(collect);
        }
    }

    public BaseContext<BlogArticleVo> entityInfo() {
        return new BaseContext<BlogArticleVo>() {
            @Override
            protected void after(BlogArticleVo target, Class<BlogArticleVo> t) {
                List<BlogContent> list = contentsService.lambdaQuery().eq(BlogContent::getParentId, target.getId()).list();
                target.setAttachments(MapperUtil.mapToAll(list, BlogAttachVo.class));

                List<BlogMeta> metas = metasContentsService.findByContentId(target.getId());

                BlogMeta cate = metas.stream().filter(blogMetas -> MetaType.CATEGORY.equals(blogMetas.getType())).findFirst().orElse(null);
                target.setCategoryNode(new DefaultTreeNode(cate));

                List<String> tags = metas.stream().filter(blogMetas -> MetaType.TAG.equals(blogMetas.getType())).map(BlogMeta::getName).collect(Collectors.toList());
                target.setTags(tags);
            }
        };
    }


    public void removeByIds(String[] idList) {
        for (String id : idList) {
            contentsService.lambdaUpdate().eq(BlogContent::getParentId, id).or().eq(BaseEntity::getId, id).remove();
        }
    }


}
