package kr.pe.ihoney.jco.restapi.service.impl;

import kr.pe.ihoney.jco.restapi.common.exception.RestApiException;
import kr.pe.ihoney.jco.restapi.domain.QUser;
import kr.pe.ihoney.jco.restapi.domain.User;
import kr.pe.ihoney.jco.restapi.repository.UserRepository;
import kr.pe.ihoney.jco.restapi.service.UserService;
import kr.pe.ihoney.jco.restapi.service.condition.UserCondition;
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
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly=false, rollbackFor=Exception.class)
    public User save(User newUser) throws RestApiException {
        if(null != userRepository.findByEmail(newUser.getEmail())) {
            throw new RestApiException("user.exception.exist.sameEmail");
        }
        return userRepository.saveAndFlush(newUser);
    }

    @Override
    @Transactional(readOnly=false, rollbackFor=Exception.class)
    public User modify(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    @Transactional(readOnly=false, rollbackFor=Exception.class)
    @CacheEvict(value="cache:users", allEntries=true)
    public void delete(User user) {
        log.debug(">> Delete User: {}", user);
        userRepository.delete(user);
        userRepository.flush();
    }

    @Override
    @Transactional(readOnly=true)
    @Cacheable(value="cache:users", key="'users'.concat(':').concat(#condition.toString()).concat(':').concat(#pageStatus.pageableQueryString)")
    public Page<User> getUsers(UserCondition condition, PageStatus pageStatus) {
        BooleanBuilder builder = new BooleanBuilder();
        QUser qUser = QUser.user;
        if(condition.hasEmail()) {
            builder.and(qUser.email.contains(condition.getEmail()));
        }
        if(condition.hasName()) {
            builder.and(qUser.name.contains(condition.getName()));
        }
        if(null == pageStatus.getSort()) {
            pageStatus = pageStatus.addSort(new Sort(Direction.DESC, "createdDate"));
        }
        return userRepository.findAll(builder, pageStatus);
    }
}
