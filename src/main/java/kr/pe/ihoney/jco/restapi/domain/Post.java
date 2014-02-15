package kr.pe.ihoney.jco.restapi.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

import kr.pe.ihoney.jco.restapi.domain.type.PostType;
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
@NoArgsConstructor
@EqualsAndHashCode(of = { "title" }, callSuper=false)
@ToString(of = { "id", "type", "title", "article" }, callSuper=false)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@XmlRootElement(name="post")
public class Post extends DomainAuditable {
    private static final long serialVersionUID = -9171893825408773970L;
    
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

    public Post(String title, String article, User createdBy) {
        setTitle(title);
        setArticle(article);
        setCreatedBy(createdBy);
        setCreatedDate(DateTime.now());
        this.type = PostType.PRIVATE;
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
}