package com.partior.reconmission.models;

import java.net.URI;
import java.util.List;

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
}
