package kr.pe.ihoney.jco.restapi.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created with IntelliJ IDEA.
 * User: ihoneymon
 * Date: 14. 2. 2
 * Time: 오후 4:38
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
@Controller
public class RootController {
    @RequestMapping(value="/", method= RequestMethod.GET)
    public String index() {
        return "index";
    }
}
