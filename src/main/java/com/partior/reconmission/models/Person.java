package com.partior.reconmission.models;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.partior.reconmission.clients.StarWarsInformationClient;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Value
@Builder
public class Person {
    String name;
    List<URI> starships;
    URI homeworld;

    public List<Starship> expandStarships(final StarWarsInformationClient swClient) {
        final List<Starship> response = new ArrayList<Starship>(starships.size());
        for (int i = 0; i < starships.size(); i++) {
            swClient.getStarship(starships.get(i)).ifPresent(starship -> response.add(starship));
        }

        return response;
    }

    public Optional<Planet> expandHomeworld(final StarWarsInformationClient swClient) {
        return swClient.getPlanet(homeworld);
    }
}
