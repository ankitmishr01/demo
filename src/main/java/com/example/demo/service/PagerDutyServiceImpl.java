package com.example.demo.service;

import com.example.demo.enitity.DeveloperEntity;
import com.example.demo.enitity.TeamEntity;
import com.example.demo.exception.BadRequestException;
import com.example.demo.helper.NotificationHelper;
import com.example.demo.model.CreateTeamRequest;
import com.example.demo.model.Developer;
import com.example.demo.model.NotificationRequest;
import com.example.demo.repository.DeveloperRepository;
import com.example.demo.repository.TeamRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class PagerDutyServiceImpl implements PagerDutyService {

    private static Logger logger = LoggerFactory.getLogger(NotificationHelper.class);


    @Autowired
    DeveloperRepository developerRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    NotificationHelper notificationHelper;

    @Override
    @Transactional(rollbackOn = BadRequestException.class)
    public CreateTeamRequest createTeam(CreateTeamRequest request) throws BadRequestException {

        TeamEntity teamEntity = new TeamEntity();

        teamEntity.setName(request.getTeam().getName());

        try {
            teamEntity = teamRepository.save(teamEntity);

            BeanUtils.copyProperties(teamEntity, request.getTeam());

            for (Developer developer : request.getDevelopers()) {
                DeveloperEntity developerEntity = new DeveloperEntity();

                developerEntity.setName(developer.getName());
                developerEntity.setPhoneNumber(developer.getPhoneNumber());
                developerEntity.setTeamId(teamEntity);

                developerEntity.setLastCalled(new Date(System.currentTimeMillis() +1));
                developerEntity = developerRepository.save(developerEntity);

                BeanUtils.copyProperties(developerEntity, developer);
            }

        } catch (Exception e){
            throw new BadRequestException("UNABLE_TO_PERSIST","UNABLE_TO_PERSIST");
        }
        logger.info("Created Team - {}", teamEntity.getId());

        return request;
    }

    @Override
    public JsonNode receiveAlert(Long teamId, String message) throws BadRequestException {

        Optional<TeamEntity> teamEntityOptional = teamRepository.findById(teamId);
        if(!teamEntityOptional.isPresent()){
            throw new BadRequestException("NO_TEAM_FOUND","NO DEVELOPER FOUND");

        }
        Optional<List<DeveloperEntity>> optionalList = developerRepository.findByTeamId(teamEntityOptional.get());

        logger.info(message);

        List<DeveloperEntity> developerEntityList = new ArrayList<>();
        if(optionalList.isPresent()){
            developerEntityList = optionalList.get();
        }

        if(CollectionUtils.isEmpty(developerEntityList)){
           throw new BadRequestException("NO_DEVELOPER_FOUND","NO DEVELOPER FOUND");
        }

        developerEntityList.sort(Comparator.comparing(DeveloperEntity::getLastCalled));

        DeveloperEntity entity = developerEntityList.get(0);
        entity.setLastCalled(new Date());

        NotificationRequest request = new NotificationRequest();

        request.setPhoneNumber(entity.getPhoneNumber());
        request.setMessage(message);

        return notificationHelper.sendNotification(request);
    }
}
