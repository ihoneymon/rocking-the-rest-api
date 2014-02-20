package kr.pe.ihoney.jco.restapi.web.controller;

import java.util.List;

import kr.pe.ihoney.jco.restapi.common.pagination.Pagination;
import kr.pe.ihoney.jco.restapi.common.pagination.Paginations;
import kr.pe.ihoney.jco.restapi.domain.Community;
import kr.pe.ihoney.jco.restapi.domain.Post;
import kr.pe.ihoney.jco.restapi.domain.User;
import kr.pe.ihoney.jco.restapi.service.UserService;
import kr.pe.ihoney.jco.restapi.web.form.UserForm;
import kr.pe.ihoney.jco.restapi.web.support.view.PageStatus;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/my")
public class UserController {
    @Autowired
    private UserService userService;
    
    @RequestMapping(value="/profile", method=RequestMethod.GET)
    public ResponseEntity<User> getUser(User user){        
        return new ResponseEntity<User>(userService.getUser(user), HttpStatus.OK);
    }
    
    @RequestMapping(value="/profile", method=RequestMethod.PUT)
    public ResponseEntity<User> modify(@RequestBody UserForm form, User user) {        
        return new ResponseEntity<User>(userService.save(form.bind(user)), HttpStatus.OK);
    }
    
    @RequestMapping(value="/comunnities", method=RequestMethod.GET)
    public ResponseEntity<Pagination<Community>> getCommunities(User user, PageStatus pageStatus) {
        List<Community> communities = userService.findCommunitiesOfUser(user);
        return new ResponseEntity<>(Paginations.pagination(communities, pageStatus), HttpStatus.OK);
    }
    
    @RequestMapping(value="/posts", method=RequestMethod.GET)
    public ResponseEntity getMyPosts(User user, PageStatus pageStatus) {
        List<Post> posts = userService.findPostsOfUser(user);
        return new ResponseEntity(HttpStatus.OK);
    }
}