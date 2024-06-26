package com.gl.givuluv.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{
   
   @Value("${file.dir}")
   private String saveFolder;
   
   
   //web root가 아닌 외부 경로에 있는 리소스를 url로 불러올 수 있도록 설정
    //localhost:8080/summernoteImage/1234.jpg
    //로 접속하면 C:/summernote_image/1234.jpg 파일을 불러온다.
   @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
       System.out.println("WebMvcConfig");
        registry.addResourceHandler("/summernoteImage/**")
                .addResourceLocations("file:///"+saveFolder);
        		
    }
}