package kr.pe.ihoney.jco.restapi.service;

import kr.pe.ihoney.jco.restapi.domain.Community;
import kr.pe.ihoney.jco.restapi.domain.Member;
import kr.pe.ihoney.jco.restapi.service.condition.MemberCondition;
import kr.pe.ihoney.jco.restapi.web.support.view.PageStatus;

import org.springframework.data.domain.Page;

public interface MemberService {

    /**
     * 회원목록 조회
     * @param community
     * @param condition
     * @param pageStatus
     * @return
     */
    Page<Member> findMembersByCommunity(Community community, MemberCondition condition, PageStatus pageStatus);

    Member save(Member member);

    void delete(Member member);
    
    Member getMember(Member member);
}
