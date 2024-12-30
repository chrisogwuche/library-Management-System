package org.example.userservice.queues;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import kong.unirest.JsonObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.userservice.dto.AddBookRequest;
import org.example.userservice.dto.BookRequest;
import org.example.userservice.dto.UserRequest;
import org.example.userservice.services.BookService;
import org.example.userservice.services.UserService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class Listeners {

    private final JsonObjectMapper jsonObjectMapper;
    private final UserService userService;
    private final BookService bookService;

    @JmsListener(destination = "add-user",containerFactory = "jmsFactory")
    public void addUser(Message message){

        UserRequest userRequest = null;
        try {
            userRequest = parseMessage(message, UserRequest.class);

            log.info("CREATE USER REQUEST MESSAGE: {}",message.getBody(String.class));

        } catch (JMSException e) {
            log.error("Error parsing message: {}",e.getMessage());
        }

        userService.createUser(userRequest);

    }

    @JmsListener(destination = "add-book",containerFactory = "jmsFactory")
    public void addBook(Message message){
        AddBookRequest addBookRequest = null;
        try {
            addBookRequest = parseMessage(message, AddBookRequest.class);

            log.info(" ADD BOOK REQUEST MESSAGE: {}",message.getBody(String.class));

        } catch (JMSException e) {
            log.error("Error parsing message: {}",e.getMessage());
        }

        bookService.addBook(addBookRequest);

    }
    @JmsListener(destination = "borrow-book",containerFactory = "jmsFactory")
    public void borrowBook(Message message){

        BookRequest request = null;
        try {
            request = parseMessage(message, BookRequest.class);

            log.info(" BORROW BOOK REQUEST MESSAGE: {}",message.getBody(String.class));

        } catch (JMSException e) {
            log.error("Error parsing message: {}",e.getMessage());
        }

        userService.borrowBook(request);
    }
    @JmsListener(destination = "return-book",containerFactory = "jmsFactory")
    public void returnBook(Message message){

        BookRequest request = null;
        try {
            request = parseMessage(message, BookRequest.class);

            log.info(" RETURN BOOK REQUEST MESSAGE: {}",message.getBody(String.class));

        } catch (JMSException e) {
            log.error("Error parsing message: {}",e.getMessage());
        }

        userService.returnBook(request);
    }


    private <T> T parseMessage(Message message, Class<T> clazz) throws JMSException {
        return jsonObjectMapper.readValue(message.getBody(String.class), clazz);
    }

}
