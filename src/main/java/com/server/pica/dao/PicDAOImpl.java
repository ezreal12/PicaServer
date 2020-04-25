package com.server.pica.dao;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.server.pica.dto.PicUploadDTO;
//@Repository ������ AutoWired �ֳ����̼� �����ڵ� �ݵ�� ������ �߿�
@Repository
public class PicDAOImpl implements PicDAO {
	
	// ����� ����.xml�� ���ӽ����̽� ��
	private static final String namespace = "com.server.pica.PicAMapper";
		
	@Inject // DB Ŀ�ݼǰ� Ŭ��� �ڵ����� ���ִ� ����
	private SqlSession sqlSession;
	
	// �����ͺ��̽��� ���ε�� ���� dto �����ϱ�
	// ����� ���������� �������� ����:0 / ���� -1
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
}
