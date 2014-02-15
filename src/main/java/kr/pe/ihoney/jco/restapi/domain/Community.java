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
import javax.xml.bind.annotation.XmlRootElement;

import kr.pe.ihoney.jco.restapi.domain.type.CommunityType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.joda.time.DateTime;
import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 커뮤니티 도메인
 * 
 * @author ihoneymon
 * 
 */
@Entity
@NoArgsConstructor
@EqualsAndHashCode(of = {"name"}, callSuper=false)
@ToString(of = { "id", "name", "type", "manager" }, callSuper=false)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@XmlRootElement(name="community")
public class Community extends DomainAuditable {
    private static final long serialVersionUID = 1246400743376293747L;
    
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Getter
    @Column(unique = true, nullable = false)
    private String name; // 커뮤니티명
    @Getter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CommunityType type; // 커뮤니티유형
    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    private User manager; // 관리자

    public Community(String name, CommunityType type, User createdBy) {
        setName(name);
        setType(type);
        setManager(manager);
        setCreatedBy(createdBy);
        setCreatedDate(DateTime.now());
    }

    public void setName(String name) {
        Assert.hasText(name, "community.require.name");
        this.name = name;
    }

    private void setType(CommunityType type) {
        Assert.notNull(type);
        this.type = type;
    }

    public void setManager(User manager) {
        Assert.notNull(manager);
        this.manager = manager;
    }
}
