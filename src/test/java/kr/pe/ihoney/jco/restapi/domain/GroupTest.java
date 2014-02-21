package kr.pe.ihoney.jco.restapi.domain;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import kr.pe.ihoney.jco.restapi.domain.type.GroupType;

import org.junit.Before;
import org.junit.Test;

public class GroupTest {
    private Member owner;

    @Before
    public void setUp() {
        owner = new Member("owner@rocking.api", "member");
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
    public void 관리자가_null인_경우() {
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
        Group group = new Group("group", GroupType.PUBLIC, owner);
        assertThat(group.isPrivate(), is(false));
    }
}
