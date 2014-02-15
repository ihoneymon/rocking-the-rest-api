package kr.pe.ihoney.jco.restapi.web.controller;

import kr.pe.ihoney.jco.restapi.common.pagination.Pagination;
import kr.pe.ihoney.jco.restapi.common.pagination.Paginations;
import kr.pe.ihoney.jco.restapi.domain.Post;
import kr.pe.ihoney.jco.restapi.service.PostService;
import kr.pe.ihoney.jco.restapi.web.form.PostForm;
import kr.pe.ihoney.jco.restapi.web.support.view.PageStatus;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<Post> save(@RequestBody PostForm form, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            log.error(">> Errors: {}", bindingResult.getAllErrors());
        }
        Post post = postService.save(form.createPost());
        return new ResponseEntity<Post>(post, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Pagination<Post>> getPosts(PageStatus pageStatus) {
        log.debug(">> PageStatus: {}", pageStatus);
        Page<Post> page = postService.posts(pageStatus);
        return new ResponseEntity<Pagination<Post>>(Paginations.pagination(page.getContent(),
                pageStatus), HttpStatus.OK);
    }
    
    @RequestMapping(value="/{post}", method=RequestMethod.GET)
    public ResponseEntity<Post> getPost(@PathVariable Post post) {
        return new ResponseEntity<Post>(post, HttpStatus.OK);
    }
    @RequestMapping(value="/{post}", method=RequestMethod.PUT)
    public ResponseEntity<Post> modify(@PathVariable Post post, @RequestBody PostForm form) {
        Post modifiedPost = postService.save(form.bind(post));
        return new ResponseEntity<Post>(modifiedPost, HttpStatus.OK);
    }
    
    @RequestMapping(value="/{post}", method=RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable Post post) {
        postService.delete(post);
        return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
    }
}
