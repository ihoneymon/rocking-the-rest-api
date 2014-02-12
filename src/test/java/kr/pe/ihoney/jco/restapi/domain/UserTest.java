package kr.pe.ihoney.jco.restapi.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.springframework.util.StringUtils;

public class UserTest {

    @Test(expected = IllegalArgumentException.class)
    public void 사용자명을_null로_생성하는경우() {
        @SuppressWarnings("unused")
        User user = new User(null, "test@test.email", "password");
        fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void 사용자이메일을_null로_생성하는경우() {
        @SuppressWarnings("unused")
        User user = new User("tester", null, "password");
        fail();
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void 사용자_비밀번호를_null로_생성하는경우_예외발생() {
        @SuppressWarnings("unused")
        User user = new User("tester", "test@test.email", null);
        fail();
    }
    
    @Test
    public void 사용자생성() {
        User user = new User("tester", "test@test.email", "password");
        assertThat(user.getName(), is("tester"));
        assertThat(user.getEmail(), is("test@test.email"));
        assertThat(user.getPassword(), is("password"));
    }
    
    @Test
    public void test() {
        String key= "";
        String[] determitedStrings = StringUtils.delimitedListToStringArray(key, "$");
    }
}
