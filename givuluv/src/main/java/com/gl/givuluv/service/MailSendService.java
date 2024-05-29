package com.gl.givuluv.service;

import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Component
public class MailSendService {
    
    @Autowired
    private JavaMailSender mailSender;
    
    private int authNumber; 
    
    public void makeRandomNumber() {
        // 난수의 범위 111111 ~ 999999 (6자리 난수)
        Random r = new Random();
        int checkNum = r.nextInt(888888) + 111111;
        System.out.println("인증번호 : " + checkNum);
        authNumber = checkNum;
    } 
        
       
    //이메일 보낼 양식! 
    public String joinEmail(String email) {
        makeRandomNumber();
        
        String setFrom = "whiteclover129@gmail.com"; // 자신의 Gmail 주소 입력 
        String toMail = email;
        String title = "아이디 찾기 인증 이메일 입니다."; // 이메일 제목 
        String content = "GivuLuv 아이디 찾기 인증 번호는 " + authNumber + "입니다. 인증번호 확인란에 기입해주세요.";

        mailSend(setFrom, toMail, title, content);
        return Integer.toString(authNumber);
    }
        
    //이메일 전송 메소드
    public void mailSend(String setFrom, String toMail, String title, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        
        try {
            message.setFrom(setFrom); // 보내는 사람 설정
            message.setTo(toMail); // 받는 사람 설정
            message.setSubject(title); // 이메일 제목 설정
            message.setText(content); // 이메일 내용 설정, HTML 형식을 사용하려면 true로 설정

            mailSender.send(message); // 이메일 전송
        } catch (Exception e) {
            e.printStackTrace(); // 예외 처리
        }
        
    }
}
