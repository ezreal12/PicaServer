package com.server.pica.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.server.pica.dao.PicDAO;
import com.server.pica.dto.AlbumMemberDTO;
import com.server.pica.dto.CreateAlbumDTO;
import com.server.pica.dto.LoginVO;
import com.server.pica.dto.MyAlbumDTO;
import com.server.pica.dto.PicUploadDTO;
import com.server.pica.dto.PictureDTO;
import com.server.pica.dto.PictureDTOWrapper;
import com.server.pica.dto.RegisterMemberDTO;
import com.server.pica.dto.ShowPictureDataResultVO;
import com.server.pica.dto.ShowPictureResultVO;
import com.server.pica.util.FileUtil;
import com.server.pica.util.StringUtil;
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
	// 열람 권한 없음
	public static final int NO_PERMISSOIN = -3;
	// (로그인 기능) 비밀번호 틀림
	public static final int PASSWARD_ERROR = -2;
	// (로그인 기능) ID 없음
	public static final int NOT_FOUND_ID = -3;
	// 요청 완료
	public static final int REQUEST_OK = 0;
	// 로그인 완료
	public static final int LOGIN_OK = 0;
	@Autowired
	PicDAO dao;

	// 컨트롤러부터 파일 입력받고 파일저장
		// 파일 저장 후엔 DB에 데이터를 입력하고 결과를 리턴함
		// 성공:0 / 파일 저장실패 -1 / DB에러 : -2
	
	@Override
	public int savePicture(PicUploadDTO dto,String tags, MultipartFile uploadfile,String savePath) {
		//insertPicData(PicUploadDTO dto,List<String> tags);
		//1. 파일 저장
		dto=FileUtil.saveFile(uploadfile,dto,savePath);
		if(dto==null)
			return UPLOAD_ERROR_FILE;
		List<String> tagList;
		if(tags==null) tagList=null;
		else {
			// DB 들어가기 전 tag 파싱하기
			tagList = StringUtil.insertTags(tags);
		}		
		//2. DB에 정보입력
		int result=dao.insertPicData(dto,tagList);		
		//DB 에러 발생시
		if(result<0) {
			System.out.println("! -- insertPicData 태그 입력 DB 에러 코드 : "+result);
			return ERROR_DATABASE;
		}
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
	// 멤버 ID로 앨범에 권한이 있는지 확인하기
	// false : 권한없음 , true : 권한 있음.
	private boolean checkPermissoinAlbum(int member_id,int album_id) {
		List<AlbumMemberDTO> list = dao.getAlbumMember(member_id);
		if (list==null||list.size()==0) return false;
		for (AlbumMemberDTO d : list) {
			if(d.getA_album_id()==album_id) {
				return true;
			}
		}
		return false;
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
	// showPicture.do
	@Override
	public ShowPictureResultVO showPictureList(int album_id,int member_id) {
		ShowPictureResultVO result=new ShowPictureResultVO();
		List<PictureDTO> list = dao.showPictureList(album_id);
		if (list==null||list.size()==0) {
			result.setCode(NOT_FOUND_DATA);
			return result;
		}
		// 앨범 권한을 확인해서 권한이 없을경우
		if(!checkPermissoinAlbum(member_id, album_id)) {
			result.setCode(NO_PERMISSOIN);
			return result;
		}
		// 앨범 이름, 앨범설명, 앨범 타이틀사진 담기
		CreateAlbumDTO album = dao.getAlbum(album_id);
		result.setName(album.getName());
		result.setDescription(album.getDescription());
		// 사진은 컨트롤러단에서 http 경로붙여서 줍시다
		result.setDefaultPicture(album.getDefaultPicture());
		result.setCode(REQUEST_OK);
		result.setResult(list);
		return result;
	}
	// 사진 id를 입력받고 사진 1개 조회해서 리턴하기
	@Override
	public ShowPictureDataResultVO showPicture(int picture_id, int member_id) {
		ShowPictureDataResultVO result = new ShowPictureDataResultVO();
		PictureDTO dto = dao.showPicture(picture_id);
		if (dto==null) {
			result.setCode(NOT_FOUND_DATA);
			return result;
		}
		/*
		 * TODO : member_id는 p_member_id의 값으로 확인 (추후에 친구초대 기능구현시 AlbumMember에서 찾아야함)
			추후 친구초대 기능이 구현되면 가능함 (앨범에 소속된 멤버를 담고있는 테이블이
		 * */
		// 1. 해당 사진이 속한 앨범 ID 조회하기
		int albumId = dto.getP_album_id();
		// 2. 해당 앨범 ID를 볼수있는 권한이 사용자에게 있는가 조회하기
		if(!checkPermissoinAlbum(member_id, albumId)) {
			result.setCode(NO_PERMISSOIN);
			return result;
		}
		// 사진 게시자 닉네임 가져오기
		PictureDTOWrapper dtoWrapper = new PictureDTOWrapper(dto);
		dtoWrapper.setNickName(dao.getNickNameFromId(dtoWrapper.getP_member_id()));
		result.setCode(REQUEST_OK);
		result.setResult(dtoWrapper);
		return result;
	}
	// 0 성공, -1 에러(안씀), -2 비밀번호 오류, -3 아이디없음
	@Override
	public LoginVO login(String email, String password) {
		RegisterMemberDTO member = dao.getMemberFromEmail(email);
		LoginVO result = new LoginVO();
		
		if(member==null) {
			result.setCode(NOT_FOUND_ID);
			return result;
		}
		if(!password.equals(member.getPassword())) {
			result.setCode(PASSWARD_ERROR);
			return result;
		}
		result.setCode(LOGIN_OK);
		result.setMember_id(member.getMember_id());
		return result;
	}
	
}
