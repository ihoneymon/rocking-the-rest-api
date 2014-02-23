package kr.pe.ihoney.jco.restapi.web.controller;

import javax.validation.Valid;

import kr.pe.ihoney.jco.restapi.common.exception.RestApiException;
import kr.pe.ihoney.jco.restapi.common.pagination.Paginations;
import kr.pe.ihoney.jco.restapi.domain.Community;
import kr.pe.ihoney.jco.restapi.domain.Member;
import kr.pe.ihoney.jco.restapi.service.MemberService;
import kr.pe.ihoney.jco.restapi.service.condition.MemberCondition;
import kr.pe.ihoney.jco.restapi.web.form.MemberForm;
import kr.pe.ihoney.jco.restapi.web.support.view.PageStatus;

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

@RestController
@SuppressWarnings({ "rawtypes", "unchecked" })
public class MemberController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MessageSourceAccessor messageSourceAccessor;

    @RequestMapping(value = "/groups/{group}/members", method = RequestMethod.GET)
    public ResponseEntity getMembers(@PathVariable Community community,
            MemberCondition condition, PageStatus pageStatus) {
        Page<Member> page = memberService.getMembers(community, condition,
                pageStatus);
        return new ResponseEntity(Paginations.pagination(page.getContent(),
                pageStatus), HttpStatus.OK);
    }

    @RequestMapping(value = "/groups/{group}/members", method = RequestMethod.POST)
    public ResponseEntity saveMember(@PathVariable Community community,
            @Valid @RequestBody MemberForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(memberService.save(form.createMember(community)),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/groups/{group}/members/{member}", method = RequestMethod.GET)
    public ResponseEntity getMember(@PathVariable Community community,
            @PathVariable Member member) {
        if (!member.getCommunity().equals(community)) {
            throw new RestApiException("member.not.incmlude.group");
        }

        return new ResponseEntity(memberService.getMember(member),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/groups/{group}/members/{member}", method = RequestMethod.PUT)
    public ResponseEntity modifyMember(@PathVariable Community community,
            @PathVariable Member member, @Valid @RequestBody MemberForm form,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        if (!member.getCommunity().equals(community)) {
            throw new RestApiException("member.not.incmlude.group");
        }

        return new ResponseEntity(memberService.modify(form.bind(member)),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/groups/{group}/members/{member}", method = RequestMethod.DELETE)
    public ResponseEntity deleteMember(@PathVariable Community community,
            @PathVariable Member member) {
        if (!member.getCommunity().equals(community)) {
            throw new RestApiException("member.not.incmlude.group");
        }

        memberService.delete(member);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
