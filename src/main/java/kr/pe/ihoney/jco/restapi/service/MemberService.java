package kr.pe.ihoney.jco.restapi.service;

import org.springframework.data.domain.Page;

import kr.pe.ihoney.jco.restapi.domain.Community;
import kr.pe.ihoney.jco.restapi.domain.Member;
import kr.pe.ihoney.jco.restapi.web.support.view.PageStatus;

public interface MemberService {

    Page<Member> findMemberByCommunity(Community community, PageStatus pageStatus);

    Member save(Member member);

}
