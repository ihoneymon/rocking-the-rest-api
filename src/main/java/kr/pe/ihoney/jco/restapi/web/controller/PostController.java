package kr.pe.ihoney.jco.restapi.web.controller;

import javax.validation.Valid;

import kr.pe.ihoney.jco.restapi.common.exception.RestApiException;
import kr.pe.ihoney.jco.restapi.common.pagination.Paginations;
import kr.pe.ihoney.jco.restapi.domain.Community;
import kr.pe.ihoney.jco.restapi.domain.Member;
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
@RequestMapping
@SuppressWarnings({ "rawtypes", "unchecked" })
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private MessageSourceAccessor messageSourceAccessor;

    @RequestMapping(value = "/communities/{community}/posts", method = RequestMethod.GET)
    public ResponseEntity getPostsOfGroup(@PathVariable Community community,
            PostCondition condition, PageStatus pageStatus) {
        Page<Post> page = postService.getPostsOfGroup(community, condition,
                pageStatus);
        return new ResponseEntity(Paginations.pagination(page.getContent(),
                pageStatus), HttpStatus.OK);
    }

    @RequestMapping(value = "/communities/{community}/posts", method = RequestMethod.POST)
    public ResponseEntity savePostOfGroup(@PathVariable Community community,
            @Valid @RequestBody PostForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(postService.save(form.createPost(community)),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/communities/{community}/posts/{post}", method = RequestMethod.GET)
    public ResponseEntity getPostOfGroup(@PathVariable Community community,
            @PathVariable Post post) {
        if (!post.getCommunity().equals(community)) {
            throw new RestApiException("post.exception.not.includeGroup");
        }

        return new ResponseEntity(postService.getPost(post), HttpStatus.OK);
    }

    @RequestMapping(value = "/communities/{community}/posts/{post}", method = RequestMethod.PUT)
    public ResponseEntity modifyPost(@PathVariable Community community,
            @PathVariable Post post, @Valid @RequestBody PostForm form,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        if (!post.getCommunity().equals(community)) {
            throw new RestApiException("post.exception.not.includeGroup");
        }
        return new ResponseEntity(postService.modify(form.bind(post)),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/communities/{community}/posts/{post}", method = RequestMethod.DELETE)
    public ResponseEntity deletePost(@PathVariable Community community,
            @PathVariable Post post) {
        if (!post.getCommunity().equals(community)) {
            throw new RestApiException("post.exception.not.includeGroup");
        }
        postService.delete(post);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/communities/{community}/members/{member}/posts", method = RequestMethod.GET)
    public ResponseEntity getPostsOfMemberInGroup(@PathVariable Community community,
            @PathVariable Member member, @RequestBody PostCondition condition,
            PageStatus pageStatus) {
        if (!member.getCommunity().equals(community)) {
            throw new RestApiException("member.not.incmlude.group");
        }

        return new ResponseEntity(postService.getPostsOfMemberInGroup(community,
                member, condition, pageStatus), HttpStatus.OK);
    }

    @RequestMapping(value = "/communities/{community}/members/{member}/posts/{post}", method = RequestMethod.GET)
    public ResponseEntity getPostOfMemberInGroup(@PathVariable Community community,
            @PathVariable Member member, @PathVariable Post post) {
        if (!member.getCommunity().equals(community)) {
            throw new RestApiException("member.not.incmlude.group");
        }
        if (!post.getCreatedBy().equals(member)) {
            throw new RestApiException("post.exception.not.same.createdBy");
        }

        return new ResponseEntity(postService.getPost(post), HttpStatus.OK);
    }
}
