package kr.pe.ihoney.jco.restapi.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import kr.pe.ihoney.jco.restapi.domain.type.GroupType;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.joda.time.DateTime;
import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Group extends DomainAuditable {
    private static final long serialVersionUID = 1246400743376293747L;

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Getter
    @Column(unique = true, nullable = false)
    private String name;            // 그룹명
    @Getter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GroupType type;         // 커뮤니티 유형
    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    private Member owner;           // 생성자

    public Group(String name, GroupType type, Member member) {
        setName(name);
        setType(type);
        setOwner(member);
        setCreatedDate(DateTime.now());
        setCreatedBy(member);        
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
}
