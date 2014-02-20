package kr.pe.ihoney.jco.restapi.service;

import java.util.List;

import kr.pe.ihoney.jco.restapi.common.exception.RestApiException;
import kr.pe.ihoney.jco.restapi.domain.Member;
import kr.pe.ihoney.jco.restapi.domain.Post;
import kr.pe.ihoney.jco.restapi.domain.User;
import kr.pe.ihoney.jco.restapi.service.condition.PostCondition;
import kr.pe.ihoney.jco.restapi.web.support.view.PageStatus;

import org.springframework.data.domain.Page;

/**
 * Post 서비스
 * @author ihoneymon
 *
 */
public interface PostService {
    /**
     * 
     * @param member
     * @param condition
     * @param pageStatus
     * @return
     */
    Page<Post> getPostsOfMember(Member member, PostCondition condition, PageStatus pageStatus);
    
    Post getPost(Post post);
    
    Post save(Post post) throws RestApiException;

    void delete(Post post) throws RestApiException;

    void deletePostsOfMember(Member member);

    List<Post> findPostsOfUser(User user);
}
