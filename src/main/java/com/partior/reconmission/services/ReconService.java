package com.partior.reconmission.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.partior.reconmission.clients.StarWarsInformationClient;
import com.partior.reconmission.models.Person;
import com.partior.reconmission.models.ReconInformation;
import com.partior.reconmission.models.Starship;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReconService {
    private static final String DEATH_STAR = "Death Star";
    private static final String DARTH_VADER = "Darth Vader";
    private static final String LEIA_ORGANA = "Leia Organa";
    @Autowired
    private final StarWarsInformationClient infoClient;

    public ReconInformation getInformation() {
        return ReconInformation.builder()
                .crew(getDeathStarCrewSize())
                .starship(getDarthVaderStarship())
                .isLeiaOnPlanet(getIsLeiaOnAlderaan())
                .build();
    }

    private boolean getIsLeiaOnAlderaan() {
        final Optional<Person> leiaOrgana = infoClient.getPersonBySearch(LEIA_ORGANA);

        return false;
    }

    private Starship getDarthVaderStarship() {
        final Optional<Person> darthVader = infoClient.getPersonBySearch(DARTH_VADER);
        final Optional<Starship> darthVaderShip = darthVader
                .map(p -> p.expandStarships(infoClient).stream().findFirst()).orElseGet(Optional::empty);
        return darthVaderShip.orElseGet(() -> null);
    }

    private long getDeathStarCrewSize() {
        final Optional<Starship> deathStar = infoClient.getStarshipBySearch(DEATH_STAR);
        return deathStar.map(Starship::getCrew).orElse(0L);
    }
}
