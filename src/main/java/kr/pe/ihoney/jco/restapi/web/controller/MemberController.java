package kr.pe.ihoney.jco.restapi.web.controller;

import java.util.Locale;

import javax.validation.Valid;

import kr.pe.ihoney.jco.restapi.common.pagination.Pagination;
import kr.pe.ihoney.jco.restapi.common.pagination.Paginations;
import kr.pe.ihoney.jco.restapi.domain.Community;
import kr.pe.ihoney.jco.restapi.domain.Member;
import kr.pe.ihoney.jco.restapi.service.MemberService;
import kr.pe.ihoney.jco.restapi.service.MessageSourceService;
import kr.pe.ihoney.jco.restapi.service.condition.MemberCondition;
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

@Slf4j
@RestController
public class MemberController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private MessageSourceAccessor messageSourceAccessor;

    @RequestMapping(value="/communities/{community}/members", method=RequestMethod.GET)
    public ResponseEntity<Pagination<Member>> getMembersOfCommunity(@PathVariable Community community, @RequestBody MemberCondition condition, PageStatus pageStatus) {
        Page<Member> page = memberService.findMembersByCommunity(community, condition, pageStatus);
        return new ResponseEntity<Pagination<Member>>(Paginations.pagination(page), HttpStatus.OK);
    }
    
    /**
     * 회원등록
     * @param community
     * @param form
     * @param bindingResult
     * @return
     */
    @RequestMapping(value="/communities/{community}/members", method=RequestMethod.POST)
    public ResponseEntity<Object> registerMember(@PathVariable Community community, @Valid @RequestBody MemberForm form, BindingResult bindingResult, Locale locale) {
        if(bindingResult.hasErrors()) {
            //TODO bindingResult 처리
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        try {
            form.setCommunity(community);
            Member member = memberService.save(form.createMember());
            return new ResponseEntity<Object>(member, HttpStatus.CREATED);
        } catch(Exception e) {
            return new ResponseEntity<Object>(messageSourceAccessor.getMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    
    @RequestMapping(value="/communities/{community}/members/{member}", method=RequestMethod.GET)
    public ResponseEntity<String> getMember(@PathVariable Community community, @PathVariable Member member) {
        if(!member.getCommunity().equals(community)) {
            return new ResponseEntity<String>("member.exception.dont.register.in.community", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>(HttpStatus.OK);
    }
    
    @RequestMapping(value="/communities/{community}/members/{member}", method=RequestMethod.PUT)
    public ResponseEntity<Object> modify(@PathVariable Community community, @PathVariable Member member, @Valid @RequestBody MemberForm form, BindingResult bindingResult) {
        if(!member.getCommunity().equals(community)) {
            return new ResponseEntity<Object>("member.exception.dont.register.in.community", HttpStatus.BAD_REQUEST);
        }
        if(bindingResult.hasErrors()) {
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        }        
        return new ResponseEntity<Object>(memberService.save(form.bind(member)), HttpStatus.OK);
    }
    
    @RequestMapping(value="/communities/{community}/members/{member}", method=RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable Community community, @PathVariable Member member) {
        if(!member.getCommunity().equals(community)) {
            return new ResponseEntity<Object>("member.exception.dont.register.in.community", HttpStatus.BAD_REQUEST);
        }
        memberService.delete(member);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
