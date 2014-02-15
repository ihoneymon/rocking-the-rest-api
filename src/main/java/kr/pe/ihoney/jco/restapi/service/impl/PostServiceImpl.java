package kr.pe.ihoney.jco.restapi.service.impl;

import kr.pe.ihoney.jco.restapi.common.exception.RestApiException;
import kr.pe.ihoney.jco.restapi.domain.Post;
import kr.pe.ihoney.jco.restapi.repository.PostRepository;
import kr.pe.ihoney.jco.restapi.service.PostService;
import kr.pe.ihoney.jco.restapi.web.support.view.PageStatus;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 * User: ihoneymon
 * Date: 14. 2. 2
 * Time: 오후 12:52
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Override
    @Transactional(readOnly=false, rollbackFor=Exception.class)
    public Post save(Post post) throws RestApiException {
        if(null != findByTitle(post.getTitle())) {
            throw new RestApiException("post.exception.exist-same-title-post");
        }
        return postRepository.saveAndFlush(post);
    }

    @Override
    @Transactional(readOnly=false, rollbackFor=Exception.class)
    @CacheEvict(value="cache:post:detail", key="'post'.concat(':').concat(#post.title)")
    public void delete(Post post) throws RestApiException {
        log.debug(">> Delete Post: {}", post);
        postRepository.delete(post);
        postRepository.flush();
    }

    @Override
    @Cacheable(value="cache:post:detail", key="'post'.concat(':').concat(#post.title)")
    public Post findByTitle(String title) {
        return postRepository.findByTitle(title);
    }
    
    @Override
    @Cacheable(value="cache:posts", key="'posts'.concate(':').concate(#pageStatus.pageNumber)")
    public Page<Post> posts(PageStatus pageStatus) {
        return postRepository.findAll(pageStatus);
    }

}
