package kr.pe.ihoney.jco.restapi.web.controller;

import kr.pe.ihoney.jco.restapi.common.pagination.Paginations;
import kr.pe.ihoney.jco.restapi.domain.Community;
import kr.pe.ihoney.jco.restapi.service.CommunityService;
import kr.pe.ihoney.jco.restapi.web.dto.CommunityDto;
import kr.pe.ihoney.jco.restapi.web.form.CommunityForm;
import kr.pe.ihoney.jco.restapi.web.support.view.PageStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 커뮤니티 컨트롤러
 * @author ihoneymon
 *
 */
@Slf4j
@RestController
@RequestMapping("/communities")
public class CommunityController {

    @Autowired
    private MessageSourceAccessor messageSourceAccessor;

    @Autowired
    private CommunityService communityService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getCommunities(PageStatus pageStatus) {
        Page<Community> page = communityService.getCommunities(pageStatus);
        return new ResponseEntity(Paginations.pagination(page), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, produces="application/xml")
    public ResponseEntity getCommunityXml(PageStatus pageStatus) {
        Page<Community> page = communityService.getCommunities(pageStatus);
        return new ResponseEntity(new CommunityDto(page.getContent()), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity save(@RequestBody CommunityForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error(">> Error: {}", bindingResult.getFieldError());
        }
        Community community = communityService.save(form.createCommunity());
        return new ResponseEntity(community, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{community}", method = RequestMethod.GET)
    public ResponseEntity getPost(@PathVariable Community community) {
        return new ResponseEntity(community, HttpStatus.OK);
    }

    @RequestMapping(value = "/{community}", method = RequestMethod.PUT)
    public ResponseEntity modify(@PathVariable Community community,
                                 @Valid @RequestBody CommunityForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error(">> Has Errors: {}", bindingResult.getAllErrors());
        }
        Community modifyCommunity = communityService.save(form.bind(community));
        return new ResponseEntity(modifyCommunity, HttpStatus.OK);
    }

    @RequestMapping(value="/{community}", method=RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable Community community) {
        communityService.delete(community);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}