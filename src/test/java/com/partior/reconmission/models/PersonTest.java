package com.partior.reconmission.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.partior.reconmission.clients.StarWarsInformationClient;

public class PersonTest {
        private static List<URI> starshipURIs = buildStarshipURIs();

        private final StarWarsInformationClient client = mock(StarWarsInformationClient.class);

        final Person person = Person.builder()
                        .name("Luke Skywalker")
                        .homeworld(URI.create("https://swapi.dev/api/planets/1/"))
                        .starships(starshipURIs).build();

        final Starship starship1 = Starship.builder()
                        .crew(1L)
                        .name("starship1")
                        .model("starship1")
                        .shipClass("starship1")
                        .build();
        final Starship starship2 = Starship.builder()
                        .crew(2L)
                        .name("starship2")
                        .model("starship2")
                        .shipClass("starship2")
                        .build();

        @Test
        void testExpandStarships() {
                when(client.getStarship(any())).thenReturn(
                                Optional.of(starship1), Optional.of(starship2));

                final List<Starship> response = person.expandStarships(client);

                assertEquals(starship1, response.get(0));
                assertEquals(starship2, response.get(1));
        }

        private static List<URI> buildStarshipURIs() {
                final List<URI> uris = new ArrayList<URI>();
                uris.add(URI.create("https://swapi.dev/api/starships/12/"));
                uris.add(URI.create("https://swapi.dev/api/starships/22/"));
                return uris;
        }
}
