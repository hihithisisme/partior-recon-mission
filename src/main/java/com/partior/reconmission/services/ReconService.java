package com.partior.reconmission.services;

import org.springframework.stereotype.Service;

import com.partior.reconmission.models.ReconInformation;
import com.partior.reconmission.models.Starship;

@Service
public class ReconService {
    public ReconInformation getInformation() {
        final Starship startship = Starship.builder()
                .shipClass("class")
                .model("model")
                .name("name")
                .build();

        return ReconInformation.builder()
                .crew(12980)
                .isLeiaOnPlanet(true)
                .starship(startship)
                .build();
    }
}
