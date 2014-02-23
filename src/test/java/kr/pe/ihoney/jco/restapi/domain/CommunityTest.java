package kr.pe.ihoney.jco.restapi.domain;

import static org.junit.Assert.*;
import kr.pe.ihoney.jco.restapi.domain.type.CommunityType;

import org.junit.Before;
import org.junit.Test;

public class CommunityTest {
    private User manager;

    @Before
    public void setUp() {
        manager = new User("Tester", "test@test.email");
    }

    @Test(expected = IllegalArgumentException.class)
    public void 커뮤니티명이_null인_경우() {
        @SuppressWarnings("unused")
        Community community = new Community(null, CommunityType.PRIVATE, manager);
        fail();
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void 커뮤니티유형이_null인_경우() {
        @SuppressWarnings("unused")
        Community community = new Community("TestCommunity", null, manager);
        fail();
    }

    @Test(expected=IllegalArgumentException.class)
    public void 관리자가_null인_경우() {
        @SuppressWarnings("unused")
        Community community = new Community("TestCommunity", CommunityType.PRIVATE, null);
        fail();
    }
}
