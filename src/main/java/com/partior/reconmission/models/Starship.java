package com.partior.reconmission.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Starship {
    String name;
    @JsonProperty("class")
    String shipClass;
    String model;
}