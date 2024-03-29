package com.server.pica.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.server.pica.dto.CreateAlbumDTO;
import com.server.pica.dto.LikePictureDTO;
import com.server.pica.dto.LoginVO;
import com.server.pica.dto.MyAlbumDTO;
import com.server.pica.dto.PicUploadDTO;
import com.server.pica.dto.RegisterMemberDTO;
import com.server.pica.dto.ReplyDTO;
import com.server.pica.dto.ReplyDTOWrapper;
import com.server.pica.dto.ShowPictureDataResultVO;
import com.server.pica.dto.ShowPictureResultVO;
import com.server.pica.dto.ShowReplyResultVO;

public interface PicService {
	// 컨트롤러부터 파일 입력받고 파일저장
	// 파일 저장 후엔 DB에 데이터를 입력하고 결과를 리턴함
	// 성공:0 / 파일 저장실패 -1 / DB에러 : -2
	public int savePicture(PicUploadDTO dto, String tags, MultipartFile uploadfile, String savePath);

	// 성공:0 / 실패 -1
	public int registerMember(RegisterMemberDTO dto);

	// 성공:0 / 파일 저장 실패 -1 / DB 저장 실패 -2
	public int createAlbum(CreateAlbumDTO dto, MultipartFile uploadfile, String savePath);

	// 테이블 이름을 입력받아 select * from tableName 후 결과를 dto로 반환
	public List showTable(String tableName);

	// 내 앨범 조회
	public List<MyAlbumDTO> getMyalbum(int create_p_member_id);

	// 앨범 id를 입력받고 앨범에 들어있는 사진 데이터 전부 가져오기
	public ShowPictureResultVO showPictureList(int album_id, int member_id);

	// 사진 id를 입력받고 사진 1개 조회해서 리턴하기
	public ShowPictureDataResultVO showPicture(int picture_id, int member_id);

	// 0 성공, -1 에러(안씀), -2 비밀번호 오류, -3 아이디없음
	public LoginVO login(String email, String password);

	// 성공:0 / 실패 -1
	// 좋아요 정보 삽입하기
	public int addLikePicture(LikePictureDTO dto);

	// 멤버 id를 입력받고 해당 유저가 좋아하는 사진 데이터 전부 가져오기
	public ShowPictureResultVO showLikePictureList(int member_id);

	// 댓글 입력받기
	// 성공:0 / 실패 -1
	public int addReply(int member_id, int picture_id, String reply_text);

	// 댓글 지우기
	// 멤버 id로 작성자인지 확인하고 작성자가 맞으면 그때 삭제함
	// 성공:0 / 실패 -1
	public int deleteReply(int member_id, int reply_id);
	
	// 사진 1장 지우기
	public int deletePicData(int member_id, int picture_id);
	
	//<!-- 사진 1개의 댓글 전부 가져오기-->
	//멤버 id는 해당 유저의 닉네임을 가져오는데, 자기자신이 작성했는지 여부를 확인하는곳에 쓰임
	public ShowReplyResultVO getReply(int member_id,int picture_id);

}
