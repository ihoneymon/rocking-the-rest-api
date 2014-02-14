package kr.pe.ihoney.jco.restapi.domain;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 사용자 도메인
 * 
 * @author ihoneymon
 * 
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = { "name", "email" })
@ToString(of = { "id", "name", "email", "createdDate" })
@JsonIgnoreProperties
@XmlRootElement(name="user")
public class User {
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
    @Column(nullable = false)
    private String password;
    @Getter
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Community> communities;
    @Getter
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    public User(String name, String email, String password) {
        setName(name);
        setEmail(email);
        setPassword(password);
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

    public User setPassword(String password) {
        Assert.hasText(password, "user.require.password");
        this.password = password;
        return this;
    }

    public User addCommunity(List<Community> communities) {
        communities.clear();
        if (!communities.isEmpty()) {
            communities.addAll(communities);
        }
        return this;
    }

    public User changePassword(String newPassword) {
        Assert.hasText(newPassword, "syste.require.user-password");
        this.password = newPassword;
        return this;
    }

}