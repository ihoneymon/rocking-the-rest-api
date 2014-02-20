package kr.pe.ihoney.jco.restapi.web.controller;

import javax.validation.Valid;

import kr.pe.ihoney.jco.restapi.common.pagination.Pagination;
import kr.pe.ihoney.jco.restapi.common.pagination.Paginations;
import kr.pe.ihoney.jco.restapi.domain.Community;
import kr.pe.ihoney.jco.restapi.domain.User;
import kr.pe.ihoney.jco.restapi.service.UserManagementService;
import kr.pe.ihoney.jco.restapi.service.condition.UserCondition;
import kr.pe.ihoney.jco.restapi.web.dto.UsersDto;
import kr.pe.ihoney.jco.restapi.web.form.UserForm;
import kr.pe.ihoney.jco.restapi.web.support.view.PageStatus;
import lombok.extern.slf4j.Slf4j;

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
 * 사용자 관리
 * 
 * @author ihoneymon
 * 
 */
@Slf4j
@RestController
@RequestMapping("/users")
public class UserManagementController {

    @Autowired
    private UserManagementService userMangementService;

    /**
     * 사용자목록 toJson
     * @param condition
     * @param pageStatus
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Pagination<User>> getUsers(@RequestBody UserCondition condition, PageStatus pageStatus) {
        Page<User> page = userMangementService.getUsers(condition, pageStatus);
        return new ResponseEntity<Pagination<User>>(Paginations.pagination(page.getContent(), pageStatus),
                HttpStatus.OK);
    }
    
    /**
     * 사용자목록 toXml
     * @param condition
     * @param pageStatus
     * @return
     */
    @RequestMapping(method=RequestMethod.GET, produces="application/xml")
    public ResponseEntity<UsersDto> getXmlUsers(@RequestBody UserCondition condition, PageStatus pageStatus) {
        Page<User> page = userMangementService.getUsers(condition, pageStatus);
        return new ResponseEntity<UsersDto>(new UsersDto(page.getContent()), HttpStatus.OK);
    }

    /**
     * 사용자 저장
     * @param form
     * @param bindingResult
     * @return
     */
    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<User> save(@Valid @RequestBody UserForm form, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            log.error(">> Error: {}", bindingResult.getAllErrors());
            return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
        }
        log.debug(">> Save user: {}", form);
        User saveUser = userMangementService.save(form.createUser());
        return new ResponseEntity<User>(saveUser, HttpStatus.CREATED);
    }
    
    /**
     * 사용자 상세정보
     * @param user
     * @return
     */
    @RequestMapping(value="/{user}", method=RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable User user) {
        return new ResponseEntity<User>(userMangementService.findByEmail(user.getEmail()), HttpStatus.OK);
    }
    
    /**
     * 사용자 정보변경
     * @param form
     * @param bindingResult
     * @param user
     * @return
     */
    @RequestMapping(value="/{user}", method=RequestMethod.PUT)
    public ResponseEntity<User> modifyUser(@Valid @RequestBody UserForm form, BindingResult bindingResult, @PathVariable User user) {
        if(bindingResult.hasErrors()){
            return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
        }
        
        return new ResponseEntity<User>(userMangementService.save(form.bind(user)),HttpStatus.OK);
    }
    
    /**
     * 사용자 삭제
     * @param user
     * @return
     */
    @RequestMapping(value="/{user}", method=RequestMethod.DELETE)
    public ResponseEntity<Object> deleteUser(@PathVariable User user) {
        log.debug(">> User: {}", user);
        try {
            userMangementService.delete(user);
        } catch(Exception e) {
            log.error(">> Occur exception: {}", e.getMessage());
        }
        return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
    }
    
    @RequestMapping(value="/{user}/communities", method=RequestMethod.GET)
    public ResponseEntity getCommunitiesOfUser(@PathVariable User user, PageStatus pageStatus) {
        //TODO
        return new ResponseEntity(HttpStatus.OK);
    }
    
    @RequestMapping(value="/{user}/communities", method=RequestMethod.GET, produces="application/xml")
    public ResponseEntity getCommunitiesOfUserToXml(@PathVariable User user, PageStatus pageStatus) {
        //TODO
        return new ResponseEntity(HttpStatus.OK);
    }
    
    @RequestMapping(value="/{user}/communities/{community}/posts", method=RequestMethod.GET)
    public ResponseEntity getPostsOfCommunity(@PathVariable User user, @PathVariable Community community, PageStatus pageStatus) {
        //TODO
        return new ResponseEntity(HttpStatus.OK);
    }
    
    @RequestMapping(value="/{user}/communities/{community}/posts", method=RequestMethod.GET, produces="application/xml")
    public ResponseEntity getPostsOfCommunityToXml(@PathVariable User user, @PathVariable Community community, PageStatus pageStatus) {
        //TODO
        return new ResponseEntity(HttpStatus.OK);
    }
}
