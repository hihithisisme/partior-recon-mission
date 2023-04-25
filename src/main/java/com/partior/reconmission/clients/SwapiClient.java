package com.partior.reconmission.clients;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.partior.reconmission.models.Person;
import com.partior.reconmission.models.Planet;
import com.partior.reconmission.models.Starship;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

// @AllArgsConstructor
@Component
public class SwapiClient implements StarWarsInformationClient {
    private final String baseUrl;

    @Autowired
    private final RestTemplate restTemplate;

    public static final ParameterizedTypeReference<PagedResult<Starship>> PAGED_STARSHIP_CLASS = new ParameterizedTypeReference<PagedResult<Starship>>() {
    };
    public static final ParameterizedTypeReference<PagedResult<Person>> PAGED_PERSON_CLASS = new ParameterizedTypeReference<PagedResult<Person>>() {
    };

    public SwapiClient(@Value("${swapi.hostname}") final String baseUrl, @Autowired final RestTemplate restTemplate) {
        this.baseUrl = baseUrl;
        this.restTemplate = restTemplate;
    }

    @Override
    public Optional<Starship> getStarshipBySearch(final String name) {
        final URI uri = buildSearchUri("starships", name);

        final ResponseEntity<PagedResult<Starship>> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                PAGED_STARSHIP_CLASS);

        return response.getBody().results.stream().findFirst();
    }

    @Override
    public Optional<Starship> getStarship(final URI uri) {
        final ResponseEntity<Starship> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                Starship.class);

        return Optional.of(response.getBody());
    }

    @Override
    public Optional<Planet> getPlanet(final URI uri) {
        final ResponseEntity<Planet> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                Planet.class);

        return Optional.of(response.getBody());
    }

    @Override
    public Optional<Person> getPersonBySearch(final String name) {
        final URI uri = buildSearchUri("people", name);

        final ResponseEntity<PagedResult<Person>> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                PAGED_PERSON_CLASS);

        return response.getBody().results.stream().findFirst();
    }

    private URI buildSearchUri(final String searchResource, final String searchParam) {
        return UriComponentsBuilder.fromHttpUrl(baseUrl)
                .pathSegment(searchResource)
                .queryParam("search", searchParam)
                .build(false).toUri();
    }

    @Jacksonized
    @lombok.Value
    @Builder
    static class PagedResult<T> {
        int count;
        // next and previous should actually be URI, but our use case do not require
        // them for now
        String next;
        String previous;
        List<T> results;
    }
}
