package kr.pe.ihoney.jco.restapi.web.api;

import kr.pe.ihoney.jco.restapi.domain.Community;
import kr.pe.ihoney.jco.restapi.service.CommunityService;
import kr.pe.ihoney.jco.restapi.web.api.form.CommunityForm;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 * User: ihoneymon
 * Date: 14. 2. 2
 * Time: 오후 12:10
 */
@Slf4j
@RestController
@RequestMapping("/communities")
public class CommunityController {
    @Autowired
    private MessageSourceAccessor messageSourceAccessor;
    @Autowired
    private CommunityService communityService;
    
    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<Page<Community>> getCommunities(Pageable pageable) {
        Page<Community> page = communityService.findAll(pageable);
        return new ResponseEntity<Page<Community>>(page, HttpStatus.OK);
    }
    
    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity save(@RequestBody CommunityForm form, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            
        }
        if(validateExistCommunity(form.getName())) {
            return new ResponseEntity(messageSourceAccessor.getMessage("community.exception.exist-same-name"), HttpStatus.BAD_REQUEST);
        }
        Community community = communityService.save(form.createCommunity());
        return new ResponseEntity(community, HttpStatus.CREATED);
    }

    private boolean validateExistCommunity(String communityName) {
        Community existCommunity = communityService.findByName(communityName);
        return existCommunity != null;
    }
}
