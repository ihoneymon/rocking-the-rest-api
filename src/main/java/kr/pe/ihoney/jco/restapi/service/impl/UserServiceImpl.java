package kr.pe.ihoney.jco.restapi.service.impl;

import java.util.List;

import kr.pe.ihoney.jco.restapi.domain.Community;
import kr.pe.ihoney.jco.restapi.domain.Post;
import kr.pe.ihoney.jco.restapi.domain.User;
import kr.pe.ihoney.jco.restapi.repository.UserRepository;
import kr.pe.ihoney.jco.restapi.service.CommunityService;
import kr.pe.ihoney.jco.restapi.service.MemberService;
import kr.pe.ihoney.jco.restapi.service.PostService;
import kr.pe.ihoney.jco.restapi.service.UserService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
    @Autowired
    private CommunityService communityService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private PostService postService;

    @Override
    @Transactional(readOnly=true)
    @Cacheable(value="cache:user:detail", key="'user'.concat(':').concat(#user.id.toString())")
    public User getUser(User user) {
        log.debug(">> Get User: {}", user);
        return userRepository.findOne(user.getId());
    }

    @Override
    @Transactional(readOnly=false, rollbackFor=Exception.class)
    @Cacheable(value="cache:user:detail", key="'user'.concat(':').concat(#user.id.toString())")
    public User save(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    @Transactional(readOnly=true)
    public List<Community> findCommunitiesOfUser(User user) {
        return communityService.findCommunitiesByUser(user);
    }

    @Override
    public List<Post> findPostsOfUser(User user) {
        return postService.findPostsOfUser(user);
    }
}
