package kr.pe.ihoney.jco.restapi.service;

import kr.pe.ihoney.jco.restapi.common.exception.RestApiException;
import kr.pe.ihoney.jco.restapi.domain.Post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created with IntelliJ IDEA.
 * User: ihoneymon
 * Date: 14. 2. 2
 * Time: 오후 12:08
 */
public interface PostService {
    Post save(Post post) throws RestApiException;
    void delete(Post post) throws RestApiException;
    Page<Post> findAll(Pageable pageable);
}
