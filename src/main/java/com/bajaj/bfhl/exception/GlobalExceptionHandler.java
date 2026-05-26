package com.bajaj.bfhl.exception;

import com.bajaj.bfhl.config.CandidateConfig;
import com.bajaj.bfhl.dto.BfhlResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;

/**
 * Global exception handler for graceful error responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private final CandidateConfig candidateConfig;

    public GlobalExceptionHandler(CandidateConfig candidateConfig) {
        this.candidateConfig = candidateConfig;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<BfhlResponse> handleInvalidPayload(HttpMessageNotReadableException ex) {
        logger.error("Invalid request payload: {}", ex.getMessage());
        return ResponseEntity.ok(buildErrorResponse());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<BfhlResponse> handleNullPointer(NullPointerException ex) {
        logger.error("Null pointer encountered: {}", ex.getMessage());
        return ResponseEntity.ok(buildErrorResponse());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BfhlResponse> handleGenericException(Exception ex) {
        logger.error("Internal processing error: {}", ex.getMessage(), ex);
        return ResponseEntity.ok(buildErrorResponse());
    }

    private BfhlResponse buildErrorResponse() {
        BfhlResponse response = new BfhlResponse();
        response.setIsSuccess(false);
        response.setUserId(candidateConfig.getUserId());
        response.setEmail(candidateConfig.getEmail());
        response.setRollNumber(candidateConfig.getRollNumber());
        response.setEvenNumbers(Collections.emptyList());
        response.setOddNumbers(Collections.emptyList());
        response.setAlphabets(Collections.emptyList());
        response.setSpecialCharacters(Collections.emptyList());
        response.setSum("0");
        response.setConcatString("");
        return response;
    }
}
