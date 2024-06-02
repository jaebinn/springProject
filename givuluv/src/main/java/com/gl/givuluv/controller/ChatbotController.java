package com.gl.givuluv.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gl.givuluv.domain.dto.FaqDTO;
import com.gl.givuluv.service.FaqService;

@RestController
@RequestMapping("/chatbot/*")
public class ChatbotController {
    @Autowired
    private FaqService faqService;
    
    @PostMapping("questions")
    @ResponseBody
    public List<FaqDTO> getAllQuestions() {
        return faqService.getAllQuestions();
    }
    
    @PostMapping("answer")
    @ResponseBody
    public ResponseEntity<String> getAnswer(@RequestBody Map<String, Integer> requestBody) {
        Integer questionNumber = requestBody.get("questionNumber");
        if (questionNumber != null) {
            System.out.println(questionNumber);
            String answer = faqService.getAnswerByQuestionNumber(questionNumber);
            if (answer != null) {
                return ResponseEntity.ok(answer);
            } else {
                String errorMessage = "질문을 찾을 수 없습니다. 요청한 질문 번호: " + questionNumber;
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
            }
        } else {
            return ResponseEntity.badRequest().body("질문 번호를 찾을 수 없습니다.");
        }
    }

}

