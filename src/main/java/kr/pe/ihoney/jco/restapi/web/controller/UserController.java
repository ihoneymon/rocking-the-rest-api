package kr.pe.ihoney.jco.restapi.web.controller;

import java.io.Serializable;

import javax.validation.Valid;

import kr.pe.ihoney.jco.restapi.common.pagination.Pagination;
import kr.pe.ihoney.jco.restapi.common.pagination.Paginations;
import kr.pe.ihoney.jco.restapi.domain.User;
import kr.pe.ihoney.jco.restapi.service.GroupService;
import kr.pe.ihoney.jco.restapi.service.UserService;
import kr.pe.ihoney.jco.restapi.service.condition.UserCondition;
import kr.pe.ihoney.jco.restapi.web.form.UserForm;
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
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private MessageSourceAccessor messageSourceAccessor;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<Pagination<User>> getusers(
            @RequestBody UserCondition condition, PageStatus pageStatus) {
        Page<User> page = userService.getUsers(condition, pageStatus);
        return new ResponseEntity<Pagination<User>>(Paginations.pagination(
                page.getContent(), pageStatus), HttpStatus.OK);
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<Serializable> saveUser(
            @Valid @RequestBody UserForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error(">> Has errors: {}", bindingResult);
        }
        try {
            return new ResponseEntity<Serializable>(userService.save(form
                    .createUser()), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<Serializable>(
                    messageSourceAccessor.getMessage(e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/users/{user}", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable User user) {
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/users/{user}", method = RequestMethod.PUT)
    public ResponseEntity<Serializable> modifyUser(
            @PathVariable User user, @Valid @RequestBody UserForm form,
            BindingResult bindingResult) {
        return new ResponseEntity<Serializable>(userService.save(form
                .bind(user)), HttpStatus.OK);
    }

    @RequestMapping(value = "/users/{user}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteUser(@PathVariable User user) {
        userService.delete(user);
        return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
    }

}
