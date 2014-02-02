package kr.pe.ihoney.jco.restapi.web.controller;

import kr.pe.ihoney.jco.restapi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created with IntelliJ IDEA.
 * User: ihoneymon
 * Date: 14. 2. 2
 * Time: 오후 12:09
 */
@Slf4j
@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
}
