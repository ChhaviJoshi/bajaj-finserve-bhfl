package com.bajaj.bfhl.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Type-safe configuration properties for candidate details.
 */
@Configuration
@ConfigurationProperties(prefix = "candidate")
public class CandidateConfig {

    private String email;
    private String rollNumber;

    // Hardcoded as per requirement
    private static final String USER_ID = "chhavi_joshi_17122005";

    public String getUserId() {
        return USER_ID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }
}
