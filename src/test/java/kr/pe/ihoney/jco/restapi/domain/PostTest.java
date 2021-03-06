package kr.pe.ihoney.jco.restapi.domain;

import static org.junit.Assert.*;
import kr.pe.ihoney.jco.restapi.domain.type.CommunityType;

import org.junit.Before;
import org.junit.Test;

public class PostTest {

    private User user;
    private Community community;
    private Member member;

    @Before
    public void setUp() {
        user = new User("tester", "test@test.email");
        community = new Community("Test Team", CommunityType.PUBLIC, user);
        member = new Member("Tester", community, user);
    }

    @Test(expected = IllegalArgumentException.class)
    public void 제목이_null인_경우() {
        @SuppressWarnings("unused")
        Post post = new Post(community, null, "article", member);
        fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void 본문이_null인_경우() {
        @SuppressWarnings("unused")
        Post post = new Post(community, "title", null, member);
        fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void 작성자가_null인_경우() {
        @SuppressWarnings("unused")
        Post post = new Post(community, "title", "article", null);
        fail();
    }

}