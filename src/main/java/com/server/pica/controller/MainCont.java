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
import com.server.pica.dto.LikePictureDTO;
import com.server.pica.dto.LoginVO;
import com.server.pica.dto.MyAlbumDTO;
import com.server.pica.dto.MyAlbumResultVO;
import com.server.pica.dto.PicUploadDTO;
import com.server.pica.dto.RegisterMemberDTO;
import com.server.pica.dto.ResultVO;
import com.server.pica.dto.ShowPictureDataResultVO;
import com.server.pica.dto.ShowPictureResultVO;
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

	// 파일 이름에 특수문자가 있거나 길면 (괄호,한국어등) 에러 발생
	// 특정 언어, 특수문자에 관한 처리도 필요
	@RequestMapping(value = "/picUpload.do", method = RequestMethod.POST)
	@ResponseBody
	public ResultVO upload(PicUploadDTO dto, String tags, MultipartFile realfile, HttpServletRequest request) {
		String savePath = FileUtil.getFileSavePath(request);
		System.out.println("------ TEST HS 1 !!!! : " + dto.toString() + " tags : " + tags);
		int code = picService.savePicture(dto, tags, realfile, savePath);
		System.out.println("picUpload.do 파일 업로드 실행결과 : {" + code + "}");
		System.out.println("picUpload.do : " + savePath);
		ResultVO v = new ResultVO();
		v.setCode(code);
		return v;
	}

	/*
	 * 주의 : insert에 not null 속성등을 빼먹는다던가 sql문이나 안에 들어간 데이터가 이상하면 바로
	 * ClassNotFoundException 에러를 뿜는데 이 에러는 특정 원인을 가르쳐주지 않는 범용적인 에러라 직접 수수께끼 찾기 해야함.
	 */
	@RequestMapping(value = "/registerMember.do", method = RequestMethod.POST)
	@ResponseBody
	public ResultVO registerMember(RegisterMemberDTO dto) {
		System.out.println("registerMember : " + dto.toString());
		int code = picService.registerMember(dto);
		System.out.println("picUpload.do 파일 업로드 실행결과 : {" + code + "}");
		ResultVO v = new ResultVO();
		v.setCode(code);
		return v;
	}

	/*
	 * 주의 : insert에 not null 속성등을 빼먹는다던가 sql문이나 안에 들어간 데이터가 이상하면 바로
	 * ClassNotFoundException 에러를 뿜는데 이 에러는 특정 원인을 가르쳐주지 않는 범용적인 에러라 직접 수수께끼 찾기 해야함.
	 */
	@RequestMapping(value = "/createAlbum.do", method = RequestMethod.POST)
	@ResponseBody
	public ResultVO createAlbum(CreateAlbumDTO dto, MultipartFile file, HttpServletRequest request) {
		System.out.println("createAlbum : " + dto.toString());
		String savePath = request.getSession().getServletContext().getRealPath("/resource");
		System.out.println("createAlbum : " + savePath);
		int code = picService.createAlbum(dto, file, savePath);
		ResultVO v = new ResultVO();
		v.setCode(code);
		return v;
	}

	// 사진 좋아요 누르기
	@RequestMapping(value = "/likePicture.do", method = RequestMethod.POST)
	@ResponseBody
	public ResultVO likePicture(LikePictureDTO dto, HttpServletRequest request) {
		int code = picService.addLikePicture(dto);
		ResultVO v = new ResultVO();
		v.setCode(code);
		return v;
	}

	// 사진에 댓글 1개 달기
	@RequestMapping(value = "/replyPictrueAdd.do", method = RequestMethod.POST)
	@ResponseBody
	public ResultVO replyPictrueAdd(int member_id, int picture_id, String reply_text, HttpServletRequest request) {
		int code = picService.addReply(member_id, picture_id, reply_text);
		ResultVO v = new ResultVO();
		v.setCode(code);
		return v;
	}

	// 테스트용
	@RequestMapping(value = "/showTable.do", method = RequestMethod.GET)
	public ModelAndView showTablePage() {

		ArrayList<String> table = new ArrayList<String>();
		table.add("PICTURE");
		table.add("MEMBER");
		table.add("ALBUM");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("showTablePage");
		mav.addObject("table", table);
		return mav;
	}

	/// myAlbum.do?member_id=##
	@RequestMapping(value = "/myAlbum.do", method = RequestMethod.GET)
	@ResponseBody
	public MyAlbumResultVO myAlbum(int member_id, HttpServletRequest request) {
		System.out.println("myAlbum.do : " + member_id);

		MyAlbumResultVO resultVO = new MyAlbumResultVO();
		List<MyAlbumDTO> list = picService.getMyalbum(member_id);
		if (list == null) {
			resultVO.setCode(PicServiceImpl.NOT_FOUND_DATA);
		} else {
			list = FileUtil.insertServerUrlInImages(list, request);
			resultVO.setResult(list);
			resultVO.setCode(PicServiceImpl.REQUEST_OK);
		}
		return resultVO;
	}

	// showPictureData.do?picture_id=##&member_id=##
	@RequestMapping(value = "/showPictureData.do", method = RequestMethod.GET)
	@ResponseBody
	public ShowPictureDataResultVO showPictureData(int picture_id, int member_id, HttpServletRequest request) {
		ShowPictureDataResultVO result = picService.showPicture(picture_id, member_id);
		result = FileUtil.insertServerUrlInImages(result, request);
		System.out.println("showPictureData.do : " + result.toString());
		return result;
	}

	// 앨범내 사진보기
	@RequestMapping(value = "/showPicture.do", method = RequestMethod.GET)
	@ResponseBody
	public ShowPictureResultVO showPicture(int album_id, int member_id, HttpServletRequest request) {
		System.out.println("showPicture.do : album_id : " + album_id + " member_id :" + member_id);
		ShowPictureResultVO result = picService.showPictureList(album_id, member_id);
		result = FileUtil.insertServerUrlInImages(result, request);
		// parseImageSrc
		result.setDefaultPicture(FileUtil.parseImageSrc(result.getDefaultPicture(), request));
		return result;
	}

	// 좋아하는 사진 목록 보기
	@RequestMapping(value = "/getLikePictureList.do", method = RequestMethod.GET)
	@ResponseBody
	public ShowPictureResultVO getLikePictureList(int member_id, HttpServletRequest request) {
		System.out.println("getLikePictureList.do : member_id : " + member_id);
		ShowPictureResultVO result = picService.showLikePictureList(member_id);
		result = FileUtil.insertServerUrlInImages(result, request);
		// parseImageSrc
		result.setDefaultPicture(FileUtil.parseImageSrc(result.getDefaultPicture(), request));
		return result;
	}

	// 로그인하기
	// 0 성공, -1 에러(안씀), -2 비밀번호 오류, -3 아이디없음
	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	@ResponseBody
	public LoginVO login(String email, String password) {
		System.out.println("login.do : email : " + email + " password :" + password);
		LoginVO result = picService.login(email, password);
		return result;
	}

	@RequestMapping(value = "/dbResult.do", method = RequestMethod.GET)
	public ModelAndView showResultPage(String tableName) {
		ArrayList<String> itemString;
		List list = picService.showTable(tableName);
		ModelAndView mav = new ModelAndView();
		if (list == null) {
			mav.addObject("err", "err");
		} else {
			itemString = new ArrayList<String>();
			for (Object i : list) {
				itemString.add(i.toString());
			}
			mav.addObject("result", itemString);
		}

		mav.setViewName("dbResultPage");

		return mav;
	}

}
