package com.server.pica.dao;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.server.pica.dto.CreateAlbumDTO;
import com.server.pica.dto.PicUploadDTO;
import com.server.pica.dto.RegisterMemberDTO;
//@Repository 없으면 AutoWired 애노테이션 쓰는코드 반드시 에러남 중요
@Repository
public class PicDAOImpl implements PicDAO {
	
	// 사용할 맵퍼.xml의 네임스페이스 명
	private static final String namespace = "com.server.pica.PicAMapper";
		
	@Inject // DB 커넷션과 클로즈를 자동으로 해주는 세션
	private SqlSession sqlSession;
	
	// 데이터베이스에 업로드된 사진 dto 삽입하기
	// 결과는 정수값으로 리턴해줌 성공:0 / 실패 -1
   @Override
   public int insertPicData(PicUploadDTO dto) {
	   try {
		   sqlSession.insert(namespace+".uploadPic",dto);
	} catch (Exception e) {
		e.printStackTrace();
		return -1;
	}
	   return 0;
   }
   // 성공:0 / 실패 -1
	@Override
	public int registerMember(RegisterMemberDTO dto) {
		try {
			   sqlSession.insert(namespace+".registerMember",dto);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		   return 0;
	}
	// 성공:0 / 파일 저장 실패 -1 / DB 저장 실패 -2
	@Override
	public int createAlbum(CreateAlbumDTO dto) {
		try {
			   sqlSession.insert(namespace+".createAlbum",dto);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		   return 0;
	}
   
   
   
   
   
}
