package com.partior.reconmission.clients;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.partior.reconmission.clients.SwapiClient.PagedResult;
import com.partior.reconmission.models.Person;
import com.partior.reconmission.models.Starship;

public class SwapiClientTests {
    private final RestTemplate rest = mock(RestTemplate.class);
    private final SwapiClient client = new SwapiClient("https://example.com", rest);

    @Nested
    class GetStarship {
        @Nested
        class whenNotFound {
            final PagedResult<Starship> notFoundResult = SwapiClient.PagedResult.<Starship>builder()
                    .count(0)
                    .next(null)
                    .previous(null)
                    .results(Arrays.asList())
                    .build();

            @BeforeEach
            void beforeEach() {
                when(rest.exchange(any(), any(), any(), eq(SwapiClient.PAGED_STARSHIP_CLASS)))
                        .thenReturn(ResponseEntity.ok().body(notFoundResult));
            }

            @Test
            void returnEmptyOptional() {
                final Optional<Starship> response = client.getStarshipBySearch("notReal");

                verify(rest).exchange(URI.create("https://example.com/starships?search=notReal"),
                        HttpMethod.GET, null,
                        SwapiClient.PAGED_STARSHIP_CLASS);

                assertEquals(Optional.empty(), response);
            }
        }

        @Nested
        class whenFound {
            private final Starship starship = Starship.builder().crew(123141L).build();
            final PagedResult<Starship> foundResult = SwapiClient.PagedResult.<Starship>builder()
                    .count(1)
                    .next(null)
                    .previous(null)
                    .results(Arrays.asList(starship))
                    .build();

            @BeforeEach
            void beforeEach() {
                when(rest.exchange(any(), any(), any(), eq(SwapiClient.PAGED_STARSHIP_CLASS)))
                        .thenReturn(ResponseEntity.ok().body(foundResult));
            }

            @Test
            void returnOptionalStarship() {
                final Optional<Starship> response = client.getStarshipBySearch("Death Star");

                verify(rest).exchange(URI.create("https://example.com/starships?search=Death%20Star"),
                        HttpMethod.GET, null,
                        SwapiClient.PAGED_STARSHIP_CLASS);

                assertEquals(Optional.of(starship), response);
            }
        }
    }

    @Nested
    class GetPerson {
        @Nested
        class whenNotFound {
            final PagedResult<Person> notFoundResult = SwapiClient.PagedResult.<Person>builder()
                    .count(0)
                    .next(null)
                    .previous(null)
                    .results(Arrays.asList())
                    .build();

            @BeforeEach
            void beforeEach() {
                when(rest.exchange(any(), any(), any(), eq(SwapiClient.PAGED_PERSON_CLASS)))
                        .thenReturn(ResponseEntity.ok().body(notFoundResult));
            }

            @Test
            void returnEmptyOptional() {
                final Optional<Person> response = client.getPersonBySearch("notReal");

                verify(rest).exchange(URI.create("https://example.com/people?search=notReal"),
                        HttpMethod.GET, null,
                        SwapiClient.PAGED_PERSON_CLASS);

                assertEquals(Optional.empty(), response);
            }
        }

        @Nested
        class whenFound {
            private final Person darthVader = Person.builder().name("Darth Vader").build();
            final PagedResult<Person> foundResult = SwapiClient.PagedResult.<Person>builder()
                    .count(1)
                    .next(null)
                    .previous(null)
                    .results(Arrays.asList(darthVader))
                    .build();

            @BeforeEach
            void beforeEach() {
                when(rest.exchange(any(), any(), any(), eq(SwapiClient.PAGED_PERSON_CLASS)))
                        .thenReturn(ResponseEntity.ok().body(foundResult));
            }

            @Test
            void returnOptionalPerson() {
                final Optional<Person> response = client.getPersonBySearch("Darth Vader");

                verify(rest).exchange(URI.create("https://example.com/people?search=Darth%20Vader"),
                        HttpMethod.GET, null,
                        SwapiClient.PAGED_PERSON_CLASS);

                assertEquals(Optional.of(darthVader), response);
            }
        }
    }
}
