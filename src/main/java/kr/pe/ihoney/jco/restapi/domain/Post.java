package kr.pe.ihoney.jco.restapi.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

import kr.pe.ihoney.jco.restapi.domain.type.PostType;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.joda.time.DateTime;
import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 포스트 도메인
 * 
 * @author ihoneymon
 * 
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = { "title" }, callSuper = false)
@ToString(of = { "id", "type", "title", "article" }, callSuper = false)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement(name = "post")
public class Post extends DomainAuditable {
    private static final long serialVersionUID = -9171893825408773970L;

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    private Group group;
    @Getter
    @Enumerated(EnumType.STRING)
    private PostType type;
    @Getter
    private String title; // 제목
    @Getter
    private String article; // 본문
    @Getter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Member member;

    public Post(Group group, String title, String article, Member member) {
        setGroup(group);
        setTitle(title);
        setArticle(article);
        setMember(member);
        this.type = PostType.PRIVATE;
    }

    private Post setGroup(Group group) {
        Assert.notNull(group, "post.require.group");
        this.group = group;
        return this;
    }

    public Post setTitle(String title) {
        Assert.hasText(title, "post.require.title");
        this.title = title;
        return this;
    }

    public Post setArticle(String article) {
        Assert.hasText(article, "post.require.article");
        this.article = article;
        return this;
    }

    public Post setMember(Member member) {
        Assert.notNull(member, "post.require.member");
        this.member = member;
        setCreatedBy(member);
        setCreatedDate(DateTime.now());
        return this;
    }

    public boolean doesOpen(Member member) {
        if (PostType.PRIVATE == getType()) {
            return this.member.equals(member);
        }
        return true;
    }
}