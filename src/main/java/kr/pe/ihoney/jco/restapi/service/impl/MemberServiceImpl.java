package kr.pe.ihoney.jco.restapi.service.impl;

import java.util.List;

import kr.pe.ihoney.jco.restapi.domain.Community;
import kr.pe.ihoney.jco.restapi.domain.Member;
import kr.pe.ihoney.jco.restapi.domain.QMember;
import kr.pe.ihoney.jco.restapi.domain.User;
import kr.pe.ihoney.jco.restapi.repository.MemberRepository;
import kr.pe.ihoney.jco.restapi.service.MemberService;
import kr.pe.ihoney.jco.restapi.service.PostService;
import kr.pe.ihoney.jco.restapi.service.condition.MemberCondition;
import kr.pe.ihoney.jco.restapi.web.support.view.PageStatus;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.mysema.query.BooleanBuilder;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PostService postService;

    @Transactional(readOnly = true)
    @Cacheable(value = "cache:members", key = "'members'.concat(':').concat('#condition.toString()').concat(':').concat(#pageStatus.pageNumber.toString())")
    public Page<Member> findMembersByCommunity(Community community, MemberCondition condition, PageStatus pageStatus) {
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
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public Member save(Member member) {
        return memberRepository.saveAndFlush(member);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    @CacheEvict(value = "cache:member:detail", key = "'member'.concat(':').concat(#member.id.toString())")
    public void delete(Member member) {
        postService.deletePostsOfMember(member);
        memberRepository.delete(member);
        memberRepository.flush();
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "cache:member:detail", key = "'member'.concat(':').concat(#member.id.toString())")
    public Member getMember(Member member) {
        return memberRepository.findOne(member.getId());
    }
}
