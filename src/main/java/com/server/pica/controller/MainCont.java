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
import com.server.pica.dto.ShowReplyResultVO;
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

	// ���� �̸��� Ư�����ڰ� �ְų� ��� (��ȣ,�ѱ����) ���� �߻�
	// Ư�� ���, Ư�����ڿ� ���� ó���� �ʿ�
	@RequestMapping(value = "/picUpload.do", method = RequestMethod.POST)
	@ResponseBody
	public ResultVO upload(PicUploadDTO dto, String tags, MultipartFile realfile, HttpServletRequest request) {
		String savePath = FileUtil.getFileSavePath(request);
		System.out.println("------ TEST HS 1 !!!! : " + dto.toString() + " tags : " + tags);
		int code = picService.savePicture(dto, tags, realfile, savePath);
		System.out.println("picUpload.do ���� ���ε� ������ : {" + code + "}");
		System.out.println("picUpload.do : " + savePath);
		ResultVO v = new ResultVO();
		v.setCode(code);
		return v;
	}

	/*
	 * ���� : insert�� not null �Ӽ����� ���Դ´ٴ��� sql���̳� �ȿ� �� �����Ͱ� �̻��ϸ� �ٷ�
	 * ClassNotFoundException ������ �մµ� �� ������ Ư�� ������ ���������� �ʴ� �������� ������ ���� �������� ã�� �ؾ���.
	 */
	@RequestMapping(value = "/registerMember.do", method = RequestMethod.POST)
	@ResponseBody
	public ResultVO registerMember(RegisterMemberDTO dto) {
		System.out.println("registerMember : " + dto.toString());
		int code = picService.registerMember(dto);
		System.out.println("picUpload.do ���� ���ε� ������ : {" + code + "}");
		ResultVO v = new ResultVO();
		v.setCode(code);
		return v;
	}

	/*
	 * ���� : insert�� not null �Ӽ����� ���Դ´ٴ��� sql���̳� �ȿ� �� �����Ͱ� �̻��ϸ� �ٷ�
	 * ClassNotFoundException ������ �մµ� �� ������ Ư�� ������ ���������� �ʴ� �������� ������ ���� �������� ã�� �ؾ���.
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

	// ���� ���ƿ� ������
	@RequestMapping(value = "/likePicture.do", method = RequestMethod.POST)
	@ResponseBody
	public ResultVO likePicture(LikePictureDTO dto, HttpServletRequest request) {
		int code = picService.addLikePicture(dto);
		ResultVO v = new ResultVO();
		v.setCode(code);
		return v;
	}

	// ������ ��� 1�� �ޱ�
	@RequestMapping(value = "/replyPictrueAdd.do", method = RequestMethod.POST)
	@ResponseBody
	public ResultVO replyPictrueAdd(int member_id, int picture_id, String reply_text, HttpServletRequest request) {
		int code = picService.addReply(member_id, picture_id, reply_text);
		ResultVO v = new ResultVO();
		v.setCode(code);
		return v;
	}

	// ������ ��� 1�� �����
	@RequestMapping(value = "/replyPictrueDelete.do", method = RequestMethod.POST)
	@ResponseBody
	public ResultVO replyPictrueAdd(int member_id, int reply_id) {
		int code = picService.deleteReply(member_id, reply_id);
		ResultVO v = new ResultVO();
		v.setCode(code);
		return v;
	}

	// ���� 1�� �����
	@RequestMapping(value = "/pictrueDelete.do", method = RequestMethod.GET)
	@ResponseBody
	public ResultVO pictrueDelete(int member_id, int picture_id) {
		int code = picService.deletePicData(member_id, picture_id);
		ResultVO v = new ResultVO();
		v.setCode(code);
		return v;
	}

	// �׽�Ʈ��
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

	// ���� 1���� ��� ��������
	// (int member_id, int picture_id)
	@RequestMapping(value = "/showReply.do", method = RequestMethod.GET)
	@ResponseBody
	public ShowReplyResultVO showReply(int picture_id, int member_id, HttpServletRequest request) {
		ShowReplyResultVO result = picService.getReply(member_id, picture_id);
		System.out.println("showReply.do : " + result.toString());
		return result;
	}

	// �ٹ��� ��������
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

	// �����ϴ� ���� ��� ����
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

	// �α����ϱ�
	// 0 ����, -1 ����(�Ⱦ�), -2 ��й�ȣ ����, -3 ���̵����
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
