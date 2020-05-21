package com.server.pica.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.server.pica.dto.CreateAlbumDTO;
import com.server.pica.dto.MyAlbumDTO;
import com.server.pica.dto.PicUploadDTO;
import com.server.pica.dto.RegisterMemberDTO;
import com.server.pica.dto.ShowPictureResultVO;


public interface PicService {
	// 컨트롤러부터 파일 입력받고 파일저장
	// 파일 저장 후엔 DB에 데이터를 입력하고 결과를 리턴함
	// 성공:0 / 파일 저장실패 -1 / DB에러 : -2
	public int savePicture(PicUploadDTO dto,String tags, MultipartFile uploadfile,String savePath);
	// 성공:0 / 실패 -1
	public int registerMember(RegisterMemberDTO dto);
	// 성공:0 / 파일 저장 실패 -1 / DB 저장 실패 -2
	public int createAlbum(CreateAlbumDTO dto,MultipartFile uploadfile,String savePath);
	// 테이블 이름을 입력받아 select * from tableName 후 결과를 dto로 반환
	public List showTable(String tableName);
	// 내 앨범 조회
	public List<MyAlbumDTO> getMyalbum(int create_p_member_id);
	// 앨범 id를 입력받고 앨범에 들어있는 사진 데이터 전부 가져오기
	public ShowPictureResultVO showPicture(int album_id,int member_id);
	
}
