package com.bajaj.bfhl.service.impl;

import com.bajaj.bfhl.config.CandidateConfig;
import com.bajaj.bfhl.dto.BfhlRequest;
import com.bajaj.bfhl.dto.BfhlResponse;
import com.bajaj.bfhl.service.BfhlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of BfhlService that processes input data array.
 */
@Service
public class BfhlServiceImpl implements BfhlService {

    private static final Logger logger = LoggerFactory.getLogger(BfhlServiceImpl.class);

    private final CandidateConfig candidateConfig;

    public BfhlServiceImpl(CandidateConfig candidateConfig) {
        this.candidateConfig = candidateConfig;
    }

    @Override
    public BfhlResponse processData(BfhlRequest request) {
        logger.info("Processing started for data array");

        List<String> data = request.getData();
        if (data == null) {
            data = Collections.emptyList();
        }

        List<String> evenNumbers = new ArrayList<>();
        List<String> oddNumbers = new ArrayList<>();
        List<String> alphabets = new ArrayList<>();
        List<String> specialCharacters = new ArrayList<>();
        long sum = 0;
        StringBuilder alphabetChars = new StringBuilder();

        for (String item : data) {
            if (item == null || item.isEmpty()) {
                continue;
            }

            if (isNumeric(item)) {
                // It's a number
                long numValue = Long.parseLong(item);
                sum += numValue;
                if (numValue % 2 == 0) {
                    evenNumbers.add(item);
                } else {
                    oddNumbers.add(item);
                }
            } else if (isAlphabetic(item)) {
                // It's alphabetical — convert to uppercase
                alphabets.add(item.toUpperCase());
                // Collect individual characters for concat_string
                alphabetChars.append(item);
            } else {
                // Special character (anything that's not purely numeric or purely alphabetic)
                specialCharacters.add(item);
            }
        }

        // Build concat_string: concatenate all alpha chars, reverse, alternating caps
        String concatString = buildConcatString(alphabetChars.toString());

        logger.info("Processing completed — evenNumbers={}, oddNumbers={}, alphabets={}, specialChars={}, sum={}",
                evenNumbers.size(), oddNumbers.size(), alphabets.size(), specialCharacters.size(), sum);

        BfhlResponse response = new BfhlResponse();
        response.setIsSuccess(true);
        response.setUserId(candidateConfig.getUserId());
        response.setEmail(candidateConfig.getEmail());
        response.setRollNumber(candidateConfig.getRollNumber());
        response.setEvenNumbers(evenNumbers);
        response.setOddNumbers(oddNumbers);
        response.setAlphabets(alphabets);
        response.setSpecialCharacters(specialCharacters);
        response.setSum(String.valueOf(sum));
        response.setConcatString(concatString);

        logger.info("Response generated successfully");
        return response;
    }

    /**
     * Check if string is purely numeric (handles negative numbers too).
     */
    private boolean isNumeric(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Check if string is purely alphabetic.
     */
    private boolean isAlphabetic(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Build concat_string:
     * 1. Take all alphabetical characters from the concatenated alpha elements
     * 2. Reverse the string
     * 3. Apply alternating caps (index 0 = uppercase, index 1 = lowercase, ...)
     *
     * Example: "AABCDDOE" → reverse → "EODDCBAA" → alternating → "EoDdCbAa"
     */
    String buildConcatString(String alphaConcat) {
        if (alphaConcat == null || alphaConcat.isEmpty()) {
            return "";
        }

        // Reverse the concatenated string
        String reversed = new StringBuilder(alphaConcat).reverse().toString();

        // Apply alternating caps
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < reversed.length(); i++) {
            char c = reversed.charAt(i);
            if (i % 2 == 0) {
                result.append(Character.toUpperCase(c));
            } else {
                result.append(Character.toLowerCase(c));
            }
        }

        return result.toString();
    }
}
