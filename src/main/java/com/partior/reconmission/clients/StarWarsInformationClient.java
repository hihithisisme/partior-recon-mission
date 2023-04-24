package com.partior.reconmission.clients;

import java.util.Optional;

import com.partior.reconmission.models.Person;
import com.partior.reconmission.models.Starship;

public interface StarWarsInformationClient {

    public Optional<Starship> getStarship(String name);

    public Optional<Person> getPerson(String name);
}
