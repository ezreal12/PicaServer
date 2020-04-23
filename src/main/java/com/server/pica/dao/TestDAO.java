package com.server.pica.dao;

import java.util.List;

import com.server.pica.bean.TestBean;

public interface TestDAO {
	public List<TestBean> test() throws Exception;
}
