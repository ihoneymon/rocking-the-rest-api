package kr.pe.ihoney.jco.restapi.web.controller;

import java.io.Serializable;

import javax.validation.Valid;

import kr.pe.ihoney.jco.restapi.common.exception.RestApiException;
import kr.pe.ihoney.jco.restapi.common.pagination.Pagination;
import kr.pe.ihoney.jco.restapi.common.pagination.Paginations;
import kr.pe.ihoney.jco.restapi.domain.Group;
import kr.pe.ihoney.jco.restapi.domain.Post;
import kr.pe.ihoney.jco.restapi.service.PostService;
import kr.pe.ihoney.jco.restapi.service.condition.PostCondition;
import kr.pe.ihoney.jco.restapi.web.form.PostForm;
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
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private MessageSourceAccessor messageSourceAccessor;

    @RequestMapping(value = "/groups/{group}/posts", method = RequestMethod.GET)
    public ResponseEntity<Pagination<Post>> getPostsOfGroup(@PathVariable Group group,
            @RequestBody PostCondition condition, PageStatus pageStatus) {
        Page<Post> page = postService.getPostsOfGroup(group, condition,
                pageStatus);
        return new ResponseEntity<Pagination<Post>>(Paginations.pagination(page.getContent(),
                pageStatus), HttpStatus.OK);
    }

    @RequestMapping(value = "/groups/{group}/posts", method = RequestMethod.POST)
    public ResponseEntity<Serializable> savePost(@PathVariable Group group,
            @Valid @RequestBody PostForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // TODO
            return new ResponseEntity<Serializable>(HttpStatus.BAD_REQUEST);
        }
        try {
            return new ResponseEntity<Serializable>(
                    postService.save(new Post(form.getTitle(), form
                            .getArticle(), group, form.getWriter())),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<Serializable>(messageSourceAccessor.getMessage(e
                    .getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @RequestMapping(value="/groups/{group}/posts/{post}", method=RequestMethod.GET)
    public ResponseEntity<Post> getPostOfGroup(@PathVariable Group group, @PathVariable Post post) {
        validGroupOfPost(group, post);
        return new ResponseEntity<Post>(post, HttpStatus.OK);
    }
    
    @RequestMapping(value="/group/{group}/posts/{post}", method=RequestMethod.PUT)
    public ResponseEntity<Serializable> modifyPost(@PathVariable Group group, @PathVariable Post post, @Valid @RequestBody PostForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // TODO
            return new ResponseEntity<Serializable>(HttpStatus.BAD_REQUEST);
        }
        validGroupOfPost(group, post);
        if(!post.getCreatedBy().equals(form.getWriter())) {
            throw new RestApiException("post.exception.modify.only.creator");
        }
        return new ResponseEntity<Serializable>(postService.modify(form.bind(post)), HttpStatus.OK);
    }
    
    @RequestMapping(value="/group/{group}/posts/{post}", method=RequestMethod.DELETE)
    public ResponseEntity<Object> deletePost(@PathVariable Group group, @PathVariable Post post, @RequestBody PostForm form) {
        validGroupOfPost(group, post);
        if(!post.getCreatedBy().equals(form.getWriter())) {
            throw new RestApiException("post.exception.not.creator");
        }
        postService.delete(post);
        return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
    }

    private void validGroupOfPost(Group group, Post post) {
        if(!post.getGroup().equals(group)) {
            throw new RestApiException("post.exception.not.include.group");
        }
    }
}
