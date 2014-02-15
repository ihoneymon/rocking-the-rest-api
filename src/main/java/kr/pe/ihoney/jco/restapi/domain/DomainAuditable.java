package kr.pe.ihoney.jco.restapi.domain;

import java.util.Date;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.joda.time.DateTime;
import org.springframework.data.domain.Auditable;
import org.springframework.util.Assert;

@MappedSuperclass
public abstract class DomainAuditable implements Auditable<User, Long> {
    private static final long serialVersionUID = -410212408783103422L;

    @ManyToOne(fetch = FetchType.LAZY)
    private User createdBy;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @ManyToOne(fetch = FetchType.LAZY)
    private User lastModifiedBy;
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Override
    public boolean isNew() {
        return null == getClass();
    }

    @Override
    public User getCreatedBy() {
        return this.createdBy;
    }

    @Override
    public void setCreatedBy(User createdBy) {
        Assert.notNull(createdBy, "domain.require.createdBy");
        this.createdBy = createdBy;
    }

    @Override
    public DateTime getCreatedDate() {
        return null == this.createdDate ? null : new DateTime(this.createdDate); 
    }

    @Override
    public void setCreatedDate(DateTime creationDate) {
        this.createdDate = null == creationDate ? null : creationDate.toDate();
    }

    @Override
    public User getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    @Override
    public void setLastModifiedBy(User lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Override
    public DateTime getLastModifiedDate() {
        return null == this.lastModifiedBy ? null : new DateTime(this.lastModifiedDate);
    }

    @Override
    public void setLastModifiedDate(DateTime lastModifiedDate) {
        this.createdDate = null == lastModifiedDate ? null : this.lastModifiedDate;
    }

}
