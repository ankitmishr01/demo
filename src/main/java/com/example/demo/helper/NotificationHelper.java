package com.example.demo.helper;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.NotificationRequest;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static com.example.demo.constant.Constants.NOTIFICATION_FALURE_CODE;
import static com.example.demo.constant.Constants.NOTIFICATION_FALURE_MESSAGE;

@Component
public class NotificationHelper {

    private static Logger logger = LoggerFactory.getLogger(NotificationHelper.class);

    @Value("${notification.service.url}")
    private String notificationUrl;

    public JsonNode sendNotification(NotificationRequest request) throws BadRequestException {

        try {
            RestTemplate restTemplate = new RestTemplate();

            RequestEntity<NotificationRequest> requestEntity = new RequestEntity<>(request, null, HttpMethod.POST, new URI(notificationUrl));

            ResponseEntity<JsonNode> responseEntity = restTemplate.exchange(requestEntity, JsonNode.class);

            logger.info("Successfully called notification service for {}, response - {}", request, responseEntity.toString());
            if(responseEntity.getStatusCode().is2xxSuccessful())
                return responseEntity.getBody();
        } catch (HttpClientErrorException e){
            logger.error("Exception -", e);
            throw e;
        } catch (Exception e){
            logger.error("Exception -", e);
            throw new BadRequestException(NOTIFICATION_FALURE_CODE, NOTIFICATION_FALURE_MESSAGE);
        }
        return null;
    }
}
