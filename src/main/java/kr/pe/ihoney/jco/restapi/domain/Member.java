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
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import kr.pe.ihoney.jco.restapi.web.support.adapter.DateTimeAdapter;
import kr.pe.ihoney.jco.restapi.web.support.serializer.DateTimeSerializer;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.joda.time.DateTime;
import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 회원
 * 
 * @author ihoneymon
 * 
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = { "group", "user" })
@ToString(of = { "id", "group", "user" })
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement(name = "member")
public class Member implements Serializable {
    private static final long serialVersionUID = -4479702752651514475L;

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Getter
    @Column(unique = true)
    private String nickName; // 별명
    @Getter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Group group; // 그룹
    @Getter
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private User user; // 사용자
    @Getter
    @JsonSerialize(using = DateTimeSerializer.class)
    @XmlJavaTypeAdapter(type = DateTime.class, value = DateTimeAdapter.class)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    public Member(String nickName, Group group, User user) {
        Assert.hasText(nickName, "member.require.nickName");
        Assert.notNull(group, "member.require.group");
        Assert.notNull(user, "member.require.user");
        this.group = group;
        this.user = user;
    }

    public Member changeNickName(String nickName) {
        Assert.hasText(nickName, "member.require.nickName");
        this.nickName = nickName;
        return this;
    }
}
