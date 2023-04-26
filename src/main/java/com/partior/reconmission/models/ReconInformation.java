package com.partior.reconmission.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Value
@Builder
public class ReconInformation {
    Starship starship;
    long crew;

    @JsonProperty
    boolean isLeiaOnPlanet;

    // NOTE: explicitly defining the getter despite using @Value in order to remove
    // duplicate fields caused by quirks around boolean fields with is- prefix
    @JsonProperty(value = "isLeiaOnPlanet")
    public boolean isLeiaOnPlanet() {
        return isLeiaOnPlanet;
    }
}
