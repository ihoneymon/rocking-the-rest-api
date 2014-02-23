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
@EqualsAndHashCode(of = { "community", "title" }, callSuper = false)
@ToString(of = { "id", "type", "title", "article" }, callSuper = false)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "community" })
@XmlRootElement(name = "post")
public class Post extends DomainAuditable {
    private static final long serialVersionUID = -9171893825408773970L;

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    private Community community;
    @Getter
    @Enumerated(EnumType.STRING)
    private PostType type;
    @Getter
    private String title; // 제목
    @Getter
    private String article; // 본문

    public Post(Community community, String title, String article, Member member) {
        setGroup(community);
        setTitle(title);
        setArticle(article);
        this.type = PostType.PRIVATE;
        setCreatedBy(member);
        setCreatedDate(DateTime.now());
    }

    private Post setGroup(Community community) {
        Assert.notNull(community, "post.require.group");
        this.community = community;
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

    public boolean doesOpen(Member member) {
        return (PostType.PRIVATE == getType()) ? getCreatedBy().equals(member)
                : true;
    }

    public Long getCommunityId() {
        return community.getId();
    }
}