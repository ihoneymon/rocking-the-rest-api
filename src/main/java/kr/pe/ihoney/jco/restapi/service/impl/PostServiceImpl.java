package kr.pe.ihoney.jco.restapi.service.impl;

import java.util.List;

import kr.pe.ihoney.jco.restapi.common.exception.RestApiException;
import kr.pe.ihoney.jco.restapi.domain.Community;
import kr.pe.ihoney.jco.restapi.domain.Member;
import kr.pe.ihoney.jco.restapi.domain.Post;
import kr.pe.ihoney.jco.restapi.domain.QMember;
import kr.pe.ihoney.jco.restapi.domain.QPost;
import kr.pe.ihoney.jco.restapi.domain.User;
import kr.pe.ihoney.jco.restapi.repository.PostRepository;
import kr.pe.ihoney.jco.restapi.service.PostService;
import kr.pe.ihoney.jco.restapi.service.condition.PostCondition;
import kr.pe.ihoney.jco.restapi.web.support.view.PageStatus;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;
import com.mysema.query.BooleanBuilder;

/**
 * 포스트 서비스
 * @author ihoneymon
 *
 */
@Slf4j
@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public Post save(Post post) throws RestApiException {
        if (null != postRepository.findByTitle(post.getTitle())) {
            throw new RestApiException("post.exception.exist-same-title-post");
        }
        return postRepository.saveAndFlush(post);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    @CacheEvict(value = "cache:post:deail", key = "'post'.concat(':').concat(#post.id.toString())")
    public void delete(Post post) throws RestApiException {
        log.debug(">> Delete Post: {}", post);
        postRepository.delete(post);
        postRepository.flush();
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void deletePostsOfMember(Member member) {
        postRepository.delete(getPostsOfMember(member));
    }

    private Iterable<Post> getPostsOfMember(Member member) {
        QPost qPost = QPost.post;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qPost.member.eq(member));
        Iterable<Post> postsOfMember = postRepository.findAll(builder);
        return postsOfMember;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "cache:posts", key = "'posts'.concat(':').concat(#condition.toString()).concat(':').concat(#pageStatus.pageNumber.toString())")
    public Page<Post> getPostsOfMember(Member member, PostCondition condition, PageStatus pageStatus) {
        QPost qPost = QPost.post;
        BooleanBuilder builder = new BooleanBuilder();
        if (StringUtils.hasText(condition.getTitle())) {
            builder.and(qPost.title.contains(condition.getTitle()));
        }
        if (StringUtils.hasText(condition.getArticle())) {
            builder.and(qPost.article.contains(condition.getArticle()));
        }
        builder.and(qPost.member.eq(member));

        return postRepository.findAll(builder, pageStatus);
    }

    @Override
    @Transactional(readOnly=true)
    @Cacheable(value = "cache:post:deail", key = "'post'.concat(':').concat(#post.id.toString())")
    public Post getPost(Post post) {
        return postRepository.findOne(post.getId());
    }

    @Override
    @Transactional(readOnly=true)
    public List<Post> findPostsOfUser(User user) {
        QMember qMember = QMember.member;
        QPost qPost = QPost.post;
        
        BooleanBuilder builder = new BooleanBuilder();
        //builder.and(qMem)
        return null;
    }
}
