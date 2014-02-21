package kr.pe.ihoney.jco.restapi.domain;

import static org.junit.Assert.*;
import kr.pe.ihoney.jco.restapi.domain.type.GroupType;

import org.junit.Before;
import org.junit.Test;

public class GroupTest {
    private User manager;

    @Before
    public void setUp() {
        manager = new User("Tester", "test@test.email");
    }

    @Test(expected = IllegalArgumentException.class)
    public void 커뮤니티명이_null인_경우() {
        @SuppressWarnings("unused")
        Group group = new Group(null, GroupType.PRIVATE, manager);
        fail();
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void 커뮤니티유형이_null인_경우() {
        @SuppressWarnings("unused")
        Group community = new Group("TestCommunity", null, manager);
        fail();
    }

    @Test(expected=IllegalArgumentException.class)
    public void 관리자가_null인_경우() {
        @SuppressWarnings("unused")
        Group community = new Group("TestCommunity", GroupType.PRIVATE, null);
        fail();
    }
}
