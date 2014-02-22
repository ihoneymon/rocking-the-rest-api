package kr.pe.ihoney.jco.restapi.web.controller;

import javax.validation.Valid;

import kr.pe.ihoney.jco.restapi.common.exception.RestApiException;
import kr.pe.ihoney.jco.restapi.common.pagination.Paginations;
import kr.pe.ihoney.jco.restapi.domain.Group;
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

    @RequestMapping(value = "/groups/{group}/posts", method = RequestMethod.GET)
    public ResponseEntity getPostsOfGroup(@PathVariable Group group,
            PostCondition condition, PageStatus pageStatus) {
        Page<Post> page = postService.getPostsOfGroup(group, condition,
                pageStatus);
        return new ResponseEntity(Paginations.pagination(page.getContent(),
                pageStatus), HttpStatus.OK);
    }

    @RequestMapping(value = "/groups/{group}/posts", method = RequestMethod.POST)
    public ResponseEntity savePostOfGroup(@PathVariable Group group,
            @Valid @RequestBody PostForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(postService.save(form.createPost(group)),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/groups/{group}/posts/{post}", method = RequestMethod.GET)
    public ResponseEntity getPostOfGroup(@PathVariable Group group,
            @PathVariable Post post) {
        if (!post.getGroup().equals(group)) {
            throw new RestApiException("post.exception.not.includeGroup");
        }

        return new ResponseEntity(postService.getPost(post), HttpStatus.OK);
    }

    @RequestMapping(value = "/groups/{group}/posts/{post}", method = RequestMethod.PUT)
    public ResponseEntity modifyPost(@PathVariable Group group,
            @PathVariable Post post, @Valid @RequestBody PostForm form,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        if (!post.getGroup().equals(group)) {
            throw new RestApiException("post.exception.not.includeGroup");
        }
        return new ResponseEntity(postService.modify(form.bind(post)),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/groups/{group}/posts/{post}", method = RequestMethod.DELETE)
    public ResponseEntity deletePost(@PathVariable Group group,
            @PathVariable Post post) {
        if (!post.getGroup().equals(group)) {
            throw new RestApiException("post.exception.not.includeGroup");
        }
        postService.delete(post);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/groups/{group}/members/{member}/posts", method = RequestMethod.GET)
    public ResponseEntity getPostsOfMemberInGroup(@PathVariable Group group,
            @PathVariable Member member, @RequestBody PostCondition condition,
            PageStatus pageStatus) {
        if (!group.getMembers().contains(member)) {
            throw new RestApiException("member.not.incmlude.group");
        }

        return new ResponseEntity(postService.getPostsOfMemberInGroup(group,
                member, condition, pageStatus), HttpStatus.OK);
    }

    @RequestMapping(value = "/groups/{group}/members/{member}/posts/{post}", method = RequestMethod.GET)
    public ResponseEntity getPostOfMemberInGroup(@PathVariable Group group,
            @PathVariable Member member, @PathVariable Post post) {
        if (!group.getMembers().contains(member)) {
            throw new RestApiException("member.not.incmlude.group");
        }
        if (!post.getCreatedBy().equals(member)) {
            throw new RestApiException("post.exception.not.same.createdBy");
        }

        return new ResponseEntity(postService.getPost(post), HttpStatus.OK);
    }
}
