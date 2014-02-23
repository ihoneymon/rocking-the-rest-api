package kr.pe.ihoney.jco.restapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.BooleanBuilder;

import kr.pe.ihoney.jco.restapi.common.exception.RestApiException;
import kr.pe.ihoney.jco.restapi.domain.Community;
import kr.pe.ihoney.jco.restapi.domain.Member;
import kr.pe.ihoney.jco.restapi.domain.QMember;
import kr.pe.ihoney.jco.restapi.repository.MemberRepository;
import kr.pe.ihoney.jco.restapi.service.MemberService;
import kr.pe.ihoney.jco.restapi.service.condition.MemberCondition;
import kr.pe.ihoney.jco.restapi.web.support.view.PageStatus;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public Member save(Member member) throws RestApiException {
        Member existMember = memberRepository.findByNickName(member
                .getNickName());
        if (null != existMember
                && existMember.getCommunity().equals(member.getCommunity())) {
            throw new RestApiException(
                    "member.exception.dont.duplicate.registered");
        }
        return memberRepository.saveAndFlush(member);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public Member modify(Member member) {
        return memberRepository.saveAndFlush(member);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(Member member) {
        memberRepository.delete(member);
        memberRepository.flush();
    }

    @Override
    public Page<Member> getMembers(Community community, MemberCondition condition,
            PageStatus pageStatus) {
        QMember qMember = QMember.member;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qMember.community.eq(community));

        if (condition.hasNickName()) {
            builder.and(qMember.nickName.contains(condition.getNickName()));
        }
        if (null == pageStatus.getSort()) {
            pageStatus = pageStatus.addSort(new Sort(Direction.DESC, "id"));
        }

        return memberRepository.findAll(builder, pageStatus);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "cache:member:detail", key = "'member'.concat(':').concat(#member.toString())")
    public Member getMember(Member member) {
        return memberRepository.findByNickName(member.getNickName());
    }

}
