package kr.pe.ihoney.jco.restapi.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import kr.pe.ihoney.jco.restapi.domain.type.GroupType;

import org.junit.Before;
import org.junit.Test;

public class PostTest {

    private Group group;
    private Member member;

    @Before
    public void setUp() {
        User owner = new User("owner@rocking.api", "owner");
        User user1 = new User("user1@rocking.api", "사용자1");
        group = new Group("Test Team", GroupType.PUBLIC, owner);
        member = new Member("회원1", group, user1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void 제목이_null인_경우() {
        @SuppressWarnings("unused")
        Post post = new Post(null, "article", group, member);
        fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void 본문이_null인_경우() {
        @SuppressWarnings("unused")
        Post post = new Post("title", null, group, member);
        fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void 그룹이_null인_경우() {
        @SuppressWarnings("unused")
        Post post = new Post("title", "article", null, member);
        fail();
    }

    @Test(expected = IllegalArgumentException.class)
    public void 생성자가_null인_경우() {
        @SuppressWarnings("unused")
        Post post = new Post("title", "article", group, null);
        fail();
    }

    @Test
    public void 글_정상생성() {
        String title = "title";
        String article = "article";
        Post post = new Post(title, article, group, member);
        assertThat(post.getTitle(), is(title));
        assertThat(post.getArticle(), is(article));
    }

    @Test
    public void 글_제목변경() {
        String title = "title";
        String article = "article";
        Post post = new Post(title, article, group, member);
        String changeTitle = "changedTitle";
        post.changeTitle(changeTitle);
        assertThat(post.getTitle(), is(changeTitle));
    }
    
    @Test
    public void 글_본문변경() {
        String title = "title";
        String article = "article";
        Post post = new Post(title, article, group, member);
        String changeArticle = "changeArticle";
        post.changeArticle(changeArticle);
        assertThat(post.getArticle(), is(changeArticle));
    }
}
