package com.server.pica.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

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
	//���� �̸��� Ư�����ڰ� �ְų� ��� (��ȣ,�ѱ����) ���� �߻�
	//Ư�� ���, Ư�����ڿ� ���� ó���� �ʿ�
	@RequestMapping(value = "/picUpload.do",method = RequestMethod.POST)
	public void upload(int p_member_id,int p_album_id, MultipartFile uploadfile){
	    //logger.info("upload() POST ȣ��");
	    //logger.info("���� �̸�: {}", uploadfile.getOriginalFilename());
	    //logger.info("���� ũ��: {}", uploadfile.getSize());
		System.out.println("upload() POST ȣ��");
		System.out.println("���� �̸�: {"+uploadfile.getOriginalFilename()+"}");
		System.out.println("���� ũ��: {"+ uploadfile.getSize()+"}");
		System.out.println("p_member_id : {"+p_member_id+"}");
		System.out.println("p_album_id : {"+ p_album_id+"}");
		int result = picService.savePicture(p_member_id, p_album_id, uploadfile);
		System.out.println("������ : {"+ result+"}");
	}

}
