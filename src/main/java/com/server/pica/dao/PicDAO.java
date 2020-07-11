package com.server.pica.dao;

import java.util.List;

import com.server.pica.dto.AlbumMemberDTO;
import com.server.pica.dto.CreateAlbumDTO;
import com.server.pica.dto.LikePictureDTO;
import com.server.pica.dto.PicUploadDTO;
import com.server.pica.dto.PictureDTO;
import com.server.pica.dto.RegisterMemberDTO;

public interface PicDAO {
	// �����ͺ��̽��� ���ε�� ���� dto �����ϱ�
		// ����� ���������� �������� ����:0 / ���� -1
		// 1. ���� ���� �����͸� �޾� �Է�
		// 2. �±� �����Ͱ� ������� �±� �����͵� �Է� / ������ �Ѿ.
	public int insertPicData(PicUploadDTO dto,List<String> tags);
	// ����:0 / ���� -1
	public int registerMember(RegisterMemberDTO dto);
	// ����:0 / ���� ���� ���� -1 / DB ���� ���� -2
	public int createAlbum(CreateAlbumDTO dto);
	// ���̺� �̸��� �Է¹޾� select * from tableName �� ����� dto�� ��ȯ
	public List showTable(String tableName);
	// �� �ٹ� ��ȸ
	public List<CreateAlbumDTO> getMyalbum(int create_p_member_id);
	// ���� ID�� �г��� ��������
	public String getNickNameFromId(int member_id);
	// �ٹ� id�� �Է¹ް� �ٹ��� ����ִ� ���� ������ ���� ��������
	public List<PictureDTO> showPictureList(int album_id);
	// ���� id�� �Է¹ް� ���� ������ ��������
	public PictureDTO showPicture(int picture_id);
	// �ٹ� id�� �Է¹ް� �ش� �ٹ� ������������
	public CreateAlbumDTO getAlbum(int album_id);
	//��� id �Է¹޾Ƽ� �ش����� ���� �ٹ� ������ ��� album_member ��������
	//�ַ� ���� üũ�� ���
	public List<AlbumMemberDTO> getAlbumMember(int member_id);
	// Email �ּҷ� �ش�Ǵ� ��� ���� ��������
	// ���� �α��� ��ɿ� ���
	public RegisterMemberDTO getMemberFromEmail(String email);
	
	// ����:0 / ���� -1
	//���ƿ� ���� �����ϱ�
	public int addLikePicture(LikePictureDTO dto);
	// ����:0 / ���� -1
	//���ƿ� ���� �����
	public int deleteLikePicture(LikePictureDTO dto);
	// ���ƿ� ���� Ȯ���ϱ�(��ۿ� ���)
	public LikePictureDTO serchLikePicture(LikePictureDTO dto);
	
	//<!-- �ش� ������ ���ƿ��� ���� ���� ��������-->
	public List<LikePictureDTO> getLike(int member_id);
	//<!-- ���� 1�� ��ȸ�ϱ� -->
	public PictureDTO getPicture(int picture_id);
	
	
	
}
