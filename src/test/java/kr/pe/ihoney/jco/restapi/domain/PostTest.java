package kr.pe.ihoney.jco.restapi.domain;

import static org.junit.Assert.*;
import kr.pe.ihoney.jco.restapi.domain.type.GroupType;

import org.junit.Before;
import org.junit.Test;

public class PostTest {

    private User user;
    private Group group;
    private Member member;

    @Before
    public void setUp() {
        user = new User("tester", "test@test.email");
        group = new Group("Test Team", GroupType.PUBLIC, user);
        member = new Member("Tester", group, user);
    }

    @Test(expected = IllegalArgumentException.class)
    public void 제목이_null인_경우() {
        @SuppressWarnings("unused")
        Post post = new Post(group, null, "article", member);
        fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void 본문이_null인_경우() {
        @SuppressWarnings("unused")
        Post post = new Post(group, "title", null, member);
        fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void 작성자가_null인_경우() {
        @SuppressWarnings("unused")
        Post post = new Post(group, "title", "article", null);
        fail();
    }

}