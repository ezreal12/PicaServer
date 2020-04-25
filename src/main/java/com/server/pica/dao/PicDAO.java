package com.server.pica.dao;

import com.server.pica.dto.PicUploadDTO;

public interface PicDAO {
	// 데이터베이스에 업로드된 사진 dto 삽입하기
	// 결과는 정수값으로 리턴해줌 성공:0 / 실패 -1
	public int insertPicData(PicUploadDTO dto);
}
