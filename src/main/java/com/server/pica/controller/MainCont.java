package com.server.pica.controller;

import java.util.ArrayList;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.server.pica.dto.CreateAlbumDTO;
import com.server.pica.dto.RegisterMemberDTO;
import com.server.pica.dto.ResultVO;
import com.server.pica.service.PicService;




@Controller
@RequestMapping("/")
public class MainCont {
	
	
	@Autowired
	PicService picService;
	
	@RequestMapping("")
	public String home() {
		return "home";
	}

	//���� �̸��� Ư�����ڰ� �ְų� ��� (��ȣ,�ѱ����) ���� �߻�
	//Ư�� ���, Ư�����ڿ� ���� ó���� �ʿ�
	@RequestMapping(value = "/picUpload.do",method = RequestMethod.POST)
	@ResponseBody
	public ResultVO upload(int p_member_id,int p_album_id, MultipartFile file,HttpServletRequest request){
		String savePath = request.getSession().getServletContext().getRealPath("/resource");
		int result = picService.savePicture(p_member_id, p_album_id, file,savePath);
		System.out.println("picUpload.do ���� ���ε� ������ : {"+ result+"}");
		System.out.println("picUpload.do : "+savePath);
		ResultVO v = new ResultVO();
		v.setResult(result);
		return v;
	}

	/*
	 * ���� : insert�� not null �Ӽ����� ���Դ´ٴ���
	 * sql���̳� �ȿ� �� �����Ͱ� �̻��ϸ� �ٷ� ClassNotFoundException ������ �մµ�
	 * �� ������ Ư�� ������ ���������� �ʴ� �������� ������ ���� �������� ã�� �ؾ���.
	 * */
	@RequestMapping(value = "/registerMember.do",method = RequestMethod.POST)
	@ResponseBody
	public ResultVO registerMember(RegisterMemberDTO dto){
		System.out.println("registerMember : "+dto.toString());
		int result = picService.registerMember(dto);
		System.out.println("picUpload.do ���� ���ε� ������ : {"+ result+"}");
		ResultVO v = new ResultVO();
		v.setResult(result);
		return v;
	}
	/*
	 * ���� : insert�� not null �Ӽ����� ���Դ´ٴ���
	 * sql���̳� �ȿ� �� �����Ͱ� �̻��ϸ� �ٷ� ClassNotFoundException ������ �մµ�
	 * �� ������ Ư�� ������ ���������� �ʴ� �������� ������ ���� �������� ã�� �ؾ���.
	 * */
	@RequestMapping(value = "/createAlbum.do",method = RequestMethod.POST)
	@ResponseBody
	public ResultVO createAlbum(CreateAlbumDTO dto, MultipartFile file,HttpServletRequest request){
		System.out.println("createAlbum : "+dto.toString());
		String savePath = request.getSession().getServletContext().getRealPath("/resource");
		System.out.println("createAlbum : "+savePath);
		int result = picService.createAlbum(dto, file,savePath);
		ResultVO v = new ResultVO();
		v.setResult(result);
		return v;
	}
	
	@RequestMapping(value = "/showTable.do",method = RequestMethod.GET)
	public ModelAndView showTablePage(){
		ArrayList<String> table = new ArrayList<String>();
		table.add("PICTURE");
		table.add("MEMBER");
		table.add("ALBUM");
		ModelAndView mav = new ModelAndView();
	    mav.setViewName("showTablePage");
	    mav.addObject("table", table);
		return mav;
	}
	
	
	@RequestMapping(value = "/dbResult.do",method = RequestMethod.GET)
	public ModelAndView showResultPage(String tableName){
		ArrayList<String> itemString;
		List list = picService.showTable(tableName);
		ModelAndView mav = new ModelAndView();
		if(list==null) {
			 mav.addObject("err", "err");
		}
		else {
			itemString=new ArrayList<String>();
			for(Object i : list) {
				itemString.add(i.toString());
			}
			mav.addObject("result", itemString);
		}
		
	    mav.setViewName("dbResultPage");
	   
		return mav;
	}
	
	
}
