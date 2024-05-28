package com.gl.givuluv.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.gl.givuluv.domain.dto.FBoardDTO;
import com.gl.givuluv.domain.dto.ProductDTO;

import ch.qos.logback.core.model.Model;

public interface FBoardService {
	boolean regist(Model model, FBoardDTO fBoard, List<ProductDTO> productList, String filenames, MultipartFile thumbnail) throws Exception;
}
