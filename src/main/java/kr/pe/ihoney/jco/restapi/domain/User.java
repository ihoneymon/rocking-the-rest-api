package kr.pe.ihoney.jco.restapi.domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import kr.pe.ihoney.jco.restapi.domain.type.UserGradeType;
import kr.pe.ihoney.jco.restapi.web.support.adapter.DateAdapter;
import kr.pe.ihoney.jco.restapi.web.support.serializer.DateTimeSerializer;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 사용자 도메인
 * 
 * @author ihoneymon
 * 
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = { "name", "email" })
@ToString(of = { "id", "name", "email", "createdDate" })
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement(name = "user")
public class User implements Serializable {
    private static final long serialVersionUID = -3393324506709169733L;

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Getter
    @Column(nullable = false)
    private String name;
    @Getter
    @Column(unique = true, nullable = false)
    private String email;
    @Getter
    @Enumerated(EnumType.STRING)
    private UserGradeType grade;
    @Getter
    private String refreshToken;
    @JsonSerialize(using = DateTimeSerializer.class)
    @XmlJavaTypeAdapter(type = Date.class, value = DateAdapter.class)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    public User(String name, String email) {
        setName(name);
        setEmail(email);
        this.createdDate = Calendar.getInstance().getTime();
    }

    public User setName(String name) {
        Assert.hasText(name, "user.require.name");
        this.name = name;
        return this;
    }

    public User setEmail(String email) {
        Assert.hasText(email, "user.require.email");
        this.email = email;
        return this;
    }

    public User addCommunity(List<Community> communities) {
        communities.clear();
        if (!communities.isEmpty()) {
            communities.addAll(communities);
        }
        return this;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }
    
    public User changeRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }
}