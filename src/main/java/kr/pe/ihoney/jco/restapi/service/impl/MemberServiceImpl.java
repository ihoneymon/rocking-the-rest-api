package kr.pe.ihoney.jco.restapi.service.impl;

import kr.pe.ihoney.jco.restapi.domain.Community;
import kr.pe.ihoney.jco.restapi.domain.Member;
import kr.pe.ihoney.jco.restapi.domain.QMember;
import kr.pe.ihoney.jco.restapi.repository.MemberRepository;
import kr.pe.ihoney.jco.restapi.service.MemberService;
import kr.pe.ihoney.jco.restapi.web.support.view.PageStatus;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.mysema.query.BooleanBuilder;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberRepository memberRepository;

    public Page<Member> findMemberByCommunity(Community community, PageStatus pageStatus) {
        log.debug(">> Find Members of community: {}", community);
        BooleanBuilder builder = memberBuilder(community);
        return memberRepository.findAll(builder, pageStatus);
    }

    private BooleanBuilder memberBuilder(Community community) {
        QMember qMember = QMember.member;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qMember.community.eq(community));
        return builder;
    }

    @Override
    public Member save(Member member) {
        return memberRepository.saveAndFlush(member);
    }

}
