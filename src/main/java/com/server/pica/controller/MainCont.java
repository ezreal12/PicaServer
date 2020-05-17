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
import com.server.pica.dto.MyAlbumResultVO;
import com.server.pica.dto.RegisterMemberDTO;
import com.server.pica.dto.ResultVO;
import com.server.pica.service.PicService;
import com.server.pica.service.PicServiceImpl;
import com.server.pica.util.FileUtil;




@Controller
@RequestMapping("/")
public class MainCont {
	
	
	@Autowired
	PicService picService;
	
	@RequestMapping("")
	public String home() {
		return "home";
	}

	//파일 이름에 특수문자가 있거나 길면 (괄호,한국어등) 에러 발생
	//특정 언어, 특수문자에 관한 처리도 필요
	@RequestMapping(value = "/picUpload.do",method = RequestMethod.POST)
	@ResponseBody
	public ResultVO upload(int p_member_id,int p_album_id, MultipartFile file,HttpServletRequest request){
		String savePath = request.getSession().getServletContext().getRealPath("/resource");
		int result = picService.savePicture(p_member_id, p_album_id, file,savePath);
		System.out.println("picUpload.do 파일 업로드 실행결과 : {"+ result+"}");
		System.out.println("picUpload.do : "+savePath);
		ResultVO v = new ResultVO();
		v.setResult(result);
		return v;
	}

	/*
	 * 주의 : insert에 not null 속성등을 빼먹는다던가
	 * sql문이나 안에 들어간 데이터가 이상하면 바로 ClassNotFoundException 에러를 뿜는데
	 * 이 에러는 특정 원인을 가르쳐주지 않는 범용적인 에러라 직접 수수께끼 찾기 해야함.
	 * */
	@RequestMapping(value = "/registerMember.do",method = RequestMethod.POST)
	@ResponseBody
	public ResultVO registerMember(RegisterMemberDTO dto){
		System.out.println("registerMember : "+dto.toString());
		int result = picService.registerMember(dto);
		System.out.println("picUpload.do 파일 업로드 실행결과 : {"+ result+"}");
		ResultVO v = new ResultVO();
		v.setResult(result);
		return v;
	}
	/*
	 * 주의 : insert에 not null 속성등을 빼먹는다던가
	 * sql문이나 안에 들어간 데이터가 이상하면 바로 ClassNotFoundException 에러를 뿜는데
	 * 이 에러는 특정 원인을 가르쳐주지 않는 범용적인 에러라 직접 수수께끼 찾기 해야함.
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
	
	///myAlbum.do?member_id=##
	@RequestMapping(value = "/myAlbum.do",method = RequestMethod.GET)
	@ResponseBody
	public MyAlbumResultVO myAlbum(int member_id,HttpServletRequest request) {
		System.out.println("myAlbum.do : "+member_id);
		
		MyAlbumResultVO resultVO = new MyAlbumResultVO();
		List<CreateAlbumDTO> list = picService.getMyalbum(member_id);
		if(list==null) {
			resultVO.setCode(PicServiceImpl.NOT_FOUND_DATA);
		}
		else {
			list = FileUtil.insertServerUrlInImages(list, request);
			resultVO.setResult(list);
			resultVO.setCode(PicServiceImpl.UPLOAD_OK);
		}
		return resultVO;
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
