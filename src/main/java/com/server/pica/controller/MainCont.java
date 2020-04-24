package com.server.pica.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

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
	public void upload(MultipartFile uploadfile){
	    //logger.info("upload() POST ȣ��");
	    //logger.info("���� �̸�: {}", uploadfile.getOriginalFilename());
	    //logger.info("���� ũ��: {}", uploadfile.getSize());
		System.out.println("upload() POST ȣ��");
		System.out.println("���� �̸�: {"+uploadfile.getOriginalFilename()+"}");
		System.out.println("���� ũ��: {"+ uploadfile.getSize()+"}");
	    saveFile(uploadfile);

	}

	private String saveFile(MultipartFile file){
	    // ���� �̸� ����
	    UUID uuid = UUID.randomUUID();
	    String saveName = uuid + "_" + file.getOriginalFilename();

	    //logger.info("saveName: {}",saveName);
	    System.out.println("saveName: {"+saveName+"}");

	    // ������ File ��ü�� ����(������ ����)��
	    File saveFile = new File(UPLOAD_PATH,saveName); // ������ ���� �̸�, ������ ���� �̸�

	    try {
	        file.transferTo(saveFile); // ���ε� ���Ͽ� saveFile�̶�� ������ ����
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }

	    return saveName;
	} // end saveFile(
	
}
