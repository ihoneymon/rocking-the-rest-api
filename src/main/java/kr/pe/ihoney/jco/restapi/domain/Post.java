package kr.pe.ihoney.jco.restapi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: ihoneymon
 * Date: 14. 2. 2
 * Time: 오전 11:48
 */
@Entity
@JsonIgnoreProperties
public class Post {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Getter
    @Setter
    private String article;
    @Getter
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    private User createdBy;
}
