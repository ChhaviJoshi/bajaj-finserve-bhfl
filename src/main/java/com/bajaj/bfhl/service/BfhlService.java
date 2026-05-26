package com.bajaj.bfhl.service;

import com.bajaj.bfhl.dto.BfhlRequest;
import com.bajaj.bfhl.dto.BfhlResponse;

/**
 * Service interface for BFHL data processing.
 */
public interface BfhlService {

    /**
     * Process the input data array and return classified results.
     *
     * @param request the request containing data array
     * @return processed response with classified data
     */
    BfhlResponse processData(BfhlRequest request);
}
