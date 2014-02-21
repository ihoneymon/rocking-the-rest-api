package kr.pe.ihoney.jco.restapi.service.impl;

import kr.pe.ihoney.jco.restapi.common.exception.RestApiException;
import kr.pe.ihoney.jco.restapi.domain.Group;
import kr.pe.ihoney.jco.restapi.domain.QGroup;
import kr.pe.ihoney.jco.restapi.repository.GroupRepository;
import kr.pe.ihoney.jco.restapi.service.GroupService;
import kr.pe.ihoney.jco.restapi.service.condition.GroupCondition;
import kr.pe.ihoney.jco.restapi.web.support.view.PageStatus;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.BooleanBuilder;

@Slf4j
@Service
public class GroupServiceImpl implements GroupService {
    @Autowired
    private GroupRepository groupRepository;

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public Group save(Group group) {
        if(null != groupRepository.findByName(group.getName())) {
            throw new RestApiException("group.exception.exist.sameName");
        }
        return groupRepository.saveAndFlush(group);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public Group modify(Group group) {
        return groupRepository.saveAndFlush(group);
    }
    
    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    @CacheEvict(value="cache:groups", allEntries=true)
    public void delete(Group group) {
        log.debug(">> Delete group: {}", group);
        groupRepository.delete(group);
        groupRepository.flush();
    }

    @Override
    @Transactional(readOnly=true)
    @Cacheable(value="cache:groups", key="'users'.concat(':').concat(#condition.toString()).concat(':').concat(#pageStatus.pageableQueryString)")
    public Page<Group> getGroups(GroupCondition condition, PageStatus pageStatus) {
        QGroup qGroup = QGroup.group;
        BooleanBuilder builder = new BooleanBuilder();
        if(condition.hasName()) {
            builder.and(qGroup.name.contains(condition.getName()));
        }
        if(condition.hasType()) {
            builder.and(qGroup.type.eq(condition.getType()));
        }
        
        return groupRepository.findAll(builder, pageStatus);
    }

}
