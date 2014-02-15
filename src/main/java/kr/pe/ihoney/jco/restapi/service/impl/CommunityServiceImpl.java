package kr.pe.ihoney.jco.restapi.service.impl;

import kr.pe.ihoney.jco.restapi.common.exception.RestApiException;
import kr.pe.ihoney.jco.restapi.domain.Community;
import kr.pe.ihoney.jco.restapi.repository.CommunityRepository;
import kr.pe.ihoney.jco.restapi.service.CommunityService;
import kr.pe.ihoney.jco.restapi.web.support.view.PageStatus;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class CommunityServiceImpl implements CommunityService {
    @Autowired
    private CommunityRepository communityRepository;

    @Override
    @Transactional(readOnly=false, rollbackFor=Exception.class)
    @Cacheable(value="cache:community:detail", key="'community'.concat(':').concat(#community.name)")
    public Community save(Community community) throws RestApiException {
        if(null != findByName(community.getName())) {
            throw new RestApiException("community.exception.exist-same-name-community");
        }
        return communityRepository.saveAndFlush(community);
    }

    @Override
    @Transactional(readOnly=false, rollbackFor=Exception.class)
    @CacheEvict(value={"cache:community:detail"}, key="'community'.concat(':').concat(#community.name)")
    public void delete(Community community) throws RestApiException {
        log.debug(">> Remove Community: {}", community);
        communityRepository.delete(community);
        communityRepository.flush();
    }

    @Override
    @Transactional(readOnly=true)
    @Cacheable(value="cache:communities", key="'communities'.concat(':').concat(#pageStatus.pageNumber)")
    public Page<Community> communities(PageStatus pageStatus) {
        return communityRepository.findAll(pageStatus);
    }

    @Override
    @Transactional(readOnly=true)
    @Cacheable(value="cache:community:detail", key="'community'.concat(':').concat(#name)")
    public Community findByName(String name) {
        return communityRepository.findByName(name);
    }
}
