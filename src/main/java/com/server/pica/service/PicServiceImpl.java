package com.server.pica.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.server.pica.dao.PicDAO;
import com.server.pica.dto.CreateAlbumDTO;
import com.server.pica.dto.PicUploadDTO;
import com.server.pica.dto.RegisterMemberDTO;
import com.server.pica.util.FileUtil;
// 새 서비스 구현시 서비스라고 명시해주는걸 잊지말자
// 복창한다 스프링의 애노테이션은 생명!
// @Repository 없으면 AutoWired 애노테이션 쓰는코드 반드시 에러남 중요
@Repository
public class PicServiceImpl implements PicService {
	//윈도우용 테스트 파일 저장 절대 경로
	//private static final String UPLOAD_PATH = "C:\\Users\\SiuKim\\Desktop\\sample";
	//리눅스용
	// 주의! : 절대 경로로 파일을 저장할수있는대신 해당 경로에 쓰기권한이 반드시 필요함 chmod 777 filetest
	
	public static final int UPLOAD_ERROR_FILE = -1;
	// DB에서 데이터를 찾지 못했을때 (select에서 값이 안나왔을때)
	public static final int NOT_FOUND_DATA = -1;
	public static final int UPLOAD_ERROR_DATABASE = -2;
	public static final int UPLOAD_OK = 0;
	@Autowired
	PicDAO dao;

	// 컨트롤러부터 파일 입력받고 파일저장
		// 파일 저장 후엔 DB에 데이터를 입력하고 결과를 리턴함
		// 성공:0 / 파일 저장실패 -1 / DB에러 : -2
	
	@Override
	public int savePicture(int p_member_id, int p_album_id, MultipartFile uploadfile,String savePath) {
		PicUploadDTO dto = new PicUploadDTO();
		dto.setP_album_id(p_album_id);
		dto.setP_member_id(p_member_id);
		//1. 파일 저장
		dto=FileUtil.saveFile(uploadfile,dto,savePath);
		if(dto==null)
			return UPLOAD_ERROR_FILE;
		//2. DB에 정보입력
		int result=dao.insertPicData(dto);
		//DB 에러 발생시
		if(result<0)
			return UPLOAD_ERROR_DATABASE;
		return UPLOAD_OK;
	}
	
	
	@Override
	public int registerMember(RegisterMemberDTO dto) {
		//2. DB에 정보입력
		int result=dao.registerMember(dto);
		//DB 에러 발생시
		if(result<0)
			return UPLOAD_ERROR_DATABASE;
		return UPLOAD_OK;
	}
	
	@Override
	public int createAlbum(CreateAlbumDTO dto,MultipartFile uploadfile,String savePath) {
		//1. 파일 저장 realFileName= 서버에 저장된 파일명
		String realFileName=FileUtil.saveFile(uploadfile,savePath);
		if(realFileName==null)
			return UPLOAD_ERROR_FILE;
		// 서버에 저장된 파일명 dto에 입력
		dto.setDefaultPicture(realFileName);
		//2. DB에 정보입력
		int result=dao.createAlbum(dto);
		//DB 에러 발생시
		if(result<0)
			return UPLOAD_ERROR_DATABASE;
		return UPLOAD_OK;
	}
	
	@Override
	public List showTable(String tableName) {
		return dao.showTable(tableName);
	}
	
	@Override
	public List<CreateAlbumDTO> getMyalbum(int create_p_member_id) {
		return dao.getMyalbum(create_p_member_id);
	}
	
}
