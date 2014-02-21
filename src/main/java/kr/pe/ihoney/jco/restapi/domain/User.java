package kr.pe.ihoney.jco.restapi.domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import kr.pe.ihoney.jco.restapi.web.support.serializer.DateSerializer;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = { "email", "name" })
@ToString(of = { "id", "email", "name" })
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class User implements Serializable {
    private static final long serialVersionUID = -5349856940098058322L;

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Getter
    @Column(unique = true, nullable = false)
    private String email; // 이메일
    @Getter
    private String name; // 사용자명
    @Getter
    @JsonSerialize(using = DateSerializer.class)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    public User(String email, String name) {
        setEmail(email);
        setName(name);
        this.createdDate = Calendar.getInstance().getTime();
    }

    private User setEmail(String email) {
        Assert.hasText(email, "member.require.email");
        this.email = email;
        return this;
    }

    private User setName(String name) {
        Assert.hasText(name, "member.require.name");
        this.name = name;
        return this;
    }

    public User changeName(String name) {
        setName(name);
        return this;
    }
}
