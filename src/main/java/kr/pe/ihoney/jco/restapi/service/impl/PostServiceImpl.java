package kr.pe.ihoney.jco.restapi.service.impl;

import kr.pe.ihoney.jco.restapi.domain.Group;
import kr.pe.ihoney.jco.restapi.domain.Post;
import kr.pe.ihoney.jco.restapi.domain.QPost;
import kr.pe.ihoney.jco.restapi.repository.PostRepository;
import kr.pe.ihoney.jco.restapi.service.PostService;
import kr.pe.ihoney.jco.restapi.service.condition.PostCondition;
import kr.pe.ihoney.jco.restapi.web.support.view.PageStatus;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.BooleanBuilder;

@Slf4j
@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public Post save(Post post) {
        return postRepository.saveAndFlush(post);
    }
    
    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public Post modify(Post post) {
        return postRepository.saveAndFlush(post);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    @CacheEvict(value="cache:posts", allEntries=true)
    public void delete(Post post) {
        log.debug(">> Delete post: {}", post);
        postRepository.delete(post);
        postRepository.flush();
    }

    @Override
    public Page<Post> getPosts(PostCondition condition, PageStatus pageStatus) {
        QPost qPost = QPost.post;
        BooleanBuilder builder = new BooleanBuilder();
        return postRepository.findAll(builder,
                buildPostSearchQuery(condition, pageStatus, qPost, builder));
    }

    @Override
    @Cacheable(value="cache:posts", key="'posts'.concat(':').concat(#group.toString()).concat(':').concat(#condition.toString()).concat(':').concat(#pageStatus.pageableQueryString)")
    public Page<Post> getPostsOfGroup(Group group, PostCondition condition,
            PageStatus pageStatus) {

        QPost qPost = QPost.post;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qPost.group.eq(group));

        return postRepository.findAll(builder,
                buildPostSearchQuery(condition, pageStatus, qPost, builder));
    }

    private PageStatus buildPostSearchQuery(PostCondition condition,
            PageStatus pageStatus, QPost qPost, BooleanBuilder builder) {
        if (condition.hasTitle()) {
            builder.and(qPost.title.contains(condition.getTitle()));
        }
        if (condition.hasArticle()) {
            builder.and(qPost.article.contains(condition.getArticle()));
        }
        if (condition.hasCreatorName()) {
            builder.and(qPost.createdBy.nickName.contains(condition
                    .getCreatorName()));
        }
        if (null == pageStatus.getSort()) {
            pageStatus = pageStatus.addSort(new Sort(Direction.DESC, "id"));
        }
        return pageStatus;
    }

    

}
