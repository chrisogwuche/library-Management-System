package org.example.userservice.queues;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import kong.unirest.JsonObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.example.userservice.dto.UserRequest;
import org.example.userservice.services.UserService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class Listeners {

    private final JsonObjectMapper jsonObjectMapper;
    private final UserService userService;

    @JmsListener(destination = "add-user",containerFactory = "jmsFactory")
    public void addUser(Message message){

        UserRequest userRequest = null;
        try {
            userRequest = parseMessage(message, UserRequest.class);

            log.info("CREATE USER REQUEST MESSAGE: {}",message.getBody(String.class));

        } catch (JMSException e) {
            log.error("Error parsing message: {}",e.getMessage());
        }

        Map<String, String> response = userService.createUser(userRequest);
        log.info("Add user response: {}", response);
    }

    private <T> T parseMessage(Message message, Class<T> clazz) throws JMSException {
        return jsonObjectMapper.readValue(message.getBody(String.class), clazz);
    }

}
