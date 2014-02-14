package kr.pe.ihoney.jco.restapi.domain;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import kr.pe.ihoney.jco.restapi.domain.type.PostType;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 포스트 도메인
 * 
 * @author ihoneymon
 * 
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = { "title", "createdDate", "createdBy" })
@ToString(of = { "id", "type", "title", "article", "createdDate", "createdBy" })
@JsonIgnoreProperties
@XmlRootElement(name="post")
public class Post {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Getter
    @Enumerated(EnumType.STRING)
    private PostType type;
    @Getter
    private String title;
    @Getter
    private String article;
    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    private User createdBy;
    @Getter
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Getter
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    public Post(String title, String article, User createdBy) {
        setTitle(title);
        setArticle(article);
        setCreatedBy(createdBy);
        this.type = PostType.PRIVATE;
        this.createdDate = Calendar.getInstance().getTime();
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

    public Post setCreatedBy(User createdBy) {
        Assert.notNull(createdBy, "post.require.created-by");
        this.createdBy = createdBy;
        return this;
    }
}