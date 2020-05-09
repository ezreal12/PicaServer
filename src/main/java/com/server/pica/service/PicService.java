package com.server.pica.service;

import org.springframework.web.multipart.MultipartFile;

import com.server.pica.dto.RegisterMemberDTO;


public interface PicService {
	// 컨트롤러부터 파일 입력받고 파일저장
	// 파일 저장 후엔 DB에 데이터를 입력하고 결과를 리턴함
	// 성공:0 / 파일 저장실패 -1 / DB에러 : -2
	public int savePicture(int p_member_id,int p_album_id, MultipartFile uploadfile);
	// 성공:0 / 실패 -1
	public int registerMember(RegisterMemberDTO dto);
	// 성공:0 / 파일 저장 실패 -1 / DB 저장 실패 -2
	//public int createAlbum(CreateAlbumDTO dto);
	
	
}
