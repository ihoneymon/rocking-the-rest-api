package kr.pe.ihoney.jco.restapi.domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 회원
 * 
 * @author ihoneymon
 * 
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = { "nickName", "group" }, callSuper=false)
@ToString(of = { "id", "nickName" }, callSuper=false)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "group", "user" })
public class Member implements Serializable {
    private static final long serialVersionUID = -4479702752651514475L;

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Getter
    private String nickName;
    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    private Group group;
    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @Getter
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    public Member(String nickName, Group group, User user) {
        Assert.hasText(nickName, "member.require.nickName");
        Assert.notNull(group, "member.require.group");
        Assert.notNull(user, "member.require.user");
        
        this.nickName = nickName;
        this.group = group;
        this.user = user;
        this.createdDate = Calendar.getInstance().getTime();
    }

    public Member changNickName(String nickName) {
        Assert.hasText(nickName, "member.require.nickName");
        this.nickName = nickName;
        return this;
    }
}
