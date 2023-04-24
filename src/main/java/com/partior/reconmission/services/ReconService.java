package com.partior.reconmission.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.partior.reconmission.clients.StarWarsInformationClient;
import com.partior.reconmission.models.Person;
import com.partior.reconmission.models.ReconInformation;
import com.partior.reconmission.models.Starship;

@Service
public class ReconService {
    private static final String DEATH_STAR = "Death Star";
    private static final String DARTH_VADER = "Darth Vader";
    private static final String LEIA_ORGANA = "Leia Organa";
    @Autowired
    private StarWarsInformationClient infoClient;

    public ReconInformation getInformation() {
        final Optional<Starship> deathStar = infoClient.getStarship(DEATH_STAR);
        final Optional<Person> darthVader = infoClient.getPerson(DARTH_VADER);
        final Optional<Person> leiaOrgana = infoClient.getPerson(LEIA_ORGANA);

        // TODO: mocked data for the time being
        return ReconInformation.builder()
                .crew(deathStar.map(Starship::getCrew).orElse(0L))
                .isLeiaOnPlanet(true)
                .starship(Starship.builder().build())
                .build();
    }
}
