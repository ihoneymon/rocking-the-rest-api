package kr.pe.ihoney.jco.restapi.service.impl;

import kr.pe.ihoney.jco.restapi.common.exception.RestApiException;
import kr.pe.ihoney.jco.restapi.domain.Group;
import kr.pe.ihoney.jco.restapi.domain.Member;
import kr.pe.ihoney.jco.restapi.domain.Post;
import kr.pe.ihoney.jco.restapi.domain.QPost;
import kr.pe.ihoney.jco.restapi.repository.PostRepository;
import kr.pe.ihoney.jco.restapi.service.PostService;
import kr.pe.ihoney.jco.restapi.service.condition.PostCondition;
import kr.pe.ihoney.jco.restapi.web.support.view.PageStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.BooleanBuilder;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public Post save(Post post) throws RestApiException {
        return postRepository.saveAndFlush(post);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public Post modify(Post post) {
        return postRepository.saveAndFlush(post);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void delete(Post post) {
        postRepository.delete(post);
        postRepository.flush();
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "cache:posts", key = "'posts'.concat(':').concat(#group.toString()).concat(':').concat(#condition.toString()).concat(':').concat(#pageStatus.pageableQueryString)")
    public Page<Post> getPostsOfGroup(Group group, PostCondition condition,
            PageStatus pageStatus) {
        QPost qPost = QPost.post;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qPost.group.eq(group));

        if (condition.hasTitle()) {
            builder.and(qPost.title.contains(condition.getTitle()));
        }
        if (condition.hasArticle()) {
            builder.and(qPost.article.contains(condition.getArticle()));
        }
        if (null == pageStatus.getSort()) {
            pageStatus = pageStatus.addSort(new Sort(Direction.DESC, "id"));
        }

        return postRepository.findAll(builder, pageStatus);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "cache:post:detail", key = "'post'.concat(':').concat(#post.id.toString())")
    public Post getPost(Post post) {
        return postRepository.findOne(post.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Post> getPostsOfMemberInGroup(Group group, Member member,
            PostCondition condition, PageStatus pageStatus) {
        QPost qPost = QPost.post;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qPost.group.eq(group));
        builder.and(qPost.createdBy.eq(member));

        if (condition.hasTitle()) {
            builder.and(qPost.title.contains(condition.getTitle()));
        }
        if (condition.hasArticle()) {
            builder.and(qPost.article.contains(condition.getArticle()));
        }
        if (null == pageStatus.getSort()) {
            pageStatus = pageStatus.addSort(new Sort(Direction.DESC, "id"));
        }

        return postRepository.findAll(builder, pageStatus);
    }

}
