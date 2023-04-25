package com.partior.reconmission.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.partior.reconmission.models.ReconInformation;
import com.partior.reconmission.services.ReconService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/information")
@AllArgsConstructor
public class InformationController {
    @Autowired
    private final ReconService reconService;

    @GetMapping
    public ReconInformation getInformation() {
        return reconService.getInformation();
    }
}
