package kr.pe.ihoney.jco.restapi.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;

public class UserTest {

    @Test(expected=IllegalArgumentException.class)
    public void 사용자의_이메일이_null인_경우() {
        @SuppressWarnings("unused")
        User user = new User(null, "사용자");
        fail();
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void 사용자의_이메일이_빈값인_경우() {
        @SuppressWarnings("unused")
        User user = new User("", "사용자");
        fail();
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void 사용자의_이름이_null인_경우() {
        @SuppressWarnings("unused")
        User user = new User("user@rocking.com", null);
        fail();
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void 사용자의_이름이_빈값인_경우() {
        @SuppressWarnings("unused")
        User user = new User("user@rocking.api", "");
        fail();
    }
    
    @Test
    public void 사용자의_이름을_변경() {
        String email = "user@rokcing.api";
        String name = "사용자";
        User user = new User(email, name);
        assertThat(user.getEmail(), is(email));
        assertThat(user.getName(), is(name));
    }
}