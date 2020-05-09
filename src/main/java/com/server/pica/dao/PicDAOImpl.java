package com.server.pica.dao;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.server.pica.dto.CreateAlbumDTO;
import com.server.pica.dto.PicUploadDTO;
import com.server.pica.dto.RegisterMemberDTO;
//@Repository ������ AutoWired �ֳ����̼� �����ڵ� �ݵ�� ������ �߿�
@Repository
public class PicDAOImpl implements PicDAO {
	
	/*
	 * ���� : insert�� not null �Ӽ����� ���Դ´ٴ���
	 * sql���̳� �ȿ� �� �����Ͱ� �̻��ϸ� �ٷ� ClassNotFoundException ������ �մµ�
	 * �� ������ Ư�� ������ ���������� �ʴ� �������� ������ ���� �������� ã�� �ؾ���.
	 * */
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
   // ����:0 / ���� -1
	/*
	 * ���� : insert�� not null �Ӽ����� ���Դ´ٴ���
	 * sql���̳� �ȿ� �� �����Ͱ� �̻��ϸ� �ٷ� ClassNotFoundException ������ �մµ�
	 * �� ������ Ư�� ������ ���������� �ʴ� �������� ������ ���� �������� ã�� �ؾ���.
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
	// ����:0 / ���� ���� ���� -1 / DB ���� ���� -2
	/*
	 * ���� : insert�� not null �Ӽ����� ���Դ´ٴ���
	 * sql���̳� �ȿ� �� �����Ͱ� �̻��ϸ� �ٷ� ClassNotFoundException ������ �մµ�
	 * �� ������ Ư�� ������ ���������� �ʴ� �������� ������ ���� �������� ã�� �ؾ���.
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
   
   
   
   
   
}
