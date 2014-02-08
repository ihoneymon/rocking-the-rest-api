package kr.pe.ihoney.jco.restapi.domain;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
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

import kr.pe.ihoney.jco.restapi.domain.type.CommunityType;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 커뮤니티 도메인
 * @author ihoneymon
 *
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of="name")
@ToString(of={"id", "name", "type", "createdBy", "createdDate"})
@JsonIgnoreProperties
public class Community {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Getter
    @Setter
    @Column(unique=true)
    private String name;
    @Getter
    @Enumerated(EnumType.STRING)
    private CommunityType type;
    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    private User createdBy;
    @Getter
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    public Community(String name, CommunityType type, User createdBy) {
        this.name = name;
        this.type = type;
        this.createdBy = createdBy;
        this.createdDate = Calendar.getInstance().getTime();
    }
}
