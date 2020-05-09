package com.server.pica.dao;

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
	
	
}
