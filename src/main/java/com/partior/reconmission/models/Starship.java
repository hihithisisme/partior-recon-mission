package com.partior.reconmission.models;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Builder;
import lombok.Value;
import lombok.With;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Value
@Builder
@With
public class Starship {
    String name;

    String model;

    @JsonDeserialize(using = CrewStringToLongDeserializer.class)
    @JsonProperty(access = Access.WRITE_ONLY)
    Long crew;

    // NOTE: This allows for "starship_class" to be used for deserialization and
    // "class" for serialization
    @JsonSetter(value = "starship_class")
    String shipClass;

    @JsonGetter(value = "class")
    public String getShipClass() {
        return shipClass;
    }
    // NOTE: There is no comprehensive format for the field crew. This deserializer
    // only tackles the existing, observed formats
    static class CrewStringToLongDeserializer extends JsonDeserializer<Long> {

        @Override
        public Long deserialize(final JsonParser p, final DeserializationContext ctxt)
                throws IOException, JacksonException {
            String input = p.getText();

            // NOTE: For numeric ranges, we take the higher bound
            if (input.contains("-")) {
                input = input.split("-")[1];
            }

            try {
                return ((Long) NumberFormat.getInstance(Locale.ENGLISH).parse(input).longValue());
            } catch (final ParseException e) {
                throw new JsonParseException(p, "Unexpected format for crew attribute", e);
            }
        }
    }
}