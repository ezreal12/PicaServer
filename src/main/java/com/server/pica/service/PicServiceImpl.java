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
import com.server.pica.dto.LikePictureDTO;
import com.server.pica.dto.LoginVO;
import com.server.pica.dto.MyAlbumDTO;
import com.server.pica.dto.PicUploadDTO;
import com.server.pica.dto.PictureDTO;
import com.server.pica.dto.PictureDTOWrapper;
import com.server.pica.dto.RegisterMemberDTO;
import com.server.pica.dto.ReplyDTO;
import com.server.pica.dto.ReplyDTOWrapper;
import com.server.pica.dto.ShowPictureDataResultVO;
import com.server.pica.dto.ShowPictureResultVO;
import com.server.pica.dto.ShowReplyResultVO;
import com.server.pica.util.FileUtil;
import com.server.pica.util.StringUtil;

// 새 서비스 구현시 서비스라고 명시해주는걸 잊지말자
// 복창한다 스프링의 애노테이션은 생명!
// @Repository 없으면 AutoWired 애노테이션 쓰는코드 반드시 에러남 중요
@Repository
public class PicServiceImpl implements PicService {
	// 윈도우용 테스트 파일 저장 절대 경로
	// private static final String UPLOAD_PATH =
	// "C:\\Users\\SiuKim\\Desktop\\sample";
	// 리눅스용
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
	public int savePicture(PicUploadDTO dto, String tags, MultipartFile uploadfile, String savePath) {
		// insertPicData(PicUploadDTO dto,List<String> tags);
		// 1. 파일 저장
		dto = FileUtil.saveFile(uploadfile, dto, savePath);
		if (dto == null)
			return UPLOAD_ERROR_FILE;
		List<String> tagList;
		if (tags == null)
			tagList = null;
		else {
			// DB 들어가기 전 tag 파싱하기
			tagList = StringUtil.insertTags(tags);
		}
		// 2. DB에 정보입력
		int result = dao.insertPicData(dto, tagList);
		// DB 에러 발생시
		if (result < 0) {
			System.out.println("! -- insertPicData 태그 입력 DB 에러 코드 : " + result);
			return ERROR_DATABASE;
		}
		return REQUEST_OK;
	}

	@Override
	public int registerMember(RegisterMemberDTO dto) {
		// 2. DB에 정보입력
		int result = dao.registerMember(dto);
		// DB 에러 발생시
		if (result < 0)
			return ERROR_DATABASE;
		return REQUEST_OK;
	}

	@Override
	public int createAlbum(CreateAlbumDTO dto, MultipartFile uploadfile, String savePath) {
		// 1. 파일 저장 realFileName= 서버에 저장된 파일명
		String realFileName = FileUtil.saveFile(uploadfile, savePath);
		if (realFileName == null)
			return UPLOAD_ERROR_FILE;
		// 서버에 저장된 파일명 dto에 입력
		dto.setDefaultPicture(realFileName);
		// 2. DB에 정보입력
		int result = dao.createAlbum(dto);
		// TODO : 3. 앨범 생성한사람을 앨범 멤버에 추가 (열람권한 부여)
		/*
		 * 6월 8일 기준 여기서 생성한 앨범의 숫자 ID를 알아내서 album_member 테이블에 앨범 ID와 멤버 ID를 던져줘야하는데 바로
		 * 윗줄에서 생성한 앨범의 숫자 ID를 알아낼수있는 방법이 없음 추후 DB 구조 변경요망
		 */
		// DB 에러 발생시
		if (result < 0)
			return ERROR_DATABASE;
		return REQUEST_OK;
	}

	@Override
	public List showTable(String tableName) {
		return dao.showTable(tableName);
	}

	// 멤버 ID로 앨범에 권한이 있는지 확인하기
	// false : 권한없음 , true : 권한 있음.
	private boolean checkPermissoinAlbum(int member_id, int album_id) {
		List<AlbumMemberDTO> list = dao.getAlbumMember(member_id);
		if (list == null || list.size() == 0)
			return false;
		for (AlbumMemberDTO d : list) {
			if (d.getA_album_id() == album_id) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<MyAlbumDTO> getMyalbum(int create_p_member_id) {
		List<CreateAlbumDTO> list = dao.getMyalbum(create_p_member_id);
		if (list == null)
			return null;
		List<MyAlbumDTO> result = new ArrayList<MyAlbumDTO>();
		for (CreateAlbumDTO d : list) {
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
	public ShowPictureResultVO showPictureList(int album_id, int member_id) {
		ShowPictureResultVO result = new ShowPictureResultVO();
		List<PictureDTO> list = dao.showPictureList(album_id);

		// 앨범 권한을 확인해서 권한이 없을경우
		if (!checkPermissoinAlbum(member_id, album_id)) {
			result.setCode(NO_PERMISSOIN);
			return result;
		}
		// 검색 결과가 0일경우
		if (list == null || list.size() == 0) {
			/*
			 * DB 검색시 해당 앨범이 존재하지 않을경우, 해당 앨범은 존재하는데 사진이 존재하지않을경우 둘다 size 0인 배열을 리턴해버림 앨범이
			 * 존재는 할경우 -1이 아니라 0을 리턴하고 데이터를 비워야함
			 */
			// 앨범이 존재하는지 확인
			CreateAlbumDTO d = dao.getAlbum(album_id);
			if (d == null) {
				// 앨범이 아예 존재하지 않으면 해당 앨범없음
				result.setCode(NOT_FOUND_DATA);
			}
			// 앨범이 존재하면 그냥 아래에서 하면됨
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
		if (dto == null) {
			result.setCode(NOT_FOUND_DATA);
			return result;
		}
		/*
		 * TODO : member_id는 p_member_id의 값으로 확인 (추후에 친구초대 기능구현시 AlbumMember에서 찾아야함) 추후
		 * 친구초대 기능이 구현되면 가능함 (앨범에 소속된 멤버를 담고있는 테이블이
		 */
		// 1. 해당 사진이 속한 앨범 ID 조회하기
		int albumId = dto.getP_album_id();
		// 2. 해당 앨범 ID를 볼수있는 권한이 사용자에게 있는가 조회하기
		if (!checkPermissoinAlbum(member_id, albumId)) {
			result.setCode(NO_PERMISSOIN);
			return result;
		}
		// 사진 게시자 닉네임 가져오기
		PictureDTOWrapper dtoWrapper = new PictureDTOWrapper(dto);
		dtoWrapper.setNickName(dao.getNickNameFromId(dtoWrapper.getP_member_id()));

		// 내가 업로드한 사진인가? 알아보기
		// 업로더 ID
		int uploaderId = dto.getP_member_id();
		// 자기가 업로드 한 사진이면
		if (uploaderId == member_id) {
			result.setIsMyUpload('y');
		}
		// 자기가 업로드한 사진이 아닐경우
		else {
			result.setIsMyUpload('n');
		}

		// 내가 사진에 좋아요를 누른 사진인가?
		LikePictureDTO sample = new LikePictureDTO();
		sample.setMember_id(member_id);
		sample.setPicture_id(dto.getPicture_id());
		LikePictureDTO serchResult = dao.serchLikePicture(sample);
		if (serchResult != null) {
			result.setIsLikePicture('y');
		} else {
			result.setIsLikePicture('n');
		}

		result.setCode(REQUEST_OK);
		result.setResult(dtoWrapper);
		return result;
	}

	// 0 성공, -1 에러(안씀), -2 비밀번호 오류, -3 아이디없음
	@Override
	public LoginVO login(String email, String password) {
		RegisterMemberDTO member = dao.getMemberFromEmail(email);
		LoginVO result = new LoginVO();

		if (member == null) {
			result.setCode(NOT_FOUND_ID);
			return result;
		}
		if (!password.equals(member.getPassword())) {
			result.setCode(PASSWARD_ERROR);
			return result;
		}
		result.setCode(LOGIN_OK);
		result.setMember_id(member.getMember_id());
		return result;
	}

	@Override
	public int addLikePicture(LikePictureDTO dto) {
		// 기존 좋아요 한 기록이 있는지 확인하기
		LikePictureDTO getData = dao.serchLikePicture(dto);
		// 정보가 없을경우
		if (getData == null) {
			// DB에 정보입력
			int result = dao.addLikePicture(dto);
			// DB 에러 발생시
			if (result < 0)
				return ERROR_DATABASE;
			return REQUEST_OK;
		}
		// 정보가 있을경우 좋아요 정보 제거
		else {
			// DB에 정보입력
			int result = dao.deleteLikePicture(dto);
			// DB 에러 발생시
			if (result < 0)
				return ERROR_DATABASE;
			return REQUEST_OK;

		}
	}

	// 멤버 id를 입력받고 해당 유저가 좋아하는 사진 데이터 전부 가져오기

	@Override
	public ShowPictureResultVO showLikePictureList(int member_id) {
		ShowPictureResultVO result = new ShowPictureResultVO();
		// 1. 해당 유저의 좋아요 정보 전부 가져오기
		List<LikePictureDTO> likeList = dao.getLike(member_id);
		if (likeList == null || likeList.size() == 0) {
			result.setCode(NOT_FOUND_DATA);
			return result;
		}
		// 2. 좋아요한 사진 전부 가져오기
		// private List<PictureDTO> result;
		List<PictureDTO> picArr = new ArrayList<PictureDTO>();
		for (LikePictureDTO d : likeList) {
			// 사진 1개 id로 정보 가져오기
			PictureDTO p = dao.getPicture(d.getPicture_id());
			if (p != null) {
				picArr.add(p);
			}
		}

		if (picArr.size() == 0) {
			result.setCode(NOT_FOUND_DATA);
			return result;
		}
		result.setCode(REQUEST_OK);
		result.setResult(picArr);
		return result;
	}

	@Override
	public int addReply(int member_id, int picture_id, String reply_text) {
		ReplyDTO dto = new ReplyDTO();
		dto.setMember_id(member_id);
		dto.setPicture_id(picture_id);
		dto.setReply_text(reply_text);

		// DB에 정보입력
		int result = dao.addReply(dto);
		// DB 에러 발생시
		if (result < 0)
			return ERROR_DATABASE;
		return REQUEST_OK;
	}

	// 댓글 지우기
	// 멤버 id로 작성자인지 확인하고 작성자가 맞으면 그때 삭제함
	// 성공:0 / 실패 -1 / 권한없음 -3
	@Override
	public int deleteReply(int member_id, int reply_id) {
		ReplyDTO sample = new ReplyDTO();
		sample.setMember_id(member_id);
		sample.setReply_id(reply_id);
		// 1. 권한 확인하기 NO_PERMISSOIN
		ReplyDTO d = dao.serchReply(sample);
		if (d == null) {
			return NO_PERMISSOIN;
		}
		int result = dao.deleteReply(reply_id);
		// DB 에러 발생시
		if (result < 0)
			return ERROR_DATABASE;
		return REQUEST_OK;
	}
	// <!-- 사진 1개의 댓글 전부 가져오기-->
	// 멤버 id는 해당 유저의 닉네임을 가져오는데, 자기자신이 작성했는지 여부를 확인하는곳에 쓰임

	@Override
	public ShowReplyResultVO getReply(int member_id, int picture_id) {
		ShowReplyResultVO result = new ShowReplyResultVO();
		// 1. 댓글부터 전부 가져오기
		List<ReplyDTO> list = dao.getReply(picture_id);
		// 검색 결과가 0일경우
		if (list == null || list.size() == 0) {
			result.setCode(NOT_FOUND_DATA);
			return result;
		}
		// 2. 닉네임 가져오기
		String nick = dao.getNickNameFromId(member_id);
		List<ReplyDTOWrapper> wList = new ArrayList<ReplyDTOWrapper>();
		for(ReplyDTO d : list) {
			// 2. 자기자신이 썼는지 여부 확인하기
			char isMyReply = 'n';
			if(d.getMember_id()==member_id) {
				isMyReply='y';
			}
			ReplyDTOWrapper w = new ReplyDTOWrapper(d);
			w.setNickName(nick);
			w.setIsMyReply(isMyReply);
			wList.add(w);
		}
		result.setNickName(nick);
		result.setCode(REQUEST_OK);
		result.setResult(wList);
		return result;
	}
	
	
	@Override
	public int deletePicData(int member_id, int picture_id) {
		PictureDTO pic = dao.getPicture(picture_id);
		if (pic == null) {
			return NOT_FOUND_DATA;
		}
		else if(pic.getP_member_id()!=member_id) {
			return NO_PERMISSOIN;
		}
		int result = dao.deletePicData(picture_id);
		if(result<0)
			return ERROR_DATABASE;
		else 
			return REQUEST_OK;
	}
	
}
