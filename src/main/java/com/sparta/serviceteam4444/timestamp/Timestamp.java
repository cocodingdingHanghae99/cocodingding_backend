package com.sparta.serviceteam4444.timestamp;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Timestamp {
    @CreatedDate
    private String createdAt;

    @LastModifiedDate
    private String modifiedAt;

    @PrePersist
    public void onPrePersist(){
        this.createdAt = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.modifiedAt = this.createdAt;
    }

    @PreUpdate
    public void onPreUpdate(){
        this.modifiedAt = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
