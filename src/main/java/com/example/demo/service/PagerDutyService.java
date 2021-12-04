package com.example.demo.service;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.CreateTeamRequest;
import com.fasterxml.jackson.databind.JsonNode;

public interface PagerDutyService {

    CreateTeamRequest createTeam(CreateTeamRequest request) throws BadRequestException;

    public JsonNode receiveAlert(Long teamId, String message) throws BadRequestException;
}
