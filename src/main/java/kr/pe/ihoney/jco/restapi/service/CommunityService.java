package kr.pe.ihoney.jco.restapi.service;

import java.util.List;

import kr.pe.ihoney.jco.restapi.common.exception.RestApiException;
import kr.pe.ihoney.jco.restapi.domain.Community;
import kr.pe.ihoney.jco.restapi.domain.User;
import kr.pe.ihoney.jco.restapi.web.support.view.PageStatus;

import org.springframework.data.domain.Page;

/**
 * Community Service
 * 
 * @author ihoneymon
 * 
 */
public interface CommunityService {
    Community save(Community community) throws RestApiException;

    void delete(Community community) throws RestApiException;

    Page<Community> getCommunities(PageStatus pageStatus);

    Community findByName(String name);
    
    List<Community> findCommunitiesByUser(User user);
}
