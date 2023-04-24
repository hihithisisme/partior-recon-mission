package com.partior.reconmission.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.partior.reconmission.models.ReconInformation;
import com.partior.reconmission.services.ReconService;

@RestController
@RequestMapping("/information")
public class InformationController {
    @Autowired
    private ReconService reconService;

    @GetMapping
    public ReconInformation getInformation() {
        return reconService.getInformation();
    }
}
