package com.server.pica.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.server.pica.dto.CreateAlbumDTO;
import com.server.pica.dto.PicUploadDTO;
import com.server.pica.dto.RegisterMemberDTO;
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
	// 유저 ID로 닉네임 가져오기
	@Override
	public String getNickNameFromId(int member_id) {
		return (String)sqlSession.selectOne(namespace+".getNickNameFromId", member_id);
	}
   
   
   
}
