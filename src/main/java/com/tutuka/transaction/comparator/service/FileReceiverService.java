package com.tutuka.transaction.comparator.service;

import com.tutuka.transaction.comparator.service.model.ResultDto;
import org.springframework.data.util.Pair;

import java.io.IOException;

public interface FileReceiverService {

    Pair<ResultDto, ResultDto> compareFiles(final String path1, final String path2) throws IOException;
}
