package com.server.pica.controller;

import java.io.File;
import java.io.IOException;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;




@Controller
@RequestMapping("/")
public class MainCont {
	//C:\Users\SiuKim\Desktop\sample
	private static final String UPLOAD_PATH = "C:\\Users\\SiuKim\\Desktop\\sample";
	@RequestMapping("test")
	public String test() {
		return "test";
	}
	@RequestMapping("")
	public String home() {
		return "home";
	}
	
	@RequestMapping(value = "/picUpload.do",method = RequestMethod.POST)
	public void upload(int p_member_id,int p_album_id, MultipartFile uploadfile){
	    //logger.info("upload() POST 호출");
	    //logger.info("파일 이름: {}", uploadfile.getOriginalFilename());
	    //logger.info("파일 크기: {}", uploadfile.getSize());
		System.out.println("upload() POST 호출");
		System.out.println("파일 이름: {"+uploadfile.getOriginalFilename()+"}");
		System.out.println("파일 크기: {"+ uploadfile.getSize()+"}");
		System.out.println("p_member_id : {"+p_member_id+"}");
		System.out.println("p_album_id : {"+ p_album_id+"}");
	    saveFile(uploadfile);

	}

	private String saveFile(MultipartFile file){
	   
	    String saveName = file.getOriginalFilename();

	    //logger.info("saveName: {}",saveName);
	    System.out.println("saveName: {"+saveName+"}");

	    // 저장할 File 객체를 생성(껍데기 파일)ㄴ
	    File saveFile = new File(UPLOAD_PATH,saveName); // 저장할 폴더 이름, 저장할 파일 이름

	    try {
	        file.transferTo(saveFile); // 업로드 파일에 saveFile이라는 껍데기 입힘
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }

	    return saveName;
	} // end saveFile(
	
}
