package com.server.pica.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.server.pica.bean.TestBean;

@Repository // DAO�� �������� �νĽ�Ŵ
public class TestDAOImpl implements TestDAO {
	// ����� ����.xml�� ���ӽ����̽� ��
	private static final String namespace = "com.server.pica.PicAMapper";
	
	@Inject // DB Ŀ�ݼǰ� Ŭ��� �ڵ����� ���ִ� ����
	private SqlSession sqlSession;
	
	public List<TestBean> test() throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(namespace+".test");
	}

	
}
