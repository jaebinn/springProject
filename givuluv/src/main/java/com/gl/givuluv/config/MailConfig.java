package com.gl.givuluv.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {
    @Bean
    public JavaMailSender javaMailService() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setHost("smtp.gmail.com");
        javaMailSender.setUsername("whiteclover129@gmail.com");
        javaMailSender.setPassword(""); // gitignore해야함

        javaMailSender.setPort(587); 

        javaMailSender.setJavaMailProperties(getMailProperties());

        return javaMailSender;
    }
    
    private Properties getMailProperties() {
    	Properties properties = new Properties();
    	properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true"); // TLS 사용
        properties.put("mail.smtp.starttls.required", "true"); // TLS 필수 사용
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2 TLSv1.3"); // TLS 프로토콜 지정
        properties.put("mail.debug", "true");
        return properties;
    }
}
