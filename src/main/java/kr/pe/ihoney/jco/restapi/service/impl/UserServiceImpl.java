package kr.pe.ihoney.jco.restapi.service.impl;

import kr.pe.ihoney.jco.restapi.common.exception.RestApiException;
import kr.pe.ihoney.jco.restapi.domain.QUser;
import kr.pe.ihoney.jco.restapi.domain.User;
import kr.pe.ihoney.jco.restapi.repository.UserRepository;
import kr.pe.ihoney.jco.restapi.service.UserService;
import kr.pe.ihoney.jco.restapi.service.condition.UserCondition;
import kr.pe.ihoney.jco.restapi.web.support.view.PageStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.mysema.query.BooleanBuilder;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public User save(User user) throws RestApiException {
        if (null != userRepository.findByEmail(user.getEmail())) {
            throw new RestApiException("user.exception.existUser");
        }
        return userRepository.saveAndFlush(user);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public User modify(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void delete(User user) {
        userRepository.delete(user);
        userRepository.flush();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User> getUsers(UserCondition condition, PageStatus pageStatus) {
        QUser qUser = QUser.user;
        BooleanBuilder builder = new BooleanBuilder();
        if (condition.hasName()) {
            builder.and(qUser.name.contains(condition.getName()));
        }
        if (condition.hasEmail()) {
            builder.and(qUser.email.contains(condition.getEmail()));
        }
        if (null == pageStatus.getSort()) {
            pageStatus = pageStatus.addSort(new Sort(Direction.DESC, Lists
                    .newArrayList("createdDate", "id")));
        }

        return userRepository.findAll(builder, pageStatus);
    }

    @Override
    @Transactional(readOnly=true)
    @Cacheable(value="cache:user:detail", key="'user'.concat(':').concat(#user.email)")
    public User getUser(User user) {
        return userRepository.findByEmail(user.getEmail());
    }
}
