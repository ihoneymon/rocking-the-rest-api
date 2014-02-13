package kr.pe.ihoney.jco.restapi.web.api;

import kr.pe.ihoney.jco.restapi.service.PostService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author ihoneymon
 *
 */
@Slf4j
@Controller
@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private PostService postService;
}
