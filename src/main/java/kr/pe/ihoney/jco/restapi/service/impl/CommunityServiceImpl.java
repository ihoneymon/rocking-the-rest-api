package kr.pe.ihoney.jco.restapi.service.impl;

import kr.pe.ihoney.jco.restapi.common.exception.RestApiException;
import kr.pe.ihoney.jco.restapi.domain.Community;
import kr.pe.ihoney.jco.restapi.domain.Member;
import kr.pe.ihoney.jco.restapi.domain.QCommunity;
import kr.pe.ihoney.jco.restapi.repository.CommunityRepository;
import kr.pe.ihoney.jco.restapi.service.CommunityService;
import kr.pe.ihoney.jco.restapi.service.MemberService;
import kr.pe.ihoney.jco.restapi.service.condition.CommunityCondition;
import kr.pe.ihoney.jco.restapi.web.support.view.PageStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.mysema.query.BooleanBuilder;

@Service
public class CommunityServiceImpl implements CommunityService {

    @Autowired
    private CommunityRepository communityRepository;
    @Autowired
    private MemberService memberService;

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public Community save(Community community) throws RestApiException {
        if (null != communityRepository.findByName(community.getName())) {
            throw new RestApiException("group.exception.exist.sameName");
        }
        communityRepository.save(community);
        Member manager = memberService.save(new Member(community.getName() + " 관리자", community, community.getCreatedBy()));
        community.setManager(manager);
        communityRepository.saveAndFlush(community);
        
        return community;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public Community modify(Community community) {
        return communityRepository.saveAndFlush(community);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void delete(Community community) {
        communityRepository.delete(community);
        communityRepository.flush();
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "cache:communities", key = "'communities'.concat(':').concat(#condition.toString()).concat(':').concat(#pageStatus.pageableQueryString)")
    public Page<Community> getCommunities(CommunityCondition condition, PageStatus pageStatus) {
        QCommunity qCommunity = QCommunity.community;
        BooleanBuilder builder = new BooleanBuilder();
        if (condition.hasName()) {
            builder.and(qCommunity.name.contains(condition.getName()));
        }
        if (condition.hasType()) {
            builder.and(qCommunity.type.eq(condition.getType()));
        }
        if (null == pageStatus.getSort()) {
            pageStatus = pageStatus.addSort(new Sort(Direction.DESC, Lists
                    .newArrayList("id", "name")));
        }
        return communityRepository.findAll(builder, pageStatus);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "cache:group:detail", key = "'group'.concat(':').concat(#group.id.toString())")
    public Community getCommunity(Community community) {
        return communityRepository.findByName(community.getName());
    }
}
