package com.tutuka.transaction.comparator.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Builder
@AllArgsConstructor
public class ResultDto {
    private String fileName;
    private String auditId;
    private int fileCount;
    private int similarity;
    private int differentRecords;
    private List<Transaction> transactions;
}
