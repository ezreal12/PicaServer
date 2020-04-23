package com.server.pica.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.server.pica.bean.TestBean;

@Repository // DAO를 스프링에 인식시킴
public class TestDAOImpl implements TestDAO {
	// 사용할 맵퍼.xml의 네임스페이스 명
	private static final String namespace = "com.server.pica.PicAMapper";
	
	@Inject // DB 커넷션과 클로즈를 자동으로 해주는 세션
	private SqlSession sqlSession;
	
	public List<TestBean> test() throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(namespace+".test");
	}

	
}
