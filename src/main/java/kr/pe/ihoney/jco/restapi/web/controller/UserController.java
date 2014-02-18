package kr.pe.ihoney.jco.restapi.web.controller;

import javax.validation.Valid;

import kr.pe.ihoney.jco.restapi.common.pagination.Pagination;
import kr.pe.ihoney.jco.restapi.common.pagination.Paginations;
import kr.pe.ihoney.jco.restapi.domain.User;
import kr.pe.ihoney.jco.restapi.service.UserService;
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

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    
    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<Pagination<User>> getUsers(PageStatus pageStatus) {
        Page<User> page = userService.users(pageStatus);
        return new ResponseEntity<Pagination<User>>(Paginations.pagination(page.getContent(), pageStatus), HttpStatus.OK);
    }
    
    @RequestMapping(method=RequestMethod.GET, produces="application/xml")
    public ResponseEntity<UsersDto> getXmlUsers(PageStatus pageStatus) {
        Page<User> page = userService.users(pageStatus);
        return new ResponseEntity<UsersDto>(new UsersDto(page.getContent()), HttpStatus.OK);
    }
    
    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<User> save(@Valid @RequestBody UserForm form, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            log.error(">> Error: {}", bindingResult.getAllErrors());
            return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
        }
        log.debug(">> Save user: {}", form);
        User saveUser = userService.save(form.createUser());
        return new ResponseEntity<User>(saveUser, HttpStatus.CREATED);
    }

    @RequestMapping(value="/{user}", method=RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable("user") User user){
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
    
    @RequestMapping(value="/{user}", method=RequestMethod.PUT)
    public ResponseEntity<User> modifyUser(@Valid @RequestBody UserForm form, BindingResult bindingResult, @PathVariable User user) {
        if(bindingResult.hasErrors()){
            return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
        }
        
        return new ResponseEntity<User>(userService.save(form.bind(user)),HttpStatus.OK);
    }
    
    @RequestMapping(value="/{user}", method=RequestMethod.DELETE)
    public ResponseEntity<Object> deleteUser(@PathVariable User user) {
        log.debug(">> User: {}", user);
        try {
            userService.delete(user);
        } catch(Exception e) {
            log.error(">> Occur exception: {}", e.getMessage());
        }
        return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
    }
    
}