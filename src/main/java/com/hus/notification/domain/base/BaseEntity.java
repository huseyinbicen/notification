package com.hus.notification.domain.base;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@MappedSuperclass
@Where(clause = BaseEntity.SOFT_DELETE_CLAUSE)
public abstract class BaseEntity {
    public static final String SOFT_DELETE_CLAUSE = "deleted = 0";

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Audited
    private LocalDateTime createTime;

    @UpdateTimestamp
    @Audited
    private LocalDateTime lastUpdateTime;

    @NotNull
    @ColumnDefault("false")
    @Audited
    private Boolean deleted = false;

    public static String getSoftDeleteClause() {
        return SOFT_DELETE_CLAUSE;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(LocalDateTime lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
