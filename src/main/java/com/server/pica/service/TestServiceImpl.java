package com.server.pica.service;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.server.pica.bean.TestBean;
import com.server.pica.dao.TestDAO;

@Repository // DAO를 스프링에 인식시킴
public class TestServiceImpl implements TestService {
	
	@Inject 
	private TestDAO dao;
	
	@Override
	public List<TestBean> test() throws Exception {
		// TODO Auto-generated method stub
		return dao.test();
	}

	
}
