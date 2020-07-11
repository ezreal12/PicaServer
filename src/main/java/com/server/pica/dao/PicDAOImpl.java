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
	// 1. ���� ���� �����͸� �޾� �Է�
	// 2. �±� �����Ͱ� ������� �±� �����͵� �Է� / ������ �Ѿ.
   @Override
   public int insertPicData(PicUploadDTO dto,List<String> tags) {
	   System.out.println("------ TEST HS 2 !!!! : "+dto.toString());
	   try {
		   sqlSession.insert(namespace+".uploadPic",dto);
	} catch (Exception e) {
		e.printStackTrace();
		return -1;
	}
	   // �Է¹��� �±װ� ������ �Է�
	   if(tags!=null) {
		   for(String tag : tags) {
			   // 1. �±� �ߺ�Ȯ��
			   Object result = sqlSession.selectOne(namespace+".getTagIdFromTag",tag);
			   // 2. �±װ� �������� ������� �Է�
			   if(result==null) {
			   	try {
			   		 sqlSession.insert(namespace+".uploadTagOne",tag);
			   	} catch (Exception e) {
			   		e.printStackTrace();
			   		// DB ������ -2 ����
			   		return -2;
			   	}
			   } 
		   }
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
	
	
	// ���� ID�� �г��� ��������
	@Override
	public String getNickNameFromId(int member_id) {
		return (String)sqlSession.selectOne(namespace+".getNickNameFromId", member_id);
	}
   
	// �ٹ� id�� �Է¹ް� �ٹ��� ����ִ� ���� ������ ���� ��������
	@Override
	public List<PictureDTO> showPictureList(int album_id) {
		System.out.println("showPictureList  album_id: "+album_id);
		return sqlSession.selectList(namespace+".showPicture",album_id);
	}
	// ���� id�� �Է¹ް� ���� ������ ��������
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
	
	// ����:0 / ���� -1
	//���ƿ� ���� �����ϱ�
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
	// ����:0 / ���� -1
		//���ƿ� ���� �����
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
	//<!-- ���� 1�� ��ȸ�ϱ� -->
	@Override
	public PictureDTO getPicture(int picture_id) {
		System.out.println("getPicture  picture_id: "+picture_id);
		return (PictureDTO)sqlSession.selectOne(namespace+".getPicture",picture_id);
	}
	//��� �Է¹ޱ�
	// ����:0 / ���� -1
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
	//<!-- ���� 1���� ��� ���� ��������-->
	@Override
	public List<ReplyDTO> getReply(int picture_id) {
		System.out.println("getReply  picture_id: "+picture_id);
		return sqlSession.selectList(namespace+".getReply",picture_id);
	}
}
