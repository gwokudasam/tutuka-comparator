package com.tutuka.transaction.comparator.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.tutuka.transaction.comparator.service.FileReceiverServiceImpl.CSV_SEPARATOR;

/**
 * A Transaction
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class Transaction {

    private String profileName;
    private LocalDateTime transactionDate;
    private Integer transactionAmount;
    private String transactionDescription;
    private Integer transactionType;
    private String walletReference;
    private String transactionNarrative;
    private String transactionId;

    public static Transaction map(String line) {
        String[] lineEntries = line.split(CSV_SEPARATOR);

        if (lineEntries[0].equals("ProfileName")) {
            return null;
        }

        if (lineEntries.length == 7) {
            return Transaction.builder()
                    .profileName(lineEntries[0])
                    .transactionAmount(Integer.valueOf(lineEntries[2]))
                    .transactionDate(LocalDateTime.parse(lineEntries[1], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                    .transactionDescription(lineEntries[4])
                    .transactionType(Integer.valueOf(lineEntries[6]))
                    .transactionNarrative(lineEntries[3])
                    .transactionId(lineEntries[5])
                    .build();
        }

        return Transaction.builder()
                .profileName(lineEntries[0])
                .transactionAmount(Integer.valueOf(lineEntries[2]))
                .transactionDate(LocalDateTime.parse(lineEntries[1], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .transactionDescription(lineEntries[4])
                .transactionType(Integer.valueOf(lineEntries[6]))
                .walletReference(lineEntries[7])
                .transactionNarrative(lineEntries[3])
                .transactionId(lineEntries[5])
                .build();
    }

    public static Transaction map(com.tutuka.transaction.comparator.dal.model.Transaction transaction) {
        return Transaction.builder()
                .transactionId(transaction.getTransactionId())
                .transactionNarrative(transaction.getTransactionNarrative())
                .walletReference(transaction.getWalletReference())
                .transactionType(transaction.getTransactionType())
                .transactionDescription(transaction.getTransactionDescription())
                .transactionDate(transaction.getTransactionDate())
                .profileName(transaction.getProfileName())
                .transactionAmount(transaction.getTransactionAmount())
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Transaction)) {
            return false;
        }

        Transaction object = (Transaction) o;


        return StringUtils.equals(object.profileName, this.profileName) &&
                object.transactionDate.equals(this.transactionDate) &&
                object.transactionAmount.equals(this.transactionAmount) &&
                StringUtils.equals(object.transactionDescription, this.transactionDescription) &&
                object.transactionType.equals(this.transactionType) &&
                StringUtils.equals(object.transactionId, this.transactionId) &&
                StringUtils.equals(object.transactionNarrative, this.transactionNarrative) &&
                StringUtils.equals(object.walletReference, this.walletReference);

    }
}
