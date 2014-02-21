package kr.pe.ihoney.jco.restapi.domain;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

public class MemberTest {

    @Test(expected=IllegalArgumentException.class)
    public void 회원의_이메일이_null인_경우() {
        @SuppressWarnings("unused")
        Member member = new Member(null, "member");
        fail();
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void 회원의_이메일이_빈값인_경우() {
        @SuppressWarnings("unused")
        Member member = new Member("", "member");
        fail();
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void 회원의_이름이_null인_경우() {
        @SuppressWarnings("unused")
        Member member = new Member("member@rocking.com", null);
        fail();
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void 회원의_이름이_빈값인_경우() {
        @SuppressWarnings("unused")
        Member member = new Member("member@rocking.api", "");
        fail();
    }
    
    @Test
    public void 회원의_이름을_변경() {
        String email = "member@rokcing.api";
        String name = "member";
        Member member = new Member(email, name);
        assertThat(member.getEmail(), is(email));
        assertThat(member.getName(), is(name));
    }
}