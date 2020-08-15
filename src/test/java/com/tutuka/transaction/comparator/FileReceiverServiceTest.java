package com.tutuka.transaction.comparator;


import com.tutuka.transaction.comparator.dal.repository.AuditTrailRepository;
import com.tutuka.transaction.comparator.service.FileReceiverServiceImpl;
import com.tutuka.transaction.comparator.service.model.ResultDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

/**
 * Test class for the FileReceiver Service.
 *
 * @author samuel gwokuda
 */

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class FileReceiverServiceTest {

    @InjectMocks
    private FileReceiverServiceImpl fileReceiverService;

    @Mock
    private AuditTrailRepository auditTrailRepository;

    private static final String FIRST_FILE_NAME = "ClientMarkoffFile20140113 (1).csv";
    private static final String SECOND_FILE_NAME = "TutukaMarkoffFile20140113 (1).csv";

    @Value("${user.dir}")
    private String projectDirectory;

    @Before
    public void initTests() {
        Mockito.when(auditTrailRepository.save(any()))
                .thenAnswer(i -> i.getArguments()[0]);
        FileReceiverServiceImpl.TUTUKA_FOLDER = projectDirectory + "/src/main/resources/tempFiles";
    }

    @Test
    public void testComparison() {
        final Pair<ResultDto, ResultDto> resultPair = fileReceiverService.compareFiles(FIRST_FILE_NAME, SECOND_FILE_NAME);

        assertThat(resultPair.getFirst())
                .hasFieldOrPropertyWithValue("fileName", FIRST_FILE_NAME)
                .hasFieldOrPropertyWithValue("fileCount", 306)
                .hasFieldOrPropertyWithValue("similarity", 290)
                .hasFieldOrPropertyWithValue("differentRecords", 16)
                .matches(resultDto -> (resultDto.getTransactions().size() == resultDto.getDifferentRecords()), "correct number of dissimilar records");

        assertThat(resultPair.getSecond())
                .hasFieldOrPropertyWithValue("fileName", SECOND_FILE_NAME)
                .hasFieldOrPropertyWithValue("fileCount", 305)
                .hasFieldOrPropertyWithValue("similarity", 290)
                .hasFieldOrPropertyWithValue("differentRecords", 15);
    }
}
