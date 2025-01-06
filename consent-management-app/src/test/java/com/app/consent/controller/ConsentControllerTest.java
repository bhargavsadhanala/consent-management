package com.app.consent.controller;

import com.app.auth.filter.JwtAuthenticationFilter;
import com.app.consent.dto.ConsentRequestDTO;
import com.app.consent.dto.ConsentResponseDTO;
import com.app.consent.entity.ConsentStatus;
import com.app.consent.service.ConsentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class ConsentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ConsentService consentService;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;



    @Test
    void shouldCreateConsent() throws Exception {
        //given
        ConsentRequestDTO request = new ConsentRequestDTO(1L, 1L, "GRANTED");
        ConsentResponseDTO response = new ConsentResponseDTO(1L, 1L, ConsentStatus.GRANTED, LocalDateTime.now());

        //when
        when(consentService.createConsent(any(ConsentRequestDTO.class))).thenReturn(response);

        //then
        mockMvc.perform(post("/api/v1/consents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.consentStatus").value("GRANTED"));
    }

    @Test
    void shouldRetrieveConsents() throws Exception {
        //given
        List<ConsentResponseDTO> response = List.of(
                new ConsentResponseDTO(1L, 1L, ConsentStatus.GRANTED, LocalDateTime.now()));

        //when
        when(consentService.retrieveConsents(1L)).thenReturn(response);

        //then
        mockMvc.perform(get("/api/v1/consents")
                        .param("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value(1L));
    }

    @Test
    void shouldUpdateConsent() throws Exception {
        //given
        ConsentRequestDTO request = new ConsentRequestDTO(1L, 1L, "REVOKED");
        ConsentResponseDTO response =
                new ConsentResponseDTO(1L, 1L, ConsentStatus.REVOKED, LocalDateTime.now());

        //when
        when(consentService.updateConsent(any(ConsentRequestDTO.class))).thenReturn(response);

        //then
        mockMvc.perform(put("/api/v1/consents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.consentStatus").value("REVOKED"));
    }

    @Test
    void shouldDeleteConsent() throws Exception {
        //when and then
        mockMvc.perform(delete("/api/v1/consents")
                        .param("userId", "1")
                        .param("purposeId", "1"))
                .andExpect(status().isNoContent());
    }

}
