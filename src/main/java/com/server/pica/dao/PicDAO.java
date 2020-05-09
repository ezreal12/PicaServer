package com.server.pica.dao;

import com.server.pica.dto.CreateAlbumDTO;
import com.server.pica.dto.PicUploadDTO;
import com.server.pica.dto.RegisterMemberDTO;

public interface PicDAO {
	// �����ͺ��̽��� ���ε�� ���� dto �����ϱ�
	// ����� ���������� �������� ����:0 / ���� -1
	public int insertPicData(PicUploadDTO dto);
	// ����:0 / ���� -1
	public int registerMember(RegisterMemberDTO dto);
	// ����:0 / ���� ���� ���� -1 / DB ���� ���� -2
	public int createAlbum(CreateAlbumDTO dto);
	
	
}
