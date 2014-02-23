package kr.pe.ihoney.jco.restapi.web.controller;

import javax.validation.Valid;

import kr.pe.ihoney.jco.restapi.common.pagination.Paginations;
import kr.pe.ihoney.jco.restapi.domain.Community;
import kr.pe.ihoney.jco.restapi.service.CommunityService;
import kr.pe.ihoney.jco.restapi.service.condition.CommunityCondition;
import kr.pe.ihoney.jco.restapi.web.form.CommunityForm;
import kr.pe.ihoney.jco.restapi.web.form.CommunityNotice;
import kr.pe.ihoney.jco.restapi.web.support.view.PageStatus;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@SuppressWarnings({ "rawtypes", "unchecked" })
public class CommunityController {
    @Autowired
    private CommunityService communityService;
    @Autowired
    private MessageSourceAccessor messageSourceAccessor;

    @RequestMapping(value = "/communities", method = RequestMethod.GET)
    public ResponseEntity getCommunities(CommunityCondition condition,
            PageStatus pageStatus) {
        Page<Community> page = communityService.getCommunities(condition, pageStatus);
        return new ResponseEntity(Paginations.pagination(page.getContent(),
                pageStatus), HttpStatus.OK);
    }

    @RequestMapping(value = "/communities", method = RequestMethod.POST)
    public ResponseEntity saveCommunity(@Valid @RequestBody CommunityForm form,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        log.debug(">> GroupForm: {}", form);
        try {
            return new ResponseEntity(communityService.save(form.createGroup()),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(messageSourceAccessor.getMessage(e
                    .getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/communities/{community}", method = RequestMethod.GET)
    public ResponseEntity getCommunity(@PathVariable Community community) {
        return new ResponseEntity(communityService.getCommunity(community), HttpStatus.OK);
    }

    @RequestMapping(value = "/communities/{community}", method = RequestMethod.PUT)
    public ResponseEntity modifyCommunity(@PathVariable Community community,
            @Valid @RequestBody CommunityForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(communityService.modify(form.bind(community)),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/communities/{community}", method = RequestMethod.DELETE)
    public ResponseEntity deleteCommunity(@PathVariable Community community) {
        try {
            communityService.delete(community);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity(messageSourceAccessor.getMessage(e
                    .getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/communities/send-notice", method = RequestMethod.POST)
    public ResponseEntity sendNotice(@RequestBody CommunityNotice notice) {
        // TODO
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/communities/{community}/send-notice", method = RequestMethod.POST)
    public ResponseEntity sendNotice(@PathVariable Community community,
            @RequestBody CommunityNotice notice) {
        // TODO
        return new ResponseEntity(HttpStatus.OK);
    }
}
