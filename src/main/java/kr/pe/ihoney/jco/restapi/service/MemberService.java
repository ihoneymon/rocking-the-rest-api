package kr.pe.ihoney.jco.restapi.service;

import kr.pe.ihoney.jco.restapi.domain.Community;
import kr.pe.ihoney.jco.restapi.domain.Member;
import kr.pe.ihoney.jco.restapi.service.condition.MemberCondition;
import kr.pe.ihoney.jco.restapi.web.support.view.PageStatus;

import org.springframework.data.domain.Page;

public interface MemberService extends GeneralDomainService<Member> {

    Page<Member> getMembers(Community community, MemberCondition condition,
            PageStatus pageStatus);

    Member getMember(Member member);

}
