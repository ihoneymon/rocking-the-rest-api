package kr.pe.ihoney.jco.restapi.domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
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
 * 그룹 도메인
 * 
 * @author ihoneymon
 * 
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = { "name" }, callSuper = false)
@ToString(of = { "id", "name", "type", "owner" }, callSuper = false)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "members" })
public class Group implements Serializable {
    private static final long serialVersionUID = 1246400743376293747L;

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Getter
    @Column(unique = true)
    private String name;            // 그룹명
    @Getter
    @Enumerated(EnumType.STRING)
    private GroupType type;         // 커뮤니티 유형
    @Getter
    @OneToOne(fetch = FetchType.LAZY)
    private Member owner;           // 관리자
    @Getter
    @OneToMany(fetch=FetchType.LAZY, mappedBy="group", cascade=CascadeType.ALL, orphanRemoval=true)
    private Set<Member> members;     // 회원
    @Getter
    @ManyToOne(fetch=FetchType.LAZY)
    private User createdBy;
    @Getter
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    public Group(String name, GroupType type, User owner) {
        Assert.notNull(owner, "group.require.owner");
        
        setName(name);
        setType(type);
        setOwner(new Member("Owner", this, owner));
        members = Sets.newHashSet();
        this.createdBy = owner;
        this.createdDate = Calendar.getInstance().getTime();
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

    public Group setOwner(Member owner) {
        Assert.notNull(owner, "group.require.owner");
        this.owner = owner;
        return this;
    }
    
    public Group changeName(String name) {
        Assert.hasText(name, "group.require.name");
        this.name = name;
        return this;
    }

    public Boolean isPrivate() {
        return this.type == GroupType.PRIVATE;
    }

    public Group addMember(Member member) throws RestApiException {
        Assert.notNull(member);
        if(members.contains(member)) {
            throw new RestApiException("group.exception.registerd.member");
        }
        this.members.add(member);
        return this;
    }    
}
