package com.partior.reconmission.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StarshipTest {

    private final ObjectMapper mapper = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    private final static String STARSHIP_ONE_PAYLOAD = "{\"name\":\"CR90 corvette\",\"model\":\"CR90 corvette\",\"crew\":\"30-165\",\"passengers\":\"600\",\"starship_class\":\"corvette\",\"pilots\":[],\"created\":\"2014-12-10T14:20:33.369000Z\",\"edited\":\"2014-12-20T21:23:49.867000Z\",\"url\":\"https://swapi.dev/api/starships/2/\"}";
    private final static Starship STARSHIP_ONE = Starship.builder()
            .crew(165L)
            .name("CR90 corvette")
            .model("CR90 corvette")
            .shipClass("corvette")
            .build();

    private final static String STARSHIP_TWO_PAYLOAD = "{\"name\":\"Star Destroyer\",\"model\":\"Imperial I-class Star Destroyer\",\"crew\":\"47,060\",\"passengers\":\"n/a\",\"starship_class\":\"Star Destroyer\",\"pilots\":[],\"created\":\"2014-12-10T15:08:19.848000Z\",\"edited\":\"2014-12-20T21:23:49.870000Z\",\"url\":\"https://swapi.dev/api/starships/3/\"}";
    private final static Starship STARSHIP_TWO = Starship.builder()
            .crew(47060L)
            .name("Star Destroyer")
            .model("Imperial I-class Star Destroyer")
            .shipClass("Star Destroyer")
            .build();

    @Test
    void whenGivenRangeOfCrewSize() throws JsonMappingException, JsonProcessingException {
        final Starship response = mapper.readValue(STARSHIP_ONE_PAYLOAD, Starship.class);
        assertEquals(STARSHIP_ONE, response);
    }

    @Test
    void whenGivenCommaSeparatedCrewValue() throws JsonMappingException, JsonProcessingException {
        final Starship response = mapper.readValue(STARSHIP_TWO_PAYLOAD, Starship.class);
        assertEquals(STARSHIP_TWO, response);
    }
}
