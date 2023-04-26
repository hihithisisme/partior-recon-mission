package com.partior.reconmission.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.partior.reconmission.Fixtures;
import com.partior.reconmission.clients.StarWarsInformationClient;

@Tag("integration")
@SpringBootTest
@AutoConfigureMockMvc
public class InformationControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    StarWarsInformationClient infoClient;

    @Nested
    class WhenInformationClientIsUnavailable {

        @Test
        void returnDefaultResponse() throws Exception {
            mockMvc.perform(get("/information"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.crew").value(0L))
                    .andExpect(jsonPath("$.starship", Matchers.nullValue()))
                    .andExpect(jsonPath("$.isLeiaOnPlanet").value(false))
                    .andReturn().getResponse().getContentAsString();
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
    void returnResponseWithExactFieldNames() throws Exception {
        mockMvc.perform(get("/information"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.crew").value(Fixtures.DEATH_STAR_CREW))

                .andExpect(jsonPath("$.isLeiaOnPlanet").value(true))
                .andExpect(jsonPath("$.leiaOnPlanet").doesNotExist())

                .andExpect(jsonPath("$.starship.name").value(Fixtures.DARTH_STARSHIP.getName()))
                .andExpect(jsonPath("$.starship.model").value(Fixtures.DARTH_STARSHIP.getModel()))
                .andExpect(jsonPath("$.starship.class").value(Fixtures.DARTH_STARSHIP.getShipClass()))
                .andExpect(jsonPath("$.starship.starship_class").doesNotExist())
                .andExpect(jsonPath("$.starship.shipClass").doesNotExist())
                .andExpect(jsonPath("$.starship.crew").doesNotExist())
                .andReturn().getResponse().getContentAsString();
    }
}
