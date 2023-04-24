package com.partior.reconmission.models;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ReconInformation {
    Starship starship;
    int crew;
    boolean isLeiaOnPlanet;
}
