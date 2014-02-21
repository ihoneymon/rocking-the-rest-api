package kr.pe.ihoney.jco.restapi.service.impl;

import kr.pe.ihoney.jco.restapi.common.exception.RestApiException;
import kr.pe.ihoney.jco.restapi.domain.Group;
import kr.pe.ihoney.jco.restapi.domain.Member;
import kr.pe.ihoney.jco.restapi.domain.QGroup;
import kr.pe.ihoney.jco.restapi.domain.QMember;
import kr.pe.ihoney.jco.restapi.domain.User;
import kr.pe.ihoney.jco.restapi.repository.MemberRepository;
import kr.pe.ihoney.jco.restapi.service.GroupService;
import kr.pe.ihoney.jco.restapi.service.MemberService;
import kr.pe.ihoney.jco.restapi.service.condition.MemberCondition;
import kr.pe.ihoney.jco.restapi.web.support.view.PageStatus;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.BooleanBuilder;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private GroupService groupService;
    @Autowired
    private MemberRepository memberRepository;

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public Member save(Member member) throws RestApiException {
        if (null != memberRepository.findMemberByNickName(member.getNickName())) {
            throw new RestApiException("member.exception.exist.sameEmail");
        }
        return memberRepository.saveAndFlush(member);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public Member modify(Member member) {
        return memberRepository.saveAndFlush(member);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    @CacheEvict(value="cache:members", allEntries=true)
    public void delete(Member member) {
        log.debug(">> Delete member: {}", member);
        memberRepository.delete(member);
        memberRepository.flush();
    }
    
    @Override
    @Transactional(readOnly=true)
    @Cacheable(value="cache:groups", key="'members'.concat(':').concat(#group.toString()).concat(':').concat(#condition.toString()).concat(':').concat(#pageStatus.pageableQueryString)")
    public Page<Member> getMembersOfGroup(Group group,
            MemberCondition condition, PageStatus pageStatus) {
        QMember qMember = QMember.member;
        QGroup qGroup = QGroup.group;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qGroup.eq(group));
        builder.and(qGroup.members.contains(qMember));
        
        if(condition.hasNickName()) {
            builder.and(qMember.nickName.contains(condition.getNickName()));
        }
        if(condition.hasGroup()) {
            builder.and(qMember.group.eq(condition.getGroup()));
        }
        if(null == pageStatus.getSort()) {
            pageStatus = pageStatus.addSort(new Sort(Direction.DESC, "nickName"));
        }
        
        return memberRepository.findAll(builder, pageStatus);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public Member addMember(String nickName, Group group, User user) {
        Member member = new Member(nickName, group, user);
        groupService.modify(group.addMember(member));
        return save(member);
    }

}
