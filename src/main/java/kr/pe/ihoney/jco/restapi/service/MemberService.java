package kr.pe.ihoney.jco.restapi.service;

import kr.pe.ihoney.jco.restapi.domain.Group;
import kr.pe.ihoney.jco.restapi.domain.Member;
import kr.pe.ihoney.jco.restapi.domain.User;
import kr.pe.ihoney.jco.restapi.service.condition.MemberCondition;
import kr.pe.ihoney.jco.restapi.web.support.view.PageStatus;

import org.springframework.data.domain.Page;

public interface MemberService extends GenericDomainService<Member> {

    Page<Member> getMembersOfGroup(Group group, MemberCondition condition,
            PageStatus pageStatus);

    Member addMember(String nickName, Group group, User user);

}
