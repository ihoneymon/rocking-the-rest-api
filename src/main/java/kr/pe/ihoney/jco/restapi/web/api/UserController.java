package kr.pe.ihoney.jco.restapi.web.api;

import kr.pe.ihoney.jco.restapi.domain.User;
import kr.pe.ihoney.jco.restapi.service.UserService;
import kr.pe.ihoney.jco.restapi.web.api.form.UserForm;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("/users")
public class UserController {
    @Autowired
    private UserService userService;
    
    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<Page<User>> getUsers() {
        Page<User> page = userService.findAll(null);
        return new ResponseEntity<Page<User>>(page, HttpStatus.OK);
    }
    
    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<User> save(@RequestBody UserForm form, BindingResult result) {
        User saveUser = userService.save(form.createUser());
        return new ResponseEntity<User>(saveUser, HttpStatus.OK);
    }    
}
