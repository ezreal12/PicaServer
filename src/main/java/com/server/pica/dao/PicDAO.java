package com.server.pica.dao;

import java.util.List;

import com.server.pica.dto.CreateAlbumDTO;
import com.server.pica.dto.PicUploadDTO;
import com.server.pica.dto.RegisterMemberDTO;

public interface PicDAO {
	// 데이터베이스에 업로드된 사진 dto 삽입하기
	// 결과는 정수값으로 리턴해줌 성공:0 / 실패 -1
	public int insertPicData(PicUploadDTO dto);
	// 성공:0 / 실패 -1
	public int registerMember(RegisterMemberDTO dto);
	// 성공:0 / 파일 저장 실패 -1 / DB 저장 실패 -2
	public int createAlbum(CreateAlbumDTO dto);
	// 테이블 이름을 입력받아 select * from tableName 후 결과를 dto로 반환
	public List showTable(String tableName);
	// 내 앨범 조회
	public List<CreateAlbumDTO> getMyalbum(int create_p_member_id);
	// 유저 ID로 닉네임 가져오기
	public String getNickNameFromId(int member_id);
	
}
