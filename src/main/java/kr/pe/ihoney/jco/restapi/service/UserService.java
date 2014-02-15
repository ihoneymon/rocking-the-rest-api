package kr.pe.ihoney.jco.restapi.service;

import kr.pe.ihoney.jco.restapi.common.exception.RestApiException;
import kr.pe.ihoney.jco.restapi.domain.User;
import kr.pe.ihoney.jco.restapi.web.support.view.PageStatus;

import org.springframework.data.domain.Page;

/**
 * 사용자 서비스
 * 
 * @author ihoneymon
 * 
 */
public interface UserService {
    User save(User user) throws RestApiException;

    void delete(User user) throws RestApiException;
    
    User findByEmail(String email);

    Page<User> users(PageStatus pageStatus);
}
