package kr.pe.ihoney.jco.restapi.service;

import kr.pe.ihoney.jco.restapi.domain.Group;
import kr.pe.ihoney.jco.restapi.service.condition.GroupCondition;
import kr.pe.ihoney.jco.restapi.web.support.view.PageStatus;

import org.springframework.data.domain.Page;

public interface GroupService extends GenericDomainService<Group>{

    Page<Group> getGroups(GroupCondition condition, PageStatus pageStatus);

}
