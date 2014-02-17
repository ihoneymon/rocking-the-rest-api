package kr.pe.ihoney.jco.restapi.service;

import kr.pe.ihoney.jco.restapi.common.exception.RestApiException;
import kr.pe.ihoney.jco.restapi.domain.Community;
import kr.pe.ihoney.jco.restapi.domain.Member;
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

    Page<Community> communities(PageStatus pageStatus);

    Community findByName(String name);

    /**
     * 커뮤니티의 회원 조회
     * @param community
     * @param pageStatus
     * @return 
     */
    Page<Member> findMembersByCommunity(Community community, PageStatus pageStatus);

    Member registerMember(String nickName, Community community, User user);
}
