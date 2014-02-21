package kr.pe.ihoney.jco.restapi.web.controller;

import java.io.Serializable;

import javax.validation.Valid;

import kr.pe.ihoney.jco.restapi.common.exception.RestApiException;
import kr.pe.ihoney.jco.restapi.common.pagination.Pagination;
import kr.pe.ihoney.jco.restapi.common.pagination.Paginations;
import kr.pe.ihoney.jco.restapi.domain.Group;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private MessageSourceAccessor messageSourceAccessor;

    @RequestMapping(value = "/groups/{group}/members", method = RequestMethod.GET)
    public ResponseEntity<Pagination<Member>> getMembersOfGroup(
            @PathVariable Group group, @RequestBody MemberCondition condition,
            PageStatus pageStatus) {
        Page<Member> page = memberService.getMembersOfGroup(group, condition,
                pageStatus);
        return new ResponseEntity<Pagination<Member>>(Paginations.pagination(
                page.getContent(), pageStatus), HttpStatus.OK);
    }

    @RequestMapping(value = "/groups/{group}/members", method = RequestMethod.POST)
    public ResponseEntity<Serializable> addMember(@PathVariable Group group,
            @Valid @RequestBody MemberForm form, BindingResult bindingResult) {
        try {
            return new ResponseEntity<Serializable>(memberService.addMember(
                    form.getNickName(), group, form.getUser()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Serializable>(
                    messageSourceAccessor.getMessage(e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/groups/{group}/member/{member}/change-nick", method = RequestMethod.PUT)
    public ResponseEntity<Member> modifyMember(@PathVariable Group group,
            @PathVariable Member member,
            @RequestParam(required = true) String nickName) {
        if (!member.getGroup().equals(group)) {
            throw new RestApiException("member.exception.not.include.group");
        }
        return new ResponseEntity<Member>(memberService.modify(member
                .changNickName(nickName)), HttpStatus.OK);
    }
}
