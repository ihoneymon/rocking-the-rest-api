package kr.pe.ihoney.jco.restapi.web.controller;

import javax.validation.Valid;

import kr.pe.ihoney.jco.restapi.common.pagination.Paginations;
import kr.pe.ihoney.jco.restapi.domain.Group;
import kr.pe.ihoney.jco.restapi.service.GroupService;
import kr.pe.ihoney.jco.restapi.service.condition.GroupCondition;
import kr.pe.ihoney.jco.restapi.web.form.GroupForm;
import kr.pe.ihoney.jco.restapi.web.form.GroupNotice;
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
public class GroupController {
    @Autowired
    private GroupService groupService;
    @Autowired
    private MessageSourceAccessor messageSourceAccessor;

    @RequestMapping(value = "/groups", method = RequestMethod.GET)
    public ResponseEntity getGroups(GroupCondition condition,
            PageStatus pageStatus) {
        Page<Group> page = groupService.getGroups(condition, pageStatus);
        return new ResponseEntity(Paginations.pagination(page.getContent(),
                pageStatus), HttpStatus.OK);
    }

    @RequestMapping(value = "/groups", method = RequestMethod.POST)
    public ResponseEntity saveGroup(@Valid @RequestBody GroupForm form,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        log.debug(">> GroupForm: {}", form);
        try {
            return new ResponseEntity(groupService.save(form.createGroup()),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(messageSourceAccessor.getMessage(e
                    .getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/groups/{group}", method = RequestMethod.GET)
    public ResponseEntity getGroup(@PathVariable Group group) {
        return new ResponseEntity(groupService.getGroup(group), HttpStatus.OK);
    }

    @RequestMapping(value = "/groups/{group}", method = RequestMethod.PUT)
    public ResponseEntity modifyGroup(@PathVariable Group group,
            @Valid @RequestBody GroupForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(groupService.modify(form.bind(group)),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/groups/{group}", method = RequestMethod.DELETE)
    public ResponseEntity deleteGroup(@PathVariable Group group) {
        try {
            groupService.delete(group);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity(messageSourceAccessor.getMessage(e
                    .getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/groups/send-notice", method = RequestMethod.POST)
    public ResponseEntity sendNotice(@RequestBody GroupNotice notice) {
        // TODO
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/groups/{group}/send-notice", method = RequestMethod.POST)
    public ResponseEntity sendNotice(@PathVariable Group group,
            @RequestBody GroupNotice notice) {
        // TODO
        return new ResponseEntity(HttpStatus.OK);
    }
}
