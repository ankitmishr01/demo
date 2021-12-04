package com.example.demo.controller;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.CreateTeamRequest;
import com.example.demo.service.PagerDutyService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class PagerDutyController {

    @Autowired
    PagerDutyService pagerDutyService;


    @RequestMapping(value = "/v1/team", method = RequestMethod.POST)
    public ResponseEntity<CreateTeamRequest> createTeam(@RequestBody @Valid CreateTeamRequest request) throws BadRequestException {
        return new ResponseEntity<>(pagerDutyService.createTeam(request), HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/team/{teamId}/notify", method = RequestMethod.POST)
    public ResponseEntity<JsonNode> alertTeam(@PathVariable(value = "teamId") final Long teamId, @RequestBody String message) throws BadRequestException {
        return new ResponseEntity<>(pagerDutyService.receiveAlert(teamId, message), HttpStatus.OK);
    }
}
