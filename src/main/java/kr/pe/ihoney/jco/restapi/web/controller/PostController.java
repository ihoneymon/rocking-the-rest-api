package kr.pe.ihoney.jco.restapi.web.controller;

import kr.pe.ihoney.jco.restapi.common.pagination.Paginations;
import kr.pe.ihoney.jco.restapi.domain.Community;
import kr.pe.ihoney.jco.restapi.domain.Member;
import kr.pe.ihoney.jco.restapi.domain.Post;
import kr.pe.ihoney.jco.restapi.service.PostService;
import kr.pe.ihoney.jco.restapi.service.condition.PostCondition;
import kr.pe.ihoney.jco.restapi.web.form.PostForm;
import kr.pe.ihoney.jco.restapi.web.support.view.PageStatus;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 포스트 컨트롤러
 * 
 * @author ihoneymon
 * 
 */
@Slf4j
@RestController
public class PostController {
    @Autowired
    private PostService postService;

    /**
     * 회원의 포스트 목록 조회
     * @param community
     * @param member
     * @param condition
     * @param pageStatus
     * @return
     */
    @RequestMapping(value = "/communities/{community}/members/{member}/posts", method = RequestMethod.GET)
    public ResponseEntity<Object> getPosts(@PathVariable Community community, @PathVariable Member member,
            @RequestBody PostCondition condition, PageStatus pageStatus) {
        log.debug(">> PageStatus: {}", pageStatus);
        if (!member.getCommunity().equals(community)) {
            return new ResponseEntity<Object>("member.exception.dont.register.in.community", HttpStatus.BAD_REQUEST);
        }
        Page<Post> page = postService.getPostsOfMember(member, condition, pageStatus);
        return new ResponseEntity<Object>(Paginations.pagination(page.getContent(), pageStatus), HttpStatus.OK);
    }

    /**
     * 회원의 포스트 등록
     * @param community
     * @param member
     * @param form
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/communities/{community}/members/{member}/posts", method = RequestMethod.POST)
    public ResponseEntity<Object> save(@PathVariable Community community, @PathVariable Member member, @RequestBody PostForm form, BindingResult bindingResult) {
        if (!member.getCommunity().equals(community)) {
            return new ResponseEntity<Object>("member.exception.dont.register.in.community", HttpStatus.BAD_REQUEST);
        }
        if (bindingResult.hasErrors()) {
            log.error(">> Errors: {}", bindingResult.getAllErrors());
        }        
        Post post = postService.save(form.createPost());
        return new ResponseEntity<Object>(post, HttpStatus.CREATED);
    }

    /**
     * 회원의 포스트 상세조회
     * @param community
     * @param member
     * @param post
     * @return
     */
    @RequestMapping(value = "/communities/{community}/members/{member}/posts/{post}", method = RequestMethod.GET)
    public ResponseEntity<Object> getPost(@PathVariable Community community, @PathVariable Member member, @PathVariable Post post) {
        if (!member.getCommunity().equals(community)) {
            return new ResponseEntity<Object>("member.exception.dont.register.in.community", HttpStatus.BAD_REQUEST);
        }        
        if(!post.doesOpen(member)) {
            return new ResponseEntity<Object>("post.exception.is.privateType", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Object>(postService.getPost(post), HttpStatus.OK);
    }

    /**
     * 회원의 포스트 수정
     * @param community
     * @param member
     * @param post
     * @param form
     * @return
     */
    @RequestMapping(value = "/communities/{community}/members/{member}/posts/{post}", method = RequestMethod.PUT)
    public ResponseEntity<Object> modify(@PathVariable Community community, @PathVariable Member member, @PathVariable Post post, @RequestBody PostForm form) {
        if (!member.getCommunity().equals(community)) {
            return new ResponseEntity<Object>("member.exception.dont.register.in.community", HttpStatus.BAD_REQUEST);
        }        
        if(!member.equals(post.getMember())) {
            return new ResponseEntity<Object>("post.exception.is.privateType", HttpStatus.BAD_REQUEST);
        }
        Post modifiedPost = postService.save(form.bind(post));
        return new ResponseEntity<Object>(modifiedPost, HttpStatus.OK);
    }

    /**
     * 회원의 포스트 삭제
     * @param post
     * @return
     */
    @RequestMapping(value = "/{post}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable Post post) {
        postService.delete(post);
        return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
    }
}
