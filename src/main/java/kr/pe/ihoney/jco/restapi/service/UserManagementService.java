package kr.pe.ihoney.jco.restapi.service;

import org.springframework.data.domain.Page;

import kr.pe.ihoney.jco.restapi.common.exception.RestApiException;
import kr.pe.ihoney.jco.restapi.domain.User;
import kr.pe.ihoney.jco.restapi.service.condition.UserCondition;
import kr.pe.ihoney.jco.restapi.web.support.view.PageStatus;

public interface UserManagementService {
    Page<User> getUsers(UserCondition condition, PageStatus pageStatus);
    
    User save(User user) throws RestApiException;

    void delete(User user) throws RestApiException;
    
    User findByEmail(String email);
}
