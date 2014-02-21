package kr.pe.ihoney.jco.restapi.web.controller;

import javax.validation.Valid;

import kr.pe.ihoney.jco.restapi.common.pagination.Pagination;
import kr.pe.ihoney.jco.restapi.common.pagination.Paginations;
import kr.pe.ihoney.jco.restapi.domain.Group;
import kr.pe.ihoney.jco.restapi.service.GroupService;
import kr.pe.ihoney.jco.restapi.service.condition.GroupCondition;
import kr.pe.ihoney.jco.restapi.web.form.GroupForm;
import kr.pe.ihoney.jco.restapi.web.support.view.PageStatus;

import org.springframework.beans.factory.annotation.Autowired;
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
 * 그룹 컨트롤러
 * 
 * @author ihoneymon
 * 
 */
@RestController
public class GroupController {
    @Autowired
    private GroupService groupService;

    @RequestMapping(value="/groups", method = RequestMethod.GET)
    public ResponseEntity<Pagination<Group>> getGroups(
            @RequestBody GroupCondition condition, PageStatus pageStatus) {
        Page<Group> page = groupService.getGroups(condition, pageStatus);
        return new ResponseEntity<Pagination<Group>>(Paginations.pagination(
                page.getContent(), pageStatus), HttpStatus.OK);
    }
    
    @RequestMapping(value="/groups", method = RequestMethod.POST)
    public ResponseEntity<Group> saveGroup(@Valid @RequestBody GroupForm form, BindingResult bindingResult) {
        return new ResponseEntity<Group>(groupService.save(form.createGroup()), HttpStatus.CREATED);
    }
    
    @RequestMapping(value="/groups/{group}", method=RequestMethod.GET)
    public ResponseEntity<Group> getGroup(@PathVariable Group group) {
        return new ResponseEntity<Group>(group, HttpStatus.OK);
    }
    
    @RequestMapping(value="/groups/{group}", method=RequestMethod.PUT)
    public ResponseEntity<Group> modifyGroup(@PathVariable Group group, @Valid @RequestBody GroupForm form, BindingResult bindingResult) {
        return new ResponseEntity<Group>(groupService.save(form.bind(group)), HttpStatus.OK);
    }
    
    @RequestMapping(value="/groups/{group}", method=RequestMethod.DELETE)
    public ResponseEntity<Object> deleteGroup(@PathVariable Group group) {
        groupService.delete(group);
        return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
    }
}
