package com.server.pica.dao;

import com.server.pica.dto.PicUploadDTO;

public interface PicDAO {
	// �����ͺ��̽��� ���ε�� ���� dto �����ϱ�
	// ����� ���������� �������� ����:0 / ���� -1
	public int insertPicData(PicUploadDTO dto);
}
