package com.bajaj.bfhl.service.impl;

import com.bajaj.bfhl.config.CandidateConfig;
import com.bajaj.bfhl.dto.BfhlRequest;
import com.bajaj.bfhl.dto.BfhlResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for BfhlServiceImpl.
 */
class BfhlServiceImplTest {

    private BfhlServiceImpl service;

    @BeforeEach
    void setUp() {
        CandidateConfig config = new CandidateConfig();
        config.setEmail("test@example.com");
        config.setRollNumber("TEST123");
        service = new BfhlServiceImpl(config);
    }

    @Test
    @DisplayName("Mixed input: numbers, alphabets, special characters")
    void testProcessData_mixedInput() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("a", "1", "334", "4", "R", "$"));
        BfhlResponse response = service.processData(request);

        assertTrue(response.isIsSuccess());
        assertEquals("chhavi_joshi_17122005", response.getUserId());

        // Even numbers as strings
        assertEquals(Arrays.asList("334", "4"), response.getEvenNumbers());

        // Odd numbers as strings
        assertEquals(List.of("1"), response.getOddNumbers());

        // Alphabets uppercased
        assertEquals(Arrays.asList("A", "R"), response.getAlphabets());

        // Special characters
        assertEquals(List.of("$"), response.getSpecialCharacters());

        // Sum as string
        assertEquals("339", response.getSum());
    }

    @Test
    @DisplayName("Alphabets only: concat_string logic verification")
    void testProcessData_alphabetsOnly() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("A", "ABCD", "DOE"));
        BfhlResponse response = service.processData(request);

        assertTrue(response.isIsSuccess());
        // concat: "AABCDDOE" → reverse: "EODDCBAA" → alternating: "EoDdCbAa"
        assertEquals("EoDdCbAa", response.getConcatString());
        assertTrue(response.getEvenNumbers().isEmpty());
        assertTrue(response.getOddNumbers().isEmpty());
        assertEquals("0", response.getSum());
    }

    @Test
    @DisplayName("Empty data array should return empty lists")
    void testProcessData_emptyArray() {
        BfhlRequest request = new BfhlRequest(Collections.emptyList());
        BfhlResponse response = service.processData(request);

        assertTrue(response.isIsSuccess());
        assertTrue(response.getEvenNumbers().isEmpty());
        assertTrue(response.getOddNumbers().isEmpty());
        assertTrue(response.getAlphabets().isEmpty());
        assertTrue(response.getSpecialCharacters().isEmpty());
        assertEquals("0", response.getSum());
        assertEquals("", response.getConcatString());
    }

    @Test
    @DisplayName("Null data should return empty lists")
    void testProcessData_nullData() {
        BfhlRequest request = new BfhlRequest(null);
        BfhlResponse response = service.processData(request);

        assertTrue(response.isIsSuccess());
        assertTrue(response.getEvenNumbers().isEmpty());
        assertEquals("0", response.getSum());
        assertEquals("", response.getConcatString());
    }

    @Test
    @DisplayName("concat_string with single character")
    void testBuildConcatString_singleChar() {
        assertEquals("A", service.buildConcatString("a"));
    }

    @Test
    @DisplayName("concat_string with empty string")
    void testBuildConcatString_empty() {
        assertEquals("", service.buildConcatString(""));
    }

    @Test
    @DisplayName("Numbers only input")
    void testProcessData_numbersOnly() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("2", "3", "100"));
        BfhlResponse response = service.processData(request);

        assertEquals(Arrays.asList("2", "100"), response.getEvenNumbers());
        assertEquals(List.of("3"), response.getOddNumbers());
        assertEquals("105", response.getSum());
        assertTrue(response.getAlphabets().isEmpty());
        assertEquals("", response.getConcatString());
    }

    @Test
    @DisplayName("Special characters only")
    void testProcessData_specialCharsOnly() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("$", "@", "#", "a1"));
        BfhlResponse response = service.processData(request);

        assertEquals(Arrays.asList("$", "@", "#", "a1"), response.getSpecialCharacters());
        assertTrue(response.getEvenNumbers().isEmpty());
        assertTrue(response.getOddNumbers().isEmpty());
        assertTrue(response.getAlphabets().isEmpty());
        assertEquals("0", response.getSum());
    }
}
