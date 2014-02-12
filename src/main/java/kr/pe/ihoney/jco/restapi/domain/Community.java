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
import lombok.ToString;

import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 커뮤니티 도메인
 * 
 * @author ihoneymon
 * 
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "name")
@ToString(of = { "id", "name", "type", "manager", "createdDate" })
@JsonIgnoreProperties
public class Community {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Getter
    @Column(unique = true, nullable = false)
    private String name;            //커뮤니티명
    @Getter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CommunityType type;     //커뮤니티유형
    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    private User manager;           //관리자
    @Getter
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;       //생성일

    public Community(String name, CommunityType type, User manager) {
        setName(name);
        setType(type);
        setManager(manager);
        this.createdDate = Calendar.getInstance().getTime();
    }

    public void setName(String name) {
        Assert.hasText(name, "community.require.name");
        this.name = name;
    }

    private void setType(CommunityType type) {
        Assert.notNull(type);
        this.type = type;
    }

    public void setManager(User manager) {
        Assert.notNull(manager);
        this.manager = manager;
    }

}
