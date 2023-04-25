package com.partior.reconmission.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.partior.reconmission.clients.StarWarsInformationClient;
import com.partior.reconmission.models.Person;
import com.partior.reconmission.models.Planet;
import com.partior.reconmission.models.ReconInformation;
import com.partior.reconmission.models.Starship;
import com.partior.reconmission.services.ReconService;

public class InformationControllerTest {

    private final StarWarsInformationClient infoClient = mock(StarWarsInformationClient.class);

    private final ReconService reconService = new ReconService(infoClient);

    private final InformationController controller = new InformationController(reconService);

    private static final String LEIA_ORGANA = "Leia Organa";
    private static final String DARTH_VADER = "Darth Vader";
    private static final String DEATH_STAR = "Death Star";
    private static final String ALDERAAN = "Alderaan";

    private static final URI STARSHIP_URI = URI.create("https://example.com/starship");
    private static final URI PLANET_URI = URI.create("https://example.com/planet");

    private final Starship darthStarship = Starship.builder()
            .name("Darth's starhips")
            .shipClass("starship")
            .model("starship")
            .crew(1L)
            .build();

    @Nested
    class GetInformation {
        @BeforeEach
        void defaultSetUp() {
            when(infoClient.getStarshipBySearch(DEATH_STAR))
                        .thenReturn(Optional.of(Starship.builder()
                                .name(DEATH_STAR)
                                .crew(10293L)
                                .build()));
            when(infoClient.getPersonBySearch(DARTH_VADER))
                .thenReturn(Optional.of(Person.builder()
                    .name(DARTH_VADER)
                    .starships(Arrays.asList(STARSHIP_URI))
                    .homeworld(null)
                    .build()));
            when(infoClient.getPersonBySearch(LEIA_ORGANA))
                .thenReturn(Optional.of(Person.builder()
                    .name(LEIA_ORGANA)
                    .starships(Arrays.asList())
                    .homeworld(PLANET_URI)
                    .build()));

            when(infoClient.getStarship(STARSHIP_URI))
                .thenReturn(Optional.of(darthStarship));
            when(infoClient.getPlanet(PLANET_URI))
                .thenReturn(Optional.of(Planet.builder().name(ALDERAAN).build()));
        }

        @Nested
        class GetDeathStarCrewSize {

            @Test
            void returnCrewValue() {
                final ReconInformation information = controller.getInformation();
                assertEquals(10293L, information.getCrew());
            }

            @Nested
            class WhenDeathStarNotFound {
                @BeforeEach
                void setUp() {
                    when(infoClient.getStarshipBySearch(DEATH_STAR))
                        .thenReturn(Optional.empty());
                }

                @Test
                void returnZero() {
                    final ReconInformation information = controller.getInformation();
                    assertEquals(0L, information.getCrew());
                }
            }
        }

        @Nested
        class GetDarthVaderStarship {

            @Test
            void returnStarship() {
                final ReconInformation information = controller.getInformation();
                assertEquals(darthStarship, information.getStarship());
            }

            @Nested
            class WhenDarthVaderNotFound {

                @Test
                void returnNull() {
                    final ReconInformation information = controller.getInformation();
                    assertEquals(null, information.getStarship());
                }

                @BeforeEach
                void setUp() {
                    when(infoClient.getPersonBySearch(DARTH_VADER))
                        .thenReturn(Optional.empty());
                }
            }

            @Nested
            class WhenURINotFound {
                @Test
                void returnNull() {
                    final ReconInformation information = controller.getInformation();
                    assertEquals(null, information.getStarship());
                }

                @BeforeEach
                void setUp() {
                    when(infoClient.getStarship(STARSHIP_URI))
                        .thenReturn(Optional.empty());
                }
            }

        }

        @Nested
        class GetIsLeiaOnAlderaan {
            @Test
            void returnTrue() {
                final ReconInformation information = controller.getInformation();
                assertTrue(information.isLeiaOnPlanet());
            }

            @Nested
            class WhenLeiaOrganaNotFound {

                @Test
                void returnNull() {
                    final ReconInformation information = controller.getInformation();
                    assertFalse(information.isLeiaOnPlanet());
                }

                @BeforeEach
                void setUp() {
                    when(infoClient.getPersonBySearch(LEIA_ORGANA))
                        .thenReturn(Optional.empty());
                }
            }

            @Nested
            class WhenURINotFound {
                @Test
                void returnNull() {
                    final ReconInformation information = controller.getInformation();
                    assertFalse(information.isLeiaOnPlanet());
                }

                @BeforeEach
                void setUp() {
                    when(infoClient.getPlanet(PLANET_URI))
                        .thenReturn(Optional.empty());
                }
            }
        }
    }
}
