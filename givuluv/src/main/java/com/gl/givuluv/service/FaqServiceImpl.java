package com.gl.givuluv.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl.givuluv.domain.dto.FaqDTO;
import com.gl.givuluv.mapper.FaqMapper;

@Service
public class FaqServiceImpl implements FaqService{

	@Autowired
    private FaqMapper faqMapper;

    public Optional<FaqDTO> getFaqByQuestion(String question) {
        return faqMapper.findByQuestion(question);
    }

    public List<FaqDTO> getAllQuestions() {
        return faqMapper.findAllQuestions();
    }

	@Override
	public String getAnswerByQuestionNumber(Integer faqnum) {
		return faqMapper.getAnswerByQuestionNumber(faqnum);
	}


}
