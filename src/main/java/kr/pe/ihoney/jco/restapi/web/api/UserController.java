package kr.pe.ihoney.jco.restapi.web.api;

import kr.pe.ihoney.jco.restapi.service.UserService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("/users")
public class UserController {
    @Autowired
    private UserService userService;
    
    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity getList() {        
        return new ResponseEntity(HttpStatus.OK);
    }
}
