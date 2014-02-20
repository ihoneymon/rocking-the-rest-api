package kr.pe.ihoney.jco.restapi.service;

import java.util.List;

import kr.pe.ihoney.jco.restapi.domain.Community;
import kr.pe.ihoney.jco.restapi.domain.Post;
import kr.pe.ihoney.jco.restapi.domain.User;


/**
 * 사용자 서비스
 * 
 * @author ihoneymon
 * 
 */
public interface UserService {

    User getUser(User user);

    User save(User bind);

    List<Community> findCommunitiesOfUser(User user);

    List<Post> findPostsOfUser(User user);

}
