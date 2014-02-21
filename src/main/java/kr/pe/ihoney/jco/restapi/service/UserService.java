package kr.pe.ihoney.jco.restapi.service;

import kr.pe.ihoney.jco.restapi.domain.User;
import kr.pe.ihoney.jco.restapi.service.condition.UserCondition;
import kr.pe.ihoney.jco.restapi.web.support.view.PageStatus;

import org.springframework.data.domain.Page;

public interface UserService extends GenericDomainService<User>{

    Page<User> getUsers(UserCondition condition, PageStatus pageStatus);

}
