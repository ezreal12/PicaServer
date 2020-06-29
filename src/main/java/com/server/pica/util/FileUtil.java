package com.server.pica.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.server.pica.dto.CreateAlbumDTO;
import com.server.pica.dto.MyAlbumDTO;
import com.server.pica.dto.PicUploadDTO;
import com.server.pica.dto.PictureDTO;
import com.server.pica.dto.PictureDTOWrapper;
import com.server.pica.dto.ShowPictureDataResultVO;
import com.server.pica.dto.ShowPictureResultVO;

public class FileUtil {
	private static final String IMAGE_SRC = "/resource";
	private static final String IMAGE_PATH = IMAGE_SRC + "/";

	// 파일을 저장하고 저장한 정보를 DTO에 담아 리턴
	public static PicUploadDTO saveFile(MultipartFile file, PicUploadDTO dto, String uploadPath) {
		// TODO : 효과적인 로그 저장법을 고민할 필요가 있음
		File dir = new File(uploadPath);
		// 폴더가 존재하지 않을경우
		if (!dir.exists()) {
			// 폴더 생성
			if (!dir.mkdirs()) {
				// 폴더 생성에서 에러 발생시 로그 남기기
			}
		}

		String fileFullStr = file.getOriginalFilename();
		String fileExt = getFileType(fileFullStr);
		String saveName = fileNameAtDate(fileExt);
		
		// logger.info("saveName: {}",saveName);
		System.out.println("saveName: {" + saveName + "}");

		// 저장할 File 객체를 생성(껍데기 파일)ㄴ
		File saveFile = new File(uploadPath, saveName); // 저장할 폴더 이름, 저장할 파일 이름

		try {
			file.transferTo(saveFile); // 업로드 파일에 saveFile이라는 껍데기 입힘
		} catch (IOException e) {
			// 에러 로그 남기기
			e.printStackTrace();
			return null;
		}
		dto.setFile(saveName);
		return dto;
	}

	// 풀 확장자의 파일명을 입력받아서 파일 확장자만 리턴하기
	// 1234.jpg -> .jpg
	public static String getFileType(String strFileName) {
		int pos = strFileName.lastIndexOf(".");
		String ext = strFileName.substring(pos);
		return ext;
	}
	// 파일확장자만 입력받아서 날짜붙인 파일이름 만들어주기
	// ext = .jpg => 리턴 : 20200629112559.jpg
	public static String fileNameAtDate(String ext) {
		SimpleDateFormat format1 = new SimpleDateFormat ( "yyyyMMddHHmmss");
		String format_time1 = format1.format (System.currentTimeMillis());
		return format_time1+ext;
	}

	// 파일을 저장하고 파일 명을 리턴함
	public static String saveFile(MultipartFile file, String uploadPath) {
		// TODO : 효과적인 로그 저장법을 고민할 필요가 있음
		File dir = new File(uploadPath);
		// 폴더가 존재하지 않을경우
		if (!dir.exists()) {
			// 폴더 생성
			if (!dir.mkdirs()) {
				// 폴더 생성에서 에러 발생시 로그 남기기
			}
		}
		String saveName = file.getOriginalFilename();

		// logger.info("saveName: {}",saveName);
		System.out.println("saveName: {" + saveName + "}");

		// 저장할 File 객체를 생성(껍데기 파일)ㄴ
		File saveFile = new File(uploadPath, saveName); // 저장할 폴더 이름, 저장할 파일 이름

		try {
			file.transferTo(saveFile); // 업로드 파일에 saveFile이라는 껍데기 입힘
		} catch (IOException e) {
			// 에러 로그 남기기
			e.printStackTrace();
			return null;
		}
		return saveName;
	}

	// 앨범 DTO의 리스트를 입력받아 해당 리스트의 DTO 파일 경로에 서버 주소를 추가해서 리턴한다.
	public static List<MyAlbumDTO> insertServerUrlInImages(List<MyAlbumDTO> list, HttpServletRequest request) {
		if (list == null)
			return list;
		for (MyAlbumDTO e : list) {
			if (e.getDefaultPicture() == null)
				continue;
			e.setDefaultPicture(parseImageSrc(e.getDefaultPicture(), request));
		}
		return list;
	}

	// 사진 DTO 1개를 입력받아 해당 리스트의 DTO 파일 경로에 서버 주소를 추가해서 리턴한다.
	public static ShowPictureDataResultVO insertServerUrlInImages(ShowPictureDataResultVO data,
			HttpServletRequest request) {
		PictureDTOWrapper dto = data.getResult();
		if (dto == null)
			return data;
		dto.setFile(parseImageSrc(dto.getFile(), request));
		data.setResult(dto);
		return data;
	}

	// 앨범 DTO의 리스트를 입력받아 해당 리스트의 DTO 파일 경로에 서버 주소를 추가해서 리턴한다.
	public static ShowPictureResultVO insertServerUrlInImages(ShowPictureResultVO vo, HttpServletRequest request) {
		List<PictureDTO> list = vo.getResult();
		if (list == null)
			return vo;
		for (PictureDTO e : list) {
			if (e.getFile() == null)
				continue;
			e.setFile(parseImageSrc(e.getFile(), request));
		}
		vo.setResult(list);
		return vo;
	}

	// 파일 이름을 입력받아서 웹서버경로+파일 이름으로 된 최종경로 리턴하기
	public static String parseImageSrc(String fileName, HttpServletRequest request) {
		String url = request.getRequestURL().toString().replace(request.getRequestURI(), "");
		url = url + request.getContextPath() + IMAGE_PATH;
		return url + fileName;
	}

	// HttpServletRequest을 입력받아 파일 저장경로 리턴하기
	public static String getFileSavePath(HttpServletRequest request) {
		return request.getSession().getServletContext().getRealPath(IMAGE_SRC);
	}
}
