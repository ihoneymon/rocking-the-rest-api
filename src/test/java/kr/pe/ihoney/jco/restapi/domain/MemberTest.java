package kr.pe.ihoney.jco.restapi.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import kr.pe.ihoney.jco.restapi.domain.type.GroupType;

import org.junit.Before;
import org.junit.Test;

public class MemberTest {
    private User user;
    private User owner;
    private Group group;

    @Before
    public void setUp() {
        user = new User("user1@rocking.api", "사용자1");
        owner = new User("owner@rocking.api", "그룹생성자");
        group = new Group("KUSG", GroupType.PUBLIC, owner);
    }

    @Test(expected = IllegalArgumentException.class)
    public void 별명이_null인_경우() {
        @SuppressWarnings("unused")
        Member member = new Member(null, group, user);
        fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void 그룹이_null인_경우() {
        @SuppressWarnings("unused")
        Member member = new Member("회원1", null, user);
        fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void 사용자가_null인_경우() {
        @SuppressWarnings("unused")
        Member member = new Member("회원1", group, null);
        fail();
    }

    @Test
    public void 회원_정상생성() {
        String nickName = "회원1";
        Member member = new Member(nickName, group, user);
        assertThat(member.getNickName(), is(nickName));
        assertThat(member.getGroup(), is(group));
        assertThat(member.getUser(), is(user));
    }

    @Test
    public void 회원_별명변경() {
        String nickName = "회원1";
        Member member = new Member(nickName, group, user);
        String changeNickName = "허니몬";
        member.changNickName(changeNickName);
        assertNotEquals(member.getNickName(), nickName);
        assertThat(member.getNickName(), is(changeNickName));
    }
}
