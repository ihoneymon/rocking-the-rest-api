package kr.pe.ihoney.jco.restapi.domain;

import java.util.Date;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import kr.pe.ihoney.jco.restapi.web.support.serializer.DateTimeSerializer;
import lombok.ToString;

import org.joda.time.DateTime;
import org.springframework.data.domain.Auditable;
import org.springframework.util.Assert;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@MappedSuperclass
@ToString(of = { "createdBy", "createdDate", "lastModifiedBy",
        "lastModifiedDate" })
public abstract class DomainAuditable implements Auditable<Member, Long> {
    private static final long serialVersionUID = -410212408783103422L;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member createdBy;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member lastModifiedBy;
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Override
    public boolean isNew() {
        return null == getClass();
    }

    @Override
    public Member getCreatedBy() {
        return this.createdBy;
    }

    @Override
    public void setCreatedBy(Member createdBy) {
        Assert.notNull(createdBy, "domain.require.createdBy");
        this.createdBy = createdBy;
    }

    @Override
    @JsonSerialize(using = DateTimeSerializer.class)
    public DateTime getCreatedDate() {
        return null == this.createdDate ? null : new DateTime(this.createdDate);
    }

    @Override
    public void setCreatedDate(DateTime creationDate) {
        this.createdDate = null == creationDate ? null : creationDate.toDate();
    }

    @Override
    public Member getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    @Override
    public void setLastModifiedBy(Member lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Override
    @JsonSerialize(using = DateTimeSerializer.class)
    public DateTime getLastModifiedDate() {
        return null == this.lastModifiedBy ? null : new DateTime(
                this.lastModifiedDate);
    }

    @Override
    public void setLastModifiedDate(DateTime lastModifiedDate) {
        this.createdDate = null == lastModifiedDate ? null
                : this.lastModifiedDate;
    }

}
