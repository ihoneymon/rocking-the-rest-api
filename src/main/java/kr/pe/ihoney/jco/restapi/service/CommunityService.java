package kr.pe.ihoney.jco.restapi.service;

import kr.pe.ihoney.jco.restapi.common.exception.RestApiException;
import kr.pe.ihoney.jco.restapi.domain.Community;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Community Service
 * @author ihoneymon
 *
 */
public interface CommunityService {
    Community save(Community community) throws RestApiException;
    void delete(Community community) throws RestApiException;
    Page<Community> findAll(Pageable pageable);
    Community findByName(String name);
}
