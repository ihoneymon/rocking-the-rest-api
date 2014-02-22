package kr.pe.ihoney.jco.restapi.web.controller;

import javax.validation.Valid;

import kr.pe.ihoney.jco.restapi.common.pagination.Paginations;
import kr.pe.ihoney.jco.restapi.domain.User;
import kr.pe.ihoney.jco.restapi.service.UserService;
import kr.pe.ihoney.jco.restapi.service.condition.UserCondition;
import kr.pe.ihoney.jco.restapi.web.form.UserForm;
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
@SuppressWarnings({ "unchecked", "rawtypes" })
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private MessageSourceAccessor messageSourceAccessor;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity getUsers(@RequestBody UserCondition condition,
            PageStatus pageStatus) {
        Page<User> page = userService.getUsers(condition, pageStatus);
        return new ResponseEntity(Paginations.pagination(page.getContent(),
                pageStatus), HttpStatus.OK);
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity saveUser(@Valid @RequestBody UserForm form,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        try {
            return new ResponseEntity(userService.save(form.createUser()),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(messageSourceAccessor.getMessage(e
                    .getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/users/{user}", method = RequestMethod.GET)
    public ResponseEntity getUser(@PathVariable User user) {
        return new ResponseEntity(userService.getUser(user), HttpStatus.OK);
    }

    @RequestMapping(value = "/users/{user}", method = RequestMethod.PUT)
    public ResponseEntity modifyUser(@PathVariable User user,
            @Valid @RequestBody UserForm form, BindingResult bindingResult) {
        return new ResponseEntity(userService.modify(form.bind(user)),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/users/{user}", method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@PathVariable User user) {
        try {
            userService.delete(user);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity(messageSourceAccessor.getMessage(e
                    .getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
