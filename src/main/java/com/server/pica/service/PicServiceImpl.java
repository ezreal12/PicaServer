package com.server.pica.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.server.pica.dao.PicDAO;
import com.server.pica.dto.CreateAlbumDTO;
import com.server.pica.dto.MyAlbumDTO;
import com.server.pica.dto.PicUploadDTO;
import com.server.pica.dto.PictureDTO;
import com.server.pica.dto.RegisterMemberDTO;
import com.server.pica.dto.ShowPictureResultVO;
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
	public static final int ERROR_DATABASE = -2;
	public static final int NO_PERMISSOIN = -3;
	public static final int REQUEST_OK = 0;
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
			return ERROR_DATABASE;
		return REQUEST_OK;
	}
	
	
	@Override
	public int registerMember(RegisterMemberDTO dto) {
		//2. DB에 정보입력
		int result=dao.registerMember(dto);
		//DB 에러 발생시
		if(result<0)
			return ERROR_DATABASE;
		return REQUEST_OK;
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
			return ERROR_DATABASE;
		return REQUEST_OK;
	}
	
	@Override
	public List showTable(String tableName) {
		return dao.showTable(tableName);
	}
	
	@Override
	public List<MyAlbumDTO> getMyalbum(int create_p_member_id) {
		List<CreateAlbumDTO> list = dao.getMyalbum(create_p_member_id);
		if (list==null) return null;
		List<MyAlbumDTO> result = new ArrayList<MyAlbumDTO>();
		for(CreateAlbumDTO d : list) {
			String nick = dao.getNickNameFromId(d.getCreate_p_member_id());
			MyAlbumDTO data = new MyAlbumDTO(d, nick);
			result.add(data);
		}
		return result;
	}
	// 앨범 id를 입력받고 앨범에 들어있는 사진 데이터 전부 가져오기
	// 0 성공(), -1 앨범없음, -2 가져오기 실패, -3 권한없음
	@Override
	public ShowPictureResultVO showPicture(int album_id,int member_id) {
		ShowPictureResultVO result=new ShowPictureResultVO();
		List<PictureDTO> list = dao.showPicture(album_id);
		if (list==null) {
			result.setCode(NOT_FOUND_DATA);
			return result;
		}
		/*
		 * TODO : member_id는 p_member_id의 값으로 확인 (추후에 친구초대 기능구현시 AlbumMember에서 찾아야함)
			내 앨범보기도 create_p_member_id가 아닌 AlbumMember에서 찾아야함 
		 * */
		// 임시로 멤버 ID가 맞는지 확인해서 인증
		int pMemberID = list.get(0).getP_member_id();
		if(pMemberID!=member_id) {
			result.setCode(NO_PERMISSOIN);
			return result;
		}
		result.setCode(REQUEST_OK);
		result.setResult(list);
		return result;
	}
	
}
