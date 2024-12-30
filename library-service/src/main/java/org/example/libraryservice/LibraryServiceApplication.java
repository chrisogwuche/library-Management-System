package org.example.libraryservice;


import jakarta.jms.JMSException;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.example.libraryservice.dto.UserRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import java.util.Scanner;

@SpringBootApplication
public class LibraryServiceApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(LibraryServiceApplication.class, args);
    }

    private void scanner(){
        Scanner scanner = new Scanner(System.in);
        boolean running  = true;

        while(running) {
            System.out.println("What would you like to do?");
            System.out.println("Enter 1: To create a user,\n" +
                    "Enter 2: To add a book,\n" +
                    "Enter 3: To borrow a book,\n" +
                    "Enter 4: To return a book,\n" +
                    "Enter 5: To get a user details,\n" +
                    "Enter 6: To search for a book by author or title\n" +
                    "Enter 7: To get all users,\n" +
                    "Enter 0: To Exit");

            System.out.print("input: ");
            int action = scanner.nextInt();
            scanner.nextLine();

            switch (action){
                case 1:
                    createUser(scanner);
                    break;

                case 0:
                    running = false;
                    break;

                default:
                    System.out.println("Wrong input!");

            }

        }

    }

    private void createUser(Scanner scanner) {
        System.out.print("Enter user name: ");
        String name = scanner.nextLine();

        UserRequest userRequest = new UserRequest();
        userRequest.setName(name);
        sendCreateUserRequest(userRequest);
    }

    private void sendCreateUserRequest(UserRequest userRequest){
        JmsTemplate jmsTemplate = jmsTemplate();
        jmsTemplate.convertAndSend("add-user",userRequest);
        System.out.println("User created successfully\n\n");
    }

//    private void sendCreateBookRequest(AddBookRequest request){
//        JmsTemplate jmsTemplate = jmsTemplate();
//        jmsTemplate.convertAndSend("add-book",request);
//    }

    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        return converter;
    }

    public JmsTemplate jmsTemplate(){
        CachingConnectionFactory cachingConnectionFactory =
                null;
        try {
            cachingConnectionFactory = new CachingConnectionFactory(connectionFactory());
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
        JmsTemplate jmsTemplate = new JmsTemplate(cachingConnectionFactory);
        jmsTemplate.setMessageConverter(jacksonJmsMessageConverter());
        return jmsTemplate;
    }

    public ActiveMQConnectionFactory connectionFactory() throws JMSException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        factory.setBrokerURL("tcp://localhost:61616");
        factory.setPassword("admin");
        factory.setUserName("admin");
        return factory;
    }

    @Override
    public void run(String... args) throws Exception {
        scanner();
    }
}
