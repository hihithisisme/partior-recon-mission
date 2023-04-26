package com.partior.reconmission;

import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.Arrays;
import java.util.Optional;

import com.partior.reconmission.clients.StarWarsInformationClient;
import com.partior.reconmission.models.Person;
import com.partior.reconmission.models.Planet;
import com.partior.reconmission.models.Starship;

public class Fixtures {
    public static final long DEATH_STAR_CREW = 10293L;
    public static final String LEIA_ORGANA = "Leia Organa";
    public static final String DARTH_VADER = "Darth Vader";
    public static final String DEATH_STAR = "Death Star";
    public static final String ALDERAAN = "Alderaan";
    public static final URI STARSHIP_URI = URI.create("https://example.com/starship");
    public static final URI PLANET_URI = URI.create("https://example.com/planet");
    public static final Starship DARTH_STARSHIP = Starship.builder()
            .name("Darth's starhips")
            .shipClass("starship")
            .model("starship")
            .crew(1L)
            .build();

    public static void defaultMockForSWInfoClient(final StarWarsInformationClient infoClient) {
                when(infoClient.getStarshipBySearch(Fixtures.DEATH_STAR))
                            .thenReturn(Optional.of(Starship.builder()
                                    .name(Fixtures.DEATH_STAR)
                                    .crew(Fixtures.DEATH_STAR_CREW)
                                    .build()));
                when(infoClient.getPersonBySearch(Fixtures.DARTH_VADER))
                    .thenReturn(Optional.of(Person.builder()
                        .name(Fixtures.DARTH_VADER)
                        .starships(Arrays.asList(Fixtures.STARSHIP_URI))
                        .homeworld(null)
                        .build()));
                when(infoClient.getPersonBySearch(Fixtures.LEIA_ORGANA))
                    .thenReturn(Optional.of(Person.builder()
                        .name(Fixtures.LEIA_ORGANA)
                        .starships(Arrays.asList())
                        .homeworld(Fixtures.PLANET_URI)
                        .build()));
            
                when(infoClient.getStarship(Fixtures.STARSHIP_URI))
                    .thenReturn(Optional.of(Fixtures.DARTH_STARSHIP));
                when(infoClient.getPlanet(Fixtures.PLANET_URI))
                    .thenReturn(Optional.of(Planet.builder().name(Fixtures.ALDERAAN).build()));
            }
}
