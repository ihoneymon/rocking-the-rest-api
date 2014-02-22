package kr.pe.ihoney.jco.restapi.domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

import kr.pe.ihoney.jco.restapi.common.exception.RestApiException;
import kr.pe.ihoney.jco.restapi.domain.type.GroupType;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.Sets;

/**
 * 커뮤니티 도메인
 * 
 * @author ihoneymon
 * 
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = { "name" }, callSuper = false)
@ToString(of = { "id", "name", "type", "manager" }, callSuper = false)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement(name = "group")
@XmlType(propOrder = { "id", "name", "type", "manager" })
public class Group implements Serializable {
    private static final long serialVersionUID = 1246400743376293747L;

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlSchemaType(name = "string")
    private Long id;
    @Getter
    @Column(unique = true, nullable = false)
    private String name; // 커뮤니티명
    @Getter
    @XmlElement(name = "type")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private GroupType type; // 커뮤니티유형
    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    private Member manager; // 관리자
    @Getter
    @OneToOne
    private User createdBy;
    @Getter
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Getter
    @OneToMany(fetch = FetchType.LAZY)
    private Set<Member> members;

    public Group(String name, GroupType type, User createdBy) {
        setName(name);
        setType(type);
        setCreatedBy(createdBy);
        this.createdDate = Calendar.getInstance().getTime();
        this.members = Sets.newHashSet();
    }

    private Group setCreatedBy(User createdBy) {
        Assert.notNull(createdBy, "group.require.createdBy");
        this.createdBy = createdBy;
        return this;
    }

    public Group setName(String name) {
        Assert.hasText(name, "group.require.name");
        this.name = name;
        return this;
    }

    private Group setType(GroupType type) {
        Assert.notNull(type);
        this.type = type;
        return this;
    }

    public Group setManager(Member manager) {
        Assert.notNull(manager, "group.require.manager");
        this.manager = manager;
        return this;
    }

    public Group addMember(Member member) throws RestApiException {
        if (members.contains(member)) {
            throw new RestApiException("group.exception.registered.member");
        }
        members.add(member);
        return this;
    }
}
