package com.bajaj.bfhl.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration test for BfhlController using full Spring context.
 */
@SpringBootTest
@AutoConfigureMockMvc
class BfhlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("POST /bfhl with valid mixed data returns 200")
    void testPostBfhl_validRequest() throws Exception {
        String requestBody = """
                {"data": ["a","1","334","4","R","$"]}
                """;

        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success").value(true))
                .andExpect(jsonPath("$.user_id").value("chhavi_joshi_17122005"))
                .andExpect(jsonPath("$.sum").value("339"))
                .andExpect(jsonPath("$.even_numbers[0]").value("334"))
                .andExpect(jsonPath("$.odd_numbers[0]").value("1"))
                .andExpect(jsonPath("$.alphabets[0]").value("A"))
                .andExpect(jsonPath("$.special_characters[0]").value("$"));
    }

    @Test
    @DisplayName("POST /bfhl with empty data returns 200")
    void testPostBfhl_emptyData() throws Exception {
        String requestBody = """
                {"data": []}
                """;

        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success").value(true))
                .andExpect(jsonPath("$.sum").value("0"));
    }

    @Test
    @DisplayName("POST /bfhl with invalid payload returns safe response with candidate details")
    void testPostBfhl_invalidPayload() throws Exception {
        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("invalid json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success").value(false))
                .andExpect(jsonPath("$.user_id").value("chhavi_joshi_17122005"))
                .andExpect(jsonPath("$.email").value("chhavijoshi230275@acropolis.in"))
                .andExpect(jsonPath("$.roll_number").value("0827IT231037"));
    }

    @Test
    @DisplayName("POST /bfhl verifies concat_string logic")
    void testPostBfhl_concatString() throws Exception {
        String requestBody = """
                {"data": ["A","ABCD","DOE"]}
                """;

        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.concat_string").value("EoDdCbAa"));
    }

    @Test
    @DisplayName("GET /bfhl returns 200 with operation_code 1")
    void testGetBfhl() throws Exception {
        mockMvc.perform(get("/bfhl"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operation_code").value(1));
    }
}
