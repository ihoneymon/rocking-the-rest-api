package kr.pe.ihoney.jco.restapi.service.impl;

import kr.pe.ihoney.jco.restapi.common.exception.RestApiException;
import kr.pe.ihoney.jco.restapi.domain.Group;
import kr.pe.ihoney.jco.restapi.domain.QGroup;
import kr.pe.ihoney.jco.restapi.repository.GroupRepository;
import kr.pe.ihoney.jco.restapi.service.GroupService;
import kr.pe.ihoney.jco.restapi.service.condition.GroupCondition;
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
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public Group save(Group group) throws RestApiException {
        if (null != groupRepository.findByName(group.getName())) {
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
    public void delete(Group group) {
        groupRepository.delete(group);
        groupRepository.flush();
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "cache:groups", key = "'groups'.concat(':').concat(#condition.toString()).concat(':').concat(#pageStatus.pageableQueryString)")
    public Page<Group> getGroups(GroupCondition condition, PageStatus pageStatus) {
        QGroup qGroup = QGroup.group;
        BooleanBuilder builder = new BooleanBuilder();
        if (condition.hasName()) {
            builder.and(qGroup.name.contains(condition.getName()));
        }
        if (condition.hasType()) {
            builder.and(qGroup.type.eq(condition.getType()));
        }
        if (null == pageStatus.getSort()) {
            pageStatus = pageStatus.addSort(new Sort(Direction.DESC, Lists
                    .newArrayList("id", "name")));
        }
        return groupRepository.findAll(builder, pageStatus);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "cache:group:detail", key = "'group'.concat(':').concat(#group.id.toString())")
    public Group getGroup(Group group) {
        return groupRepository.findByName(group.getName());
    }
}
