package com.partior.reconmission.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.partior.reconmission.Fixtures;
import com.partior.reconmission.clients.StarWarsInformationClient;
import com.partior.reconmission.models.ReconInformation;

@Tag("integration")
@SpringBootTest
@AutoConfigureMockMvc
public class InformationControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    StarWarsInformationClient infoClient;

    @Nested
    class WhenInformationClientIsUnavailable {

        @Test
        void returnDefaultResponse() throws Exception {
            final ReconInformation expected = ReconInformation.builder()
                    .crew(0L)
                    .starship(null)
                    .isLeiaOnPlanet(false)
                    .build();

            final ReconInformation response = mapper.readValue(
                    mockMvc.perform(get("/information"))
                            .andDo(print())
                            .andExpect(status().isOk())
                            .andReturn().getResponse().getContentAsString(),
                    ReconInformation.class);

            assertEquals(expected, response);
        }

        @BeforeEach
        void setUp() {
            when(infoClient.getPersonBySearch(any())).thenReturn(Optional.empty());
            when(infoClient.getStarshipBySearch(any())).thenReturn(Optional.empty());
        }
    }

    @BeforeEach
    void setUp() {
        Fixtures.defaultMockForSWInfoClient(infoClient);
    }

    @Test
    void returnResponse() throws Exception {
        final ReconInformation expected = ReconInformation.builder()
                .crew(Fixtures.DEATH_STAR_CREW)
                .starship(Fixtures.DARTH_STARSHIP.withCrew(null))
                .isLeiaOnPlanet(true)
                .build();

        final ReconInformation response = mapper.readValue(
                mockMvc.perform(get("/information"))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andReturn().getResponse().getContentAsString(),
                ReconInformation.class);

        assertEquals(expected, response);
    }
}
