package com.server.pica.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.server.pica.service.PicService;




@Controller
@RequestMapping("/")
public class MainCont {
	
	@Autowired
	PicService picService;
	
	
	@RequestMapping("test")
	public String test() {
		return "test";
	}
	@RequestMapping("")
	public String home() {
		return "home";
	}
	//파일 이름에 특수문자가 있거나 길면 (괄호,한국어등) 에러 발생
	//특정 언어, 특수문자에 관한 처리도 필요
	@RequestMapping(value = "/picUpload.do",method = RequestMethod.POST)
	public ModelAndView upload(int p_member_id,int p_album_id, MultipartFile uploadfile){
	    //logger.info("upload() POST 호출");
	    //logger.info("파일 이름: {}", uploadfile.getOriginalFilename());
	    //logger.info("파일 크기: {}", uploadfile.getSize());
		System.out.println("upload() POST 호출");
		System.out.println("파일 이름: {"+uploadfile.getOriginalFilename()+"}");
		System.out.println("파일 크기: {"+ uploadfile.getSize()+"}");
		System.out.println("p_member_id : {"+p_member_id+"}");
		System.out.println("p_album_id : {"+ p_album_id+"}");
		int result = picService.savePicture(p_member_id, p_album_id, uploadfile);
		System.out.println("실행결과 : {"+ result+"}");
		
		ModelAndView mav = new ModelAndView();
	    mav.setViewName("UploadTest");
	    mav.addObject("message", "Hello World!");
		
		return mav;
	}

}
