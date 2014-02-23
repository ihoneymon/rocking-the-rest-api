package kr.pe.ihoney.jco.restapi.service;

import kr.pe.ihoney.jco.restapi.domain.Community;
import kr.pe.ihoney.jco.restapi.service.condition.CommunityCondition;
import kr.pe.ihoney.jco.restapi.web.support.view.PageStatus;

import org.springframework.data.domain.Page;

public interface CommunityService extends GeneralDomainService<Community> {

    Page<Community> getCommunities(CommunityCondition condition, PageStatus pageStatus);

    Community getCommunity(Community community);

}
