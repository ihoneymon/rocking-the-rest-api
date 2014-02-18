package kr.pe.ihoney.jco.restapi.service.impl;

import java.util.List;

import kr.pe.ihoney.jco.restapi.common.exception.RestApiException;
import kr.pe.ihoney.jco.restapi.domain.User;
import kr.pe.ihoney.jco.restapi.repository.UserRepository;
import kr.pe.ihoney.jco.restapi.service.UserService;
import kr.pe.ihoney.jco.restapi.web.support.view.PageStatus;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author ihoneymon
 * 
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    @Cacheable(value = "cache:user:detail", key = "'user'.concat(':').concat(#user.email)")
    public User save(User user) throws RestApiException {
        if (null != findByEmail(user.getEmail())) {
            throw new RestApiException("user.exception.exist-user");
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

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "cache:users", key = "'users'.concat(':').concat(#pageStatus.pageNumber)")
    public Page<User> users(PageStatus pageStatus) {
        return userRepository.findAll(pageStatus);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
