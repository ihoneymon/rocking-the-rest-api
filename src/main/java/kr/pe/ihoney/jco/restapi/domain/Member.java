package kr.pe.ihoney.jco.restapi.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.Assert;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 회원
 * 
 * @author ihoneymon
 * 
 */
@Entity
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@EqualsAndHashCode(of = { "community", "user" }, callSuper = false)
@ToString(of = { "id", "community", "user" }, callSuper = false)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement(name = "member")
public class Member implements Serializable {
    private static final long serialVersionUID = -4479702752651514475L;

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique=true)
    private String nickName;        //별명
    @Getter
    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    private Community community;    //커뮤니티
    @Getter
    @OneToOne(fetch = FetchType.LAZY, optional=false)
    private User user;              //사용자
    @Getter
    @DateTimeFormat
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    
    public Member(String nickName, Community community, User user) {
        Assert.notNull(nickName, "member.require.nick_name");
        Assert.notNull(community, "member.require.community");
        Assert.notNull(user, "member.require.user");
        this.community = community;
        this.user = user;
    }
}
