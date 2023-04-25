package com.partior.reconmission.clients;

import java.net.URI;
import java.util.Optional;

import com.partior.reconmission.models.Person;
import com.partior.reconmission.models.Planet;
import com.partior.reconmission.models.Starship;

public interface StarWarsInformationClient {

    public Optional<Starship> getStarshipBySearch(String name);

    public Optional<Starship> getStarship(URI uri);

    public Optional<Person> getPersonBySearch(String name);

    public Optional<Planet> getPlanet(URI uri);
}
