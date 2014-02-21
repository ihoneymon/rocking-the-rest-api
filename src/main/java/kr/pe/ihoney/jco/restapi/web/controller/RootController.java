package kr.pe.ihoney.jco.restapi.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RootController {

    @RequestMapping(value={"/", "index"}, method=RequestMethod.GET)
    public void index() {}
}
