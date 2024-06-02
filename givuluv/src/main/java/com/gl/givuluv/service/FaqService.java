package com.gl.givuluv.service;

import java.util.List;
import java.util.Optional;

import com.gl.givuluv.domain.dto.FaqDTO;

public interface FaqService {

	Optional<FaqDTO> getFaqByQuestion(String question);

	List<FaqDTO> getAllQuestions();

	String getAnswerByQuestionNumber(Integer questionNumber);


}
