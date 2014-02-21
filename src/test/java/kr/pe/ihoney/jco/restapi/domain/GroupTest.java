package kr.pe.ihoney.jco.restapi.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import kr.pe.ihoney.jco.restapi.common.exception.RestApiException;
import kr.pe.ihoney.jco.restapi.domain.type.GroupType;

import org.junit.Before;
import org.junit.Test;

public class GroupTest {
    private User owner;

    @Before
    public void setUp() {
        owner = new User("owner@rocking.api", "member");
    }

    @Test(expected = IllegalArgumentException.class)
    public void 커뮤니티명이_null인_경우() {
        @SuppressWarnings("unused")
        Group group = new Group(null, GroupType.PRIVATE, owner);
        fail();
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void 커뮤니티유형이_null인_경우() {
        @SuppressWarnings("unused")
        Group group = new Group("TestCommunity", null, owner);
        fail();
    }

    @Test(expected=IllegalArgumentException.class)
    public void 소유자가_null인_경우() {
        @SuppressWarnings("unused")
        Group group = new Group("TestCommunity", GroupType.PRIVATE, null);
        fail();
    }
    
    @Test
    public void 정상생성() {
        String groupName = "Group";
        GroupType type = GroupType.PRIVATE;
        Group group = new Group(groupName, type, owner);
        assertThat(group.getName(), is(groupName));
        assertThat(group.getType(), is(type));
    }
    
    @Test
    public void 비공개_그룹생성_확인() {
        Group group = new Group("group", GroupType.PRIVATE, owner);
        assertThat(group.isPrivate(), is(true));
    }
    
    @Test
    public void 공개_그룹생성_확인() {
        Group group = getPublicGroup();
        assertThat(group.isPrivate(), is(false));
    }
    
    @Test
    public void 그룹_회원추가() {
        Group group = getPublicGroup();
        User user1 = new User("user1@rocking.api", "user1");
        group.addMember(new Member("회원1", group, user1));
        assertThat(group.getMembers().size(), is(1));
        
        User user2 = new User("user2@rocking.api", "user1");
        group.addMember(new Member("회원2", group, user2));
        assertThat(group.getMembers().size(), is(2));
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void 그룹_회원을_null로_입력하는_경우() {
        Group group = getPublicGroup();
        group.addMember(null);
    }
    
    @Test(expected=RestApiException.class)
    public void 중복된_회원_입력하는_경우() {
        Group group = getPublicGroup();
        User user1 = new User("user1@rocking.api", "user1");
        group.addMember(new Member("회원1", group, user1));
        group.addMember(new Member("회원1", group, user1));
        fail();
    }
    
    private Group getPublicGroup() {
        return new Group("group", GroupType.PUBLIC, owner);
    }
}
