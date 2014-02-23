package kr.pe.ihoney.jco.restapi.domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

import kr.pe.ihoney.jco.restapi.domain.type.CommunityType;
import kr.pe.ihoney.jco.restapi.web.support.serializer.DateSerializer;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 커뮤니티 도메인
 * 
 * @author ihoneymon
 * 
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = { "name", "type" })
@ToString(of = { "id", "name", "type", "manager" })
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@XmlRootElement(name = "community")
@XmlType(propOrder = { "id", "name", "type", "manager" })
public class Community implements Serializable {
    private static final long serialVersionUID = 1246400743376293747L;

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlSchemaType(name = "string")
    private Long id;
    @Getter
    private String name; // 커뮤니티명
    @Getter
    @XmlElement(name = "type")
    @Enumerated(EnumType.STRING)
    private CommunityType type; // 커뮤니티유형
    @Getter
    @OneToOne(fetch = FetchType.LAZY)
    private Member manager; // 관리자
    @Getter
    @ManyToOne(fetch=FetchType.LAZY)
    private User createdBy;
    @Getter
    @JsonSerialize(using = DateSerializer.class)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    public Community(String name, CommunityType type, User createdBy) {
        setName(name);
        setType(type);
        setCreatedBy(createdBy);
        this.createdDate = Calendar.getInstance().getTime();
    }

    private Community setCreatedBy(User createdBy) {
        Assert.notNull(createdBy, "group.require.createdBy");
        this.createdBy = createdBy;
        return this;
    }

    public Community setName(String name) {
        Assert.hasText(name, "group.require.name");
        this.name = name;
        return this;
    }

    private Community setType(CommunityType type) {
        Assert.notNull(type);
        this.type = type;
        return this;
    }

    public Community setManager(Member manager) {
        Assert.notNull(manager, "group.require.manager");
        this.manager = manager;
        return this;
    }
}
