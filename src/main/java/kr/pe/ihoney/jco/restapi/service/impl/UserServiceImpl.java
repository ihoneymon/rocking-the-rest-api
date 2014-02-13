package kr.pe.ihoney.jco.restapi.service.impl;

import kr.pe.ihoney.jco.restapi.common.exception.RestApiException;
import kr.pe.ihoney.jco.restapi.domain.User;
import kr.pe.ihoney.jco.restapi.repository.UserRepository;
import kr.pe.ihoney.jco.restapi.service.UserService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly=false, rollbackFor=RestApiException.class)
    public User save(User user) throws RestApiException {
        User existUser = userRepository.findByEmail(user.getName());
        if(existUser != null) {
            throw new RestApiException("user.exception.exist-user");
        }
        log.debug(">> Save user: {}", user);
        return userRepository.saveAndFlush(user);
    }

    @Override
    public void delete(User user) throws RestApiException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
