package kr.pe.ihoney.jco.restapi.service;

import kr.pe.ihoney.jco.restapi.common.exception.RestApiException;
import kr.pe.ihoney.jco.restapi.domain.Community;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created with IntelliJ IDEA.
 * User: ihoneymon
 * Date: 14. 2. 2
 * Time: 오후 12:09
 */
public interface CommunityService {
    Community save(Community community) throws RestApiException;
    void delete(Community community) throws RestApiException;
    Page<Community> findAll(Pageable pageable);
}
