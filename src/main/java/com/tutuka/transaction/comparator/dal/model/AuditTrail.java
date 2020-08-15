package com.tutuka.transaction.comparator.dal.model;

import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

/**
 * An Audit Trail
 */

@Data
@Entity(name = "audit_trail")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AuditTrail implements Serializable {

    private static final long serialVersionUID = -41184776390993239L;

    @Id
    private String id;

    private LocalDateTime created;

    private LocalDateTime updated;

    private LocalDateTime deleted;

    @Column(name = "file_name_1")
    private String file1;

    @Column(name = "file_name_2")
    private String file2;

    @Column(name = "file_count_1")
    private int fileCount1;

    @Column(name = "file_count_2")
    private int fileCount2;

    private int similarity;

    @OneToMany(mappedBy = "auditTrail", cascade = CascadeType.ALL)
    private List<Transaction> transactions;

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
        created = LocalDateTime.now();
    }

    @PreUpdate
    public void update() {
        updated = LocalDateTime.now();
    }

    public static AuditTrailBuilder builder() {
        return new AuditTrailBuilder();
    }

    public static class AuditTrailBuilder {
        private String file1;
        private String file2;
        private int fileCount1;
        private int fileCount2;
        private int similarity;

        public AuditTrailBuilder file1(final String file1) {
            this.file1 = file1;
            return this;
        }

        public AuditTrailBuilder file2(final String file2) {
            this.file2 = file2;
            return this;
        }

        public AuditTrailBuilder fileCount1(final int fileCount1) {
            this.fileCount1 = fileCount1;
            return this;
        }

        public AuditTrailBuilder fileCount2(final int fileCount2) {
            this.fileCount2 = fileCount2;
            return this;
        }

        public AuditTrailBuilder similarity(final int similarity) {
            this.similarity = similarity;
            return this;
        }

        public AuditTrail build() {
            final AuditTrail auditTrail = new AuditTrail();
            auditTrail.setFile1(this.file1);
            auditTrail.setFile2(this.file2);
            auditTrail.setSimilarity(this.similarity);
            auditTrail.setFileCount1(this.fileCount1);
            auditTrail.setFileCount2(this.fileCount2);
            return auditTrail;
        }
    }

}
