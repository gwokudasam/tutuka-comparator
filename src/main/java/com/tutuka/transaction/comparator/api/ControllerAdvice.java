package com.tutuka.transaction.comparator.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;

/**
 * The class Global controller exception handler is a generic and central point for all
 * application exceptions.
 *
 * @author samuel gwokuda
 * @version v1.0
 * @since v1.0
 */

@RestControllerAdvice
@Slf4j
public class ControllerAdvice {

    /**
     * Method to handle <i>IO Exceptions</i> http error
     *
     * @param io the ex to get its information
     * @return the http status code with empty response body
     * @since v0.1
     */

    @ExceptionHandler(IOException.class)
    public ResponseEntity<Void> handleIOException(final IOException io) {
        log.error("IOException Advice - {}", io.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                .build();
    }

    /**
     * Method to handle <i>Not Found Exceptions</i> http error
     *
     * @param enfe the ex to get its information
     * @return the http status code with empty response body
     * @since v0.1
     */

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(final EntityNotFoundException enfe) {
        log.error("EntityNotFoundException Advice - {}", enfe.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(enfe.getMessage());
    }

    /**
     * Method to handle <i>Exceptions</i> http general exception
     *
     * @param e the ex to get its information
     * @return the http status code with empty response body
     * @since v0.1
     */

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> handleException(final Exception e) {
        log.error("Exception Advice - {}", e.getLocalizedMessage());
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
    }
}
