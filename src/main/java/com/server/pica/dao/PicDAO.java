package com.server.pica.dao;

import java.util.List;

import com.server.pica.dto.AlbumMemberDTO;
import com.server.pica.dto.CreateAlbumDTO;
import com.server.pica.dto.PicUploadDTO;
import com.server.pica.dto.PictureDTO;
import com.server.pica.dto.RegisterMemberDTO;

public interface PicDAO {
	// 데이터베이스에 업로드된 사진 dto 삽입하기
		// 결과는 정수값으로 리턴해줌 성공:0 / 실패 -1
		// 1. 사진 관련 데이터를 받아 입력
		// 2. 태그 데이터가 있을경우 태그 데이터도 입력 / 없으면 넘어감.
	public int insertPicData(PicUploadDTO dto,List<String> tags);
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
	// 앨범 id를 입력받고 앨범에 들어있는 사진 데이터 전부 가져오기
	public List<PictureDTO> showPictureList(int album_id);
	// 사진 id를 입력받고 사진 정보를 가져오기
	public PictureDTO showPicture(int picture_id);
	// 앨범 id를 입력받고 해당 앨범 정보가져오기
	public CreateAlbumDTO getAlbum(int album_id);
	//멤버 id 입력받아서 해당멤버가 속한 앨범 정보를 담는 album_member 가져오기
	//주로 권한 체크시 사용
	public List<AlbumMemberDTO> getAlbumMember(int member_id);
	// Email 주소로 해당되는 멤버 정보 가져오기
	// 실제 로그인 기능에 사용
	public RegisterMemberDTO getMemberFromEmail(String email);
}
