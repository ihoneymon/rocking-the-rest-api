package kr.pe.ihoney.jco.restapi.web.controller;

import javax.validation.Valid;

import kr.pe.ihoney.jco.restapi.common.pagination.Pagination;
import kr.pe.ihoney.jco.restapi.common.pagination.Paginations;
import kr.pe.ihoney.jco.restapi.domain.Community;
import kr.pe.ihoney.jco.restapi.domain.Member;
import kr.pe.ihoney.jco.restapi.service.CommunityService;
import kr.pe.ihoney.jco.restapi.web.dto.CommunityDto;
import kr.pe.ihoney.jco.restapi.web.form.CommunityForm;
import kr.pe.ihoney.jco.restapi.web.form.MemberForm;
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
    public ResponseEntity<Pagination<Community>> getCommunities(PageStatus pageStatus) {
        Page<Community> page = communityService.getCommunities(pageStatus);
        return new ResponseEntity<Pagination<Community>>(Paginations.pagination(page), HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.GET, produces="application/xml")
    public ResponseEntity<CommunityDto> getCommunityXml(PageStatus pageStatus) {
        Page<Community> page = communityService.getCommunities(pageStatus);
        return new ResponseEntity<CommunityDto>(new CommunityDto(page.getContent()), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> save(@RequestBody CommunityForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error(">> Error: {}", bindingResult.getFieldError());
        }
        Community community = communityService.save(form.createCommunity());
        return new ResponseEntity<Object>(community, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{community}", method = RequestMethod.GET)
    public ResponseEntity<Community> getPost(@PathVariable Community community) {
        return new ResponseEntity<Community>(community, HttpStatus.OK);
    }

    @RequestMapping(value = "/{community}", method = RequestMethod.PUT)
    public ResponseEntity<Community> modify(@PathVariable Community community,
            @Valid @RequestBody CommunityForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error(">> Has Errors: {}", bindingResult.getAllErrors());
        }
        Community modifyCommunity = communityService.save(form.bind(community));
        return new ResponseEntity<Community>(modifyCommunity, HttpStatus.OK);
    }
    
    @RequestMapping(value="/{community}", method=RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable Community community) {
        communityService.delete(community);
        return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
    }
    
    @RequestMapping(value="/{community}/members", method=RequestMethod.GET)
    public ResponseEntity<Pagination<Member>> getMembersOfCommunity(@PathVariable Community community, PageStatus pageStatus) {
        Page<Member> page = communityService.findMembersByCommunity(community, pageStatus);
        return new ResponseEntity<Pagination<Member>>(Paginations.pagination(page), HttpStatus.OK);
    }
    
    /**
     * 회원등록
     * @param community
     * @param form
     * @param bindingResult
     * @return
     */
    @RequestMapping(value="/{community}/register-member", method=RequestMethod.POST)
    public ResponseEntity<Member> registerMember(@PathVariable Community community, @Valid @RequestBody MemberForm form, BindingResult bindingResult) {
        Member member = communityService.registerMember(form.getNickName(), community, form.getUser());
        return new ResponseEntity<Member>(member, HttpStatus.CREATED);
    }
    
    /**
     * 회원등록
     * 
     * @param form
     * @param bindingResult
     * @return
     */
    @RequestMapping(value="/register-member", method=RequestMethod.POST)
    public ResponseEntity<Member> registerMember(@Valid @RequestBody MemberForm form, BindingResult bindingResult) {
        Member member = communityService.registerMember(form.getNickName(), form.getCommunity(), form.getUser());
        return new ResponseEntity<Member>(member, HttpStatus.CREATED);
    }
}
