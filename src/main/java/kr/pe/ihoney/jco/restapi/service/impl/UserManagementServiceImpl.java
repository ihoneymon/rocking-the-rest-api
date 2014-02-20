package kr.pe.ihoney.jco.restapi.service.impl;


import kr.pe.ihoney.jco.restapi.common.exception.RestApiException;
import kr.pe.ihoney.jco.restapi.domain.QMember;
import kr.pe.ihoney.jco.restapi.domain.QUser;
import kr.pe.ihoney.jco.restapi.domain.User;
import kr.pe.ihoney.jco.restapi.repository.UserRepository;
import kr.pe.ihoney.jco.restapi.service.UserManagementService;
import kr.pe.ihoney.jco.restapi.service.condition.UserCondition;
import kr.pe.ihoney.jco.restapi.web.support.view.PageStatus;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.mysema.query.BooleanBuilder;

@Slf4j
@Service
public class UserManagementServiceImpl implements UserManagementService {

    @Autowired
    private UserRepository userRepository;
    
    @Override
    @Transactional(readOnly=true)
    @Cacheable(value = "cache:users", key = "'users'.concat(':').concat(#condition.toString()).concat(':').concat(#pageStatus.pageNumber.toString())")
    public Page<User> getUsers(UserCondition condition, PageStatus pageStatus) {
        QMember qMember = QMember.member;
        QUser qUser = QUser.user;
        BooleanBuilder builder = new BooleanBuilder();
        if (StringUtils.hasText(condition.getName())) {
            builder.and(qUser.name.contains(condition.getName()));
        }
        if (StringUtils.hasText(condition.getEmail())) {
            builder.and(qUser.email.contains(condition.getEmail()));
        }
        if (null != condition.getCommunity()) {
            builder.and(qMember.user.eq(qUser).and(qMember.community.eq(condition.getCommunity())));
        }
        return userRepository.findAll(builder, pageStatus);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    @Cacheable(value = "cache:user:detail", key = "'user'.concat(':').concat(#user.email)")
    public User save(User user) throws RestApiException {
        if (null != findByEmail(user.getEmail())) {
            throw new RestApiException("user.exception.existUser");
        }
        log.debug(">> Save user: {}", user);
        return userRepository.saveAndFlush(user);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    @CacheEvict(value = "cache:user:detail", key = "'user'.concat(':').concat(#user.email)")
    public void delete(User user) throws RestApiException {
        try {
            userRepository.delete(user);
            userRepository.flush();
        } catch (Exception e) {
            throw new RestApiException(e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "cache:user:detail", key = "'user'.concat(':').concat(#email)")
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
