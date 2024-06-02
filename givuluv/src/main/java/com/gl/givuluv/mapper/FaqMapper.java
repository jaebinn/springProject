package com.gl.givuluv.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.gl.givuluv.domain.dto.FaqDTO;

@Mapper
public interface FaqMapper {

	Optional<FaqDTO> findByQuestion(String question);

	List<FaqDTO> findAllQuestions();

	String getAnswerByQuestionNumber(Integer faqnum);


}
