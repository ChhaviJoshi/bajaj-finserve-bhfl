package com.bajaj.bfhl.config;

import org.springframework.context.annotation.Configuration;

/**
 * Candidate configuration with hardcoded details.
 */
@Configuration
public class CandidateConfig {

    private static final String USER_ID = "chhavi_joshi_17122005";
    private static final String EMAIL = "chhavijoshi230275@acropolis.in";
    private static final String ROLL_NUMBER = "0827IT23103";

    public String getUserId() {
        return USER_ID;
    }

    public String getEmail() {
        return EMAIL;
    }

    public String getRollNumber() {
        return ROLL_NUMBER;
    }
}
