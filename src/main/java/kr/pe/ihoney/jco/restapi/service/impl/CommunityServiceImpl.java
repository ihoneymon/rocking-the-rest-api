package kr.pe.ihoney.jco.restapi.service.impl;

import kr.pe.ihoney.jco.restapi.common.RestApiException;
import kr.pe.ihoney.jco.restapi.domain.Community;
import kr.pe.ihoney.jco.restapi.repository.CommunityRepository;
import kr.pe.ihoney.jco.restapi.service.CommunityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: ihoneymon
 * Date: 14. 2. 2
 * Time: 오후 12:52
 */
@Slf4j
@Service
public class CommunityServiceImpl implements CommunityService {
    @Autowired
    private CommunityRepository communityRepository;

    @Override
    public Community save(Community community) throws RestApiException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void delete(Community community) throws RestApiException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Page<Community> findAll(Pageable pageable) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
