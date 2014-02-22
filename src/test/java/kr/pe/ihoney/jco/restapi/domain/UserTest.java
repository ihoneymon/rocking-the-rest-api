package kr.pe.ihoney.jco.restapi.domain;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class UserTest {

    @Test(expected = IllegalArgumentException.class)
    public void 사용자명을_null로_생성하는경우() {
        @SuppressWarnings("unused")
        User user = new User(null, "test@test.email");
        fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void 사용자이메일을_null로_생성하는경우() {
        @SuppressWarnings("unused")
        User user = new User("tester", null);
        fail();
    }
    
    @Test
    public void 사용자생성() {
        User user = new User("tester", "test@test.email");
        assertThat(user.getName(), isA(String.class));
        assertThat(user.getEmail(), isA(String.class));
    }    
}
