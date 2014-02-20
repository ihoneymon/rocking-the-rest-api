package kr.pe.ihoney.jco.restapi.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
@EqualsAndHashCode(of = { "email", "name" })
@ToString(of = { "id", "email", "name" })
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Member implements Serializable {
    private static final long serialVersionUID = -4479702752651514475L;

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Getter
    @Column(unique = true, nullable = false)
    private String email; // 별명
    @Getter
    private String name; // 사용자명
    @Getter
    @JsonSerialize(using = DateTimeSerializer.class)
    @Temporal(TemporalType.TIMESTAMP)
    private DateTime createdDate;

    public Member(String email, String name) {
        setEmail(email);
        setName(name);
        this.createdDate = DateTime.now();
    }

    private Member setEmail(String email) {
        Assert.hasText(email, "member.require.email");
        this.email = email;
        return this;
    }

    private Member setName(String name) {
        Assert.hasText(name, "member.require.name");
        this.name = name;
        return this;
    }

    public Member changeName(String name) {
        setName(name);
        return this;
    }
}
