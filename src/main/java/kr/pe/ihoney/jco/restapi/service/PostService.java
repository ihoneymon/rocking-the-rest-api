package kr.pe.ihoney.jco.restapi.service;

import kr.pe.ihoney.jco.restapi.domain.Group;
import kr.pe.ihoney.jco.restapi.domain.Post;
import kr.pe.ihoney.jco.restapi.service.condition.PostCondition;
import kr.pe.ihoney.jco.restapi.web.support.view.PageStatus;

import org.springframework.data.domain.Page;

public interface PostService extends GenericDomainService<Post>{

    Page<Post> getPosts(PostCondition condition, PageStatus pageStatus);

    Page<Post> getPostsOfGroup(Group group, PostCondition condition,
            PageStatus pageStatus);

}
