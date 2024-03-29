package com.server.pica.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.server.pica.dto.AlbumMemberDTO;
import com.server.pica.dto.CreateAlbumDTO;
import com.server.pica.dto.LikePictureDTO;
import com.server.pica.dto.PicUploadDTO;
import com.server.pica.dto.PictureDTO;
import com.server.pica.dto.RegisterMemberDTO;
import com.server.pica.dto.ReplyDTO;
//@Repository 없으면 AutoWired 애노테이션 쓰는코드 반드시 에러남 중요
@Repository
public class PicDAOImpl implements PicDAO {
	
	/*
	 * 주의 : insert에 not null 속성등을 빼먹는다던가
	 * sql문이나 안에 들어간 데이터가 이상하면 바로 ClassNotFoundException 에러를 뿜는데
	 * 이 에러는 특정 원인을 가르쳐주지 않는 범용적인 에러라 직접 수수께끼 찾기 해야함.
	 * */
	// 사용할 맵퍼.xml의 네임스페이스 명
	private static final String namespace = "com.server.pica.PicAMapper";
	
	@Inject // DB 커넷션과 클로즈를 자동으로 해주는 세션
	private SqlSession sqlSession;
	
	// 데이터베이스에 업로드된 사진 dto 삽입하기
	// 결과는 정수값으로 리턴해줌 성공:0 / 실패 -1
	// 1. 사진 관련 데이터를 받아 입력
	// 2. 태그 데이터가 있을경우 태그 데이터도 입력 / 없으면 넘어감.
   @Override
   public int insertPicData(PicUploadDTO dto,List<String> tags) {
	   System.out.println("------ TEST HS 2 !!!! : "+dto.toString());
	   try {
		   sqlSession.insert(namespace+".uploadPic",dto);
	} catch (Exception e) {
		e.printStackTrace();
		return -1;
	}
	   // 입력받은 태그가 있으면 입력
	   if(tags!=null) {
		   for(String tag : tags) {
			   // 1. 태그 중복확인
			   Object result = sqlSession.selectOne(namespace+".getTagIdFromTag",tag);
			   // 2. 태그가 존재하지 않을경우 입력
			   if(result==null) {
			   	try {
			   		 sqlSession.insert(namespace+".uploadTagOne",tag);
			   	} catch (Exception e) {
			   		e.printStackTrace();
			   		// DB 에러시 -2 리턴
			   		return -2;
			   	}
			   } 
		   }
	   }
	   return 0;
   }
   
 
   // 성공:0 / 실패 -1
	/*
	 * 주의 : insert에 not null 속성등을 빼먹는다던가
	 * sql문이나 안에 들어간 데이터가 이상하면 바로 ClassNotFoundException 에러를 뿜는데
	 * 이 에러는 특정 원인을 가르쳐주지 않는 범용적인 에러라 직접 수수께끼 찾기 해야함.
	 * */
	@Override
	public int registerMember(RegisterMemberDTO dto) {
		System.out.println(dto.toString());
		try {
			   sqlSession.insert(namespace+".registerMember",dto);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		   return 0;
	}
	// 성공:0 / 파일 저장 실패 -1 / DB 저장 실패 -2
	/*
	 * 주의 : insert에 not null 속성등을 빼먹는다던가
	 * sql문이나 안에 들어간 데이터가 이상하면 바로 ClassNotFoundException 에러를 뿜는데
	 * 이 에러는 특정 원인을 가르쳐주지 않는 범용적인 에러라 직접 수수께끼 찾기 해야함.
	 * */
	@Override
	public int createAlbum(CreateAlbumDTO dto) {
		System.out.println(dto.toString());
		try {
			   sqlSession.insert(namespace+".createAlbum",dto);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		   return 0;
	}

	@Override
	public List showTable(String tableName) {
		System.out.println(tableName);
		if(tableName.equals("PICTURE")) {
			return sqlSession.selectList(namespace+".showPic");
		}
		if(tableName.equals("MEMBER")) {
			return sqlSession.selectList(namespace+".showMember");
		}
		if(tableName.equals("ALBUM")) {
			return sqlSession.selectList(namespace+".showAlbum");
		}
		else {
			return null;
		}
	}
   
	@Override
	public List<CreateAlbumDTO> getMyalbum(int create_p_member_id) {
		System.out.println("create_p_member_id: "+create_p_member_id);
		return sqlSession.selectList(namespace+".myAlbum",create_p_member_id);
	}
	
	@Override
	public CreateAlbumDTO getAlbum(int album_id) {
		System.out.println("getAlbum album_id : "+album_id);
		return sqlSession.selectOne(namespace+".getAlbum",album_id);
	}
	
	
	// 유저 ID로 닉네임 가져오기
	@Override
	public String getNickNameFromId(int member_id) {
		return (String)sqlSession.selectOne(namespace+".getNickNameFromId", member_id);
	}
   
	// 앨범 id를 입력받고 앨범에 들어있는 사진 데이터 전부 가져오기
	@Override
	public List<PictureDTO> showPictureList(int album_id) {
		System.out.println("showPictureList  album_id: "+album_id);
		return sqlSession.selectList(namespace+".showPicture",album_id);
	}
	// 사진 id를 입력받고 사진 정보를 가져오기
	@Override
	public PictureDTO showPicture(int picture_id) {
		System.out.println("showPicture  picture_id: "+picture_id);
		return (PictureDTO)sqlSession.selectOne(namespace+".getPicture",picture_id);
	}
	
	@Override
	public List<AlbumMemberDTO> getAlbumMember(int member_id) {
		System.out.println("getAlbumMember  member_id: "+member_id);
		return sqlSession.selectList(namespace+".getAlbum_member",member_id);
	}
	
	@Override
	public RegisterMemberDTO getMemberFromEmail(String email) {
		System.out.println("getMemberFromEmail  email: "+email);
		return (RegisterMemberDTO)sqlSession.selectOne(namespace+".getMemberFromEmail",email);
	}
	
	// 성공:0 / 실패 -1
	//좋아요 정보 삽입하기
	@Override
	public int addLikePicture(LikePictureDTO dto) {
		System.out.println(dto.toString());
		try {
			   sqlSession.insert(namespace+".addLikePicture",dto);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		   return 0;
	}
	// 성공:0 / 실패 -1
		//좋아요 정보 지우기
	@Override
	public int deleteLikePicture(LikePictureDTO dto) {
		System.out.println(dto.toString());
		try {
			   sqlSession.delete(namespace+".deleteLikePicture",dto);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		   return 0;
	}
	@Override
	public LikePictureDTO serchLikePicture(LikePictureDTO dto) {
		System.out.println("serchLikePicture  dto: "+dto.toString());
		return (LikePictureDTO)sqlSession.selectOne(namespace+".serchLikePicture",dto);
	}
	
	@Override
	public List<LikePictureDTO> getLike(int member_id) {
		System.out.println("getLikePicture  member_id: "+member_id);
		return sqlSession.selectList(namespace+".getLike",member_id);
	}
	//<!-- 사진 1개 조회하기 -->
	@Override
	public PictureDTO getPicture(int picture_id) {
		System.out.println("getPicture  picture_id: "+picture_id);
		return (PictureDTO)sqlSession.selectOne(namespace+".getPicture",picture_id);
	}
	//댓글 입력받기
	// 성공:0 / 실패 -1
	@Override
	public int addReply(ReplyDTO dto) {
		System.out.println(dto.toString());
		try {
			   sqlSession.insert(namespace+".addReply",dto);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		   return 0;
	}
	
	@Override
	public int deleteReply(int reply_id) {
		System.out.println(reply_id);
		try {
			   sqlSession.delete(namespace+".deleteReply",reply_id);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		   return 0;
	}
	@Override
	public ReplyDTO serchReply(ReplyDTO dto) {
		System.out.println("serchReply  dto: "+dto.toString());
		return (ReplyDTO)sqlSession.selectOne(namespace+".serchReply",dto);
	}
	//<!-- 사진 1개의 댓글 전부 가져오기-->
	@Override
	public List<ReplyDTO> getReply(int picture_id) {
		System.out.println("getReply  picture_id: "+picture_id);
		return sqlSession.selectList(namespace+".getReply",picture_id);
	}
	
	@Override
	public int deletePicData(int picture_id) {
		System.out.println("deletePicData picture_id : "+picture_id);
		try {
			   sqlSession.delete(namespace+".deletePicture",picture_id);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		   return 0;
	}
	
	
}
