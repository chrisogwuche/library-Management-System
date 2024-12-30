package org.example.libraryservice;


import jakarta.jms.JMSException;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.example.libraryservice.dto.AddBookRequest;
import org.example.libraryservice.dto.BookRequest;
import org.example.libraryservice.dto.UserDto;
import org.example.libraryservice.dto.UserRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.web.client.RestTemplate;

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

            System.out.print("Enter input: ");
            int action = scanner.nextInt();
            scanner.nextLine();

            switch (action) {
                case 1 -> createUser(scanner);
                case 2 -> addBook(scanner);
                case 3 -> borrowBook(scanner);
                case 4 -> returnBook(scanner);
                case 5 -> userDetails(scanner);
                case 6 -> bookSearch(scanner);
                case 7 -> allUsers();
                case 0 -> running = false;
                default -> System.out.println("Wrong input!");
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

    private void borrowBook(Scanner scanner){
        System.out.print("Enter user id: ");
        String userId = scanner.nextLine();
        System.out.print("Enter book isbn: ");
        String isbn = scanner.nextLine();

        BookRequest request = new BookRequest();
        request.setIsbn(isbn);
        request.setUserId(userId);
        processBorrowBook(request);
    }

    private void allUsers(){
        processAllUsers();
    }

    private void bookSearch(Scanner scanner){
        System.out.print("Enter book title or author: ");
        String keyword = scanner.nextLine();

        UserDto request = new UserDto();

        request.setKeyword(keyword);
        processBookSearch(request);
    }

    private void returnBook(Scanner scanner){
        System.out.print("Enter user id: ");
        String userId = scanner.nextLine();
        System.out.print("Enter book isbn: ");
        String isbn = scanner.nextLine();

        BookRequest request = new BookRequest();
        request.setIsbn(isbn);
        request.setUserId(userId);
        processReturnBook(request);
    }

    private void userDetails(Scanner scanner){
        System.out.print("Enter user id: ");
        String userId = scanner.nextLine();

        UserDto request = new UserDto();
        request.setId(userId);
        processUserDetails(request);
    }

    private void addBook(Scanner scanner){
        System.out.print("Enter author: ");
        String author = scanner.nextLine();
        System.out.print("Enter book isbn: ");
        String isbn = scanner.nextLine();
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();

        AddBookRequest request = new AddBookRequest();
        request.setAuthor(author);
        request.setIsbn(isbn);
        request.setTitle(title);

        processAddBook(request);
    }

    private void sendCreateUserRequest(UserRequest userRequest){
        JmsTemplate jmsTemplate = jmsTemplate();
        jmsTemplate.convertAndSend("add-user",userRequest);
        System.out.println("User created successfully\n\n");
    }

    private void processAllUsers() {
        String url = "http://localhost:8081/user/all-users";
        try{
            String response = process(null,url,HttpMethod.GET);
            System.out.println("response: "+response +"\n\n");
        }
        catch (Exception e){
            System.out.println("Error: "+e.getMessage());
        }
    }

    private void processBookSearch(UserDto request) {
        String url = "http://localhost:8081/user/search";
        try{
           String response = process(request,url,HttpMethod.POST);
            System.out.println("response: "+response +"\n\n");
        }
        catch (Exception e){
            System.out.println("Error: "+e.getMessage());
        }
    }

    private void processUserDetails(UserDto request) {
        String url = "http://localhost:8081/user/user-details";
        try{
            String response = process(request,url,HttpMethod.POST);
            System.out.println("response: "+response +"\n\n");
        }
        catch (Exception e){
            System.out.println("Error: "+e.getMessage());
        }
    }

    private void processReturnBook(BookRequest request){
        String url = "http://localhost:8081/user/return-book";
        try{
            String response = process(request,url,HttpMethod.POST);
            System.out.println("response: "+response +"\n\n");
        }
        catch (Exception e){
            System.out.println("Error: "+e.getMessage());
        }
    }

    private void processBorrowBook(BookRequest request){
        String url = "http://localhost:8081/user/borrow-book";
        try{
            String response = process(request,url,HttpMethod.POST);
            System.out.println("response: "+response +"\n\n");
        }
        catch (Exception e){
            System.out.println("Error: "+e.getMessage());
        }
    }

    private void processAddBook(AddBookRequest request){
        String url = "http://localhost:8081/user/add-book";
        try{
            String response = process(request,url,HttpMethod.POST);
            System.out.println("response: "+response +"\n\n");
        }
        catch (Exception e){
            System.out.println("Error: "+e.getMessage());
        }
    }

    private String process(Object object, String url, HttpMethod httpMethod){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<?> httpEntity = new HttpEntity<>(object,headers);
        return restTemplate.exchange(url,httpMethod,httpEntity,String.class).getBody();
    }

    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        return converter;
    }

    public JmsTemplate jmsTemplate(){
        CachingConnectionFactory cachingConnectionFactory;
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
