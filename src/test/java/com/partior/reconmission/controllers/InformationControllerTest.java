package com.partior.reconmission.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.partior.reconmission.Fixtures;
import com.partior.reconmission.clients.StarWarsInformationClient;
import com.partior.reconmission.models.ReconInformation;
import com.partior.reconmission.services.ReconService;

public class InformationControllerTest {

    private final StarWarsInformationClient infoClient = mock(StarWarsInformationClient.class);

    private final ReconService reconService = new ReconService(infoClient);

    private final InformationController controller = new InformationController(reconService);

    @Nested
    class GetInformation {
        @BeforeEach
        void defaultSetUp() {
            Fixtures.defaultMockForSWInfoClient(infoClient);
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
                    when(infoClient.getStarshipBySearch(Fixtures.DEATH_STAR))
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
                assertEquals(Fixtures.DARTH_STARSHIP, information.getStarship());
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
                    when(infoClient.getPersonBySearch(Fixtures.DARTH_VADER))
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
                    when(infoClient.getStarship(Fixtures.STARSHIP_URI))
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
                    when(infoClient.getPersonBySearch(Fixtures.LEIA_ORGANA))
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
                    when(infoClient.getPlanet(Fixtures.PLANET_URI))
                        .thenReturn(Optional.empty());
                }
            }
        }
    }
}
