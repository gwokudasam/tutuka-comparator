package com.tutuka.transaction.comparator.service;

import com.tutuka.transaction.comparator.dal.model.AuditTrail;
import com.tutuka.transaction.comparator.dal.repository.AuditTrailRepository;
import com.tutuka.transaction.comparator.service.model.ResultDto;
import com.tutuka.transaction.comparator.service.model.Transaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static com.tutuka.transaction.comparator.service.model.Transaction.map;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@Component
@RequiredArgsConstructor
@Slf4j
public class FileReceiverServiceImpl implements FileReceiverService {
    public static String TUTUKA_FOLDER;
    public static final String DIRECTORY_MARKER = "/";
    public static final String CSV_SEPARATOR = ",";

    @Value("${com.tutuka.fileDirectory:/tmp/Tutuka}")
    private String tutukaFolder;

    private final AuditTrailRepository repository;

    @PostConstruct
    public void init() {
        TUTUKA_FOLDER = tutukaFolder;
    }

    /**
     * @param file1 The fist file to compare
     * @param file2 The second file to compare
     * @return ResultDto pair
     */
    @Override
    public Pair<ResultDto, ResultDto> compareFiles(final String file1, final String file2) {

        final AuditTrail auditTrail = repository.save(AuditTrail.builder()
                .file1(file1)
                .file2(file2)
                .build());
        final List<com.tutuka.transaction.comparator.dal.model.Transaction> file1DifferentTransactions = new ArrayList<>();
        final List<com.tutuka.transaction.comparator.dal.model.Transaction> file2DifferentTransactions = new ArrayList<>();
        try {
            log.info("Start comparing");

            final Pair<Integer, Integer> firstComparisonPair = compare(file1, file2, file1DifferentTransactions);

            final Pair<Integer, Integer> secondComparisonPair = compare(file2, file1, file2DifferentTransactions);

            log.info("Finished comparing.");

            auditTrail.setFileCount1(firstComparisonPair.getFirst());
            auditTrail.setFileCount2(secondComparisonPair.getFirst());
            auditTrail.setSimilarity(firstComparisonPair.getSecond());

            file1DifferentTransactions.addAll(file2DifferentTransactions);

            auditTrail.setTransactions(file1DifferentTransactions);

            repository.save(auditTrail);

        } catch (final Exception e) {
            log.warn("Exception - {}", e.getLocalizedMessage());
            e.printStackTrace();
        }


        return Pair.of(ResultDto.builder()
                        .fileName(auditTrail.getFile1())
                        .fileCount(auditTrail.getFileCount1())
                        .similarity(auditTrail.getSimilarity())
                        .differentRecords(auditTrail.getFileCount1() - auditTrail.getSimilarity())
                        .auditId(auditTrail.getId())
                        .transactions(file1DifferentTransactions.stream()
                                .filter(transaction -> transaction.getFileName().equals(auditTrail.getFile1()))
                                .map(Transaction::map)
                                .collect(Collectors.toList()))
                        .build(),
                ResultDto.builder()
                        .fileName(auditTrail.getFile2())
                        .fileCount(auditTrail.getFileCount2())
                        .similarity(auditTrail.getSimilarity())
                        .differentRecords(auditTrail.getFileCount2() - auditTrail.getSimilarity())
                        .auditId(auditTrail.getId())
                        .transactions(file2DifferentTransactions.stream()
                                .filter(transaction -> transaction.getFileName().equals(auditTrail.getFile2()))
                                .map(Transaction::map)
                                .collect(Collectors.toList()))
                        .build());
    }

    /**
     * @param fileName1 The first file name
     * @param fileName2 The second file name
     * @param transactions A list of transactions
     * @return
     */
    private Pair<Integer, Integer> compare(final String fileName1, final String fileName2, final List<com.tutuka.transaction.comparator.dal.model.Transaction> transactions) {
        int count1 = 0;
        int similarity = 0;
        final AtomicBoolean matched = new AtomicBoolean(FALSE);
        try (LineIterator lineIterator1 = FileUtils.lineIterator(Paths.get(TUTUKA_FOLDER + DIRECTORY_MARKER + fileName1).toFile())) {
            while (lineIterator1.hasNext()) {
                final String line1 = lineIterator1.nextLine();
                final Transaction transaction = map(line1);
                matched.set(FALSE);

                if (transaction == null) {
                    continue;
                }

                ++count1;
                try (LineIterator lineIterator2 = FileUtils.lineIterator(Paths.get(TUTUKA_FOLDER + DIRECTORY_MARKER + fileName2).toFile())) {
                    while (lineIterator2.hasNext()) {
                        final Transaction innerTransaction = map(lineIterator2.nextLine());
                        if (transaction.equals(innerTransaction)) {
                            ++similarity;
                            matched.set(TRUE);
                            break;
                        }
                    }

                    if (!matched.get()) {
                        final com.tutuka.transaction.comparator.dal.model.Transaction transaction1 = com.tutuka.transaction.comparator.dal.model.Transaction.map(transaction);
                        transaction1.setFileName(fileName1);
                        transactions.add(transaction1);
                    }
                }
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return Pair.of(count1, similarity);
    }
}
