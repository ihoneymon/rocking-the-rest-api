package kr.pe.ihoney.jco.restapi.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

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
@ToString(of = { "id", "title", "article" }, callSuper = false)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "group" })
public class Post extends DomainAuditable {
    private static final long serialVersionUID = -9171893825408773970L;

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Getter
    private String title; // 제목
    @Getter
    private String article; // 본문
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Group group; // 그룹

    public Post(String title, String article, Group group, Member member) {
        Assert.hasText(title, "post.require.title");
        Assert.hasText(article, "post.require.article");
        Assert.notNull(group, "post.require.group");

        this.group = group;
        this.title = title;
        this.article = article;
        setCreatedBy(member);
        setCreatedDate(DateTime.now());
    }

    public Post changeTitle(String title) {
        Assert.hasText(title, "post.require.title");
        this.title = title;
        return this;
    }

    public Post changeArticle(String article) {
        Assert.hasText(article, "post.require.article");
        this.article = article;
        return this;
    }

}