package com.bajaj.bfhl.controller;

import com.bajaj.bfhl.dto.BfhlRequest;
import com.bajaj.bfhl.dto.BfhlResponse;
import com.bajaj.bfhl.service.BfhlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for BFHL API.
 */
@RestController
public class BfhlController {

    private static final Logger logger = LoggerFactory.getLogger(BfhlController.class);

    private final BfhlService bfhlService;

    public BfhlController(BfhlService bfhlService) {
        this.bfhlService = bfhlService;
    }

    @PostMapping("/bfhl")
    public ResponseEntity<BfhlResponse> processData(@RequestBody BfhlRequest request) {
        logger.info("POST /bfhl - Request received");
        BfhlResponse response = bfhlService.processData(request);
        logger.info("POST /bfhl - Response generated");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/health")
    public ResponseEntity<java.util.Map<String, String>> healthCheck() {
        logger.info("GET /health - Health check requested");
        return ResponseEntity.ok(java.util.Map.of("status", "UP"));
    }
}
