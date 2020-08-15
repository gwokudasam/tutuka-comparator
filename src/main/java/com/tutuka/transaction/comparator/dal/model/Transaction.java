package com.tutuka.transaction.comparator.dal.model;

import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * A Transaction
 */

@Data
@Entity
@Table(name = "transaction")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1544441938634371046L;

    @Id
    private String id;

    private LocalDateTime created;

    private LocalDateTime updated;

    private LocalDateTime deleted;

    @Column(name = "profile_name")
    private String profileName;

    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;

    @Column(name = "transaction_amount")
    private Integer transactionAmount;

    @Column(name = "transaction_description")
    private String transactionDescription;

    @Column(name = "transaction_type")
    private int transactionType;

    @Column(name = "wallet_reference")
    private String walletReference;

    @Column(name = "transaction_narrative")
    private String transactionNarrative;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "file_name")
    private String fileName;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private AuditTrail auditTrail;

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

    public static Transaction map(final com.tutuka.transaction.comparator.service.model.Transaction transaction) {
        final Transaction transaction1 = new Transaction();
        transaction1.setProfileName(transaction.getProfileName());
        transaction1.setTransactionAmount(transaction.getTransactionAmount());
        transaction1.setTransactionDate(transaction.getTransactionDate());
        transaction1.setTransactionDescription(transaction.getTransactionDescription());
        transaction1.setTransactionId(transaction.getTransactionId());
        transaction1.setTransactionNarrative(transaction.getTransactionNarrative());
        transaction1.setTransactionType(transaction.getTransactionType());
        transaction1.setWalletReference(transaction.getWalletReference());
        return transaction1;
    }

}
