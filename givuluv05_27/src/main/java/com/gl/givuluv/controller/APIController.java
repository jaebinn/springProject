package com.gl.givuluv.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/api/*")
public class APIController {
	
	final static String serviceKey = "reHCol7bfQHqGd%2BYMI10ShfWHGOuqUzrv9wU11YXZMJit7f6JF43V2bZ%2BGR1p9KIx1%2Fq8t7TaiNaUldbGg%2FsnA%3D%3D";
	@GetMapping("public")
	public void replace() {}
	
	@PostMapping(value = "public", produces = "application/json;charset=utf-8")
	@ResponseBody
	public String getData() throws Exception {
		//공공데이터포탈과 통신
		String url = "http://api.odcloud.kr/api/15091502/v1/uddi:9da76b1c-a81c-4173-a37e-f9504d6b774d?serviceKey="+serviceKey;
		url += "&page="+1;
		url += "&perPage="+100000;
		url += "&returnType=json";
		
		//단순한 문자열로 정의한 url 변수를 자바에서 네트워킹 때 활용할 수 있는 객체로 변환
		URL requestURL = new URL(url);
		//목적지로 향하는 다리 건설
		HttpURLConnection conn = (HttpURLConnection)requestURL.openConnection();
		
		conn.setRequestMethod("GET");
		
		//conn 다리가 건설되어 있는 목적지로부터 데이터를 읽어오기 위한 IS
		InputStream is = conn.getInputStream();
		//열려있는 IS 통로를 통해 들어오는 데이터를 읽기위한 리더기
		InputStreamReader isr = new InputStreamReader(is);
		
		BufferedReader br = new BufferedReader(isr);
		
		String result = "";
		String line = "";
		while(true) {
			line = br.readLine();
			if(line == null) { break; }
			result += line;
		}
		
//		System.out.println(result);
		
		return result;
	}
	
}
