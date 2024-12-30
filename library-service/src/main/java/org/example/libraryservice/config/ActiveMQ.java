//package org.example.libraryservice.config;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jms.annotation.EnableJms;
//import org.springframework.jms.connection.CachingConnectionFactory;
//import org.springframework.jms.core.JmsTemplate;
//import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
//import org.springframework.jms.support.converter.MessageConverter;
//import org.springframework.jms.support.converter.MessageType;
//
//@Configuration
//@EnableJms
//@RequiredArgsConstructor
//public class ActiveMQ {
//
//    private final CachingConnectionFactory cachingConnectionFactory;
//
//    @Bean
//    public MessageConverter jacksonJmsMessageConverter() {
//        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
//        converter.setTargetType(MessageType.TEXT);
//        converter.setTypeIdPropertyName("_asb_");
//        return converter;
//    }
//
//    @Bean
//    public JmsTemplate jmsTemplate(MessageConverter converter){
//        JmsTemplate jmsTemplate = new JmsTemplate(cachingConnectionFactory);
//        jmsTemplate.setMessageConverter(converter);
//        return jmsTemplate;
//    }
//}
