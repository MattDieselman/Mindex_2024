package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class CompensationController {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);


    @Autowired
    private CompensationService compensationService;


    /*
     * I would hope that this is being created by another owned service
     * that already has the entire employee object?
     * Assuming this is the case we are creating based off the employee OBJECT
     * rather than another form of unique identifier such as the UUID itself
     * If we were allowing creation via something like a web portal where
     * the uuid might be all that we have (but know the user is authenticated)
     * we could adjust this to run off of just that value.
     */
    @PostMapping("/compensation")
    public Compensation create(@RequestBody Compensation compensation) {
        LOG.debug("Received employee create request for [{}]", compensation);

        return compensationService.create(compensation);
    }


    @GetMapping("/compensation/{id}")
    public Compensation read(@PathVariable String id) {
        LOG.debug("Received employee create request for id [{}]", id);

        return compensationService.read(id);
    }
}
