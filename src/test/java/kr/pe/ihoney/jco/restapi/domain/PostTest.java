package kr.pe.ihoney.jco.restapi.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PostTest {

    private User user;
    
    @Before
    public void setUp() {
        user = new User("tester", "test@test.email", "password");
    }

    @Test(expected=IllegalArgumentException.class)
    public void 제목이_null인_경우() {
        @SuppressWarnings("unused")
        Post post = new Post(null, "article", user);
        fail();
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void 본문이_null인_경우() {
        @SuppressWarnings("unused")
        Post post = new Post("title", null, user);
        fail();
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void 생성자가_null인_경우() {
        @SuppressWarnings("unused")
        Post post = new Post("title", "article", null);
        fail();
    }

}
