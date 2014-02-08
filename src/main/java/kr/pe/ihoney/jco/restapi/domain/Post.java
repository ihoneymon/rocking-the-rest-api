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

import kr.pe.ihoney.jco.restapi.domain.type.PostType;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 포스트 도메인
 * @author ihoneymon
 *
 */
@Entity
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@EqualsAndHashCode(of={"title", "createdDate", "createdBy"})
@ToString(of={"id", "type", "title", "article", "createdDate", "createdBy"})
@JsonIgnoreProperties
public class Post {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Getter
    @Enumerated(EnumType.STRING)
    private PostType type;
    @Getter
    @Setter
    private String title;
    @Getter
    @Setter
    private String article;
    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    private User createdBy;
    @Getter
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
        
    public Post(String title, String article, User createdBy) {
        this.title = title;
        this.article = article;
        this.createdBy = createdBy;
        this.type = PostType.PRIVATE;
        this.createdDate = Calendar.getInstance().getTime();
    }
}
