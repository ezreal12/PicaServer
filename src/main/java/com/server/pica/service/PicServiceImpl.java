package com.server.pica.service;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.server.pica.dao.PicDAO;
import com.server.pica.dto.PicUploadDTO;
// 새 서비스 구현시 서비스라고 명시해주는걸 잊지말자
// 복창한다 스프링의 애노테이션은 생명!
// @Repository 없으면 AutoWired 애노테이션 쓰는코드 반드시 에러남 중요
@Repository
public class PicServiceImpl implements PicService {
	//윈도우용 테스트 파일 저장 절대 경로
	//private static final String UPLOAD_PATH = "C:\\Users\\SiuKim\\Desktop\\sample";
	//리눅스용
	// 주의! : 절대 경로로 파일을 저장할수있는대신 해당 경로에 쓰기권한이 반드시 필요함 chmod 777 filetest
	private static final String UPLOAD_PATH = "/home/ubuntu/filetest";
	public static final int UPLOAD_ERROR_FILE = -1;
	public static final int UPLOAD_ERROR_DATABASE = -2;
	public static final int UPLOAD_OK = 0;
	@Autowired
	PicDAO dao;

	// 컨트롤러부터 파일 입력받고 파일저장
		// 파일 저장 후엔 DB에 데이터를 입력하고 결과를 리턴함
		// 성공:0 / 파일 저장실패 -1 / DB에러 : -2
	
	@Override
	public int savePicture(int p_member_id, int p_album_id, MultipartFile uploadfile) {
		PicUploadDTO dto = new PicUploadDTO();
		dto.setP_album_id(p_album_id);
		dto.setP_member_id(p_member_id);
		//1. 파일 저장
		dto=saveFile(uploadfile,dto);
		if(dto==null)
			return UPLOAD_ERROR_FILE;
		//2. DB에 정보입력
		int result=dao.insertPicData(dto);
		//DB 에러 발생시
		if(result<0)
			return UPLOAD_ERROR_DATABASE;
		return UPLOAD_OK;
	}
	//파일을 저장하고 저장한 정보를 DTO에 담아 리턴 
	private PicUploadDTO saveFile(MultipartFile file,PicUploadDTO dto){
		
		// TODO : 효과적인 로그 저장법을 고민할 필요가 있음
		
		File dir = new File(UPLOAD_PATH);
		// 폴더가 존재하지 않을경우
		if(!dir.exists()) {
			//폴더 생성
			if(!dir.mkdirs()) {
				// 폴더 생성에서 에러 발생시 로그 남기기
			}
		}
		
		
	   
	    String saveName = file.getOriginalFilename();

	    //logger.info("saveName: {}",saveName);
	    System.out.println("saveName: {"+saveName+"}");

	    // 저장할 File 객체를 생성(껍데기 파일)ㄴ
	    File saveFile = new File(UPLOAD_PATH,saveName); // 저장할 폴더 이름, 저장할 파일 이름

	    try {
	        file.transferTo(saveFile); // 업로드 파일에 saveFile이라는 껍데기 입힘
	    } catch (IOException e) {
	    	// 에러 로그 남기기
	        e.printStackTrace();
	        return null;
	    }
	    dto.setFile(saveName);
	    dto.setPath(UPLOAD_PATH);
	    return dto;
	} 
	
}
