package com.server.pica.service;

import org.springframework.web.multipart.MultipartFile;

public interface PicService {
	// ��Ʈ�ѷ����� ���� �Է¹ް� ��������
	// ���� ���� �Ŀ� DB�� �����͸� �Է��ϰ� ����� ������
	// ����:0 / ���� ������� -1 / DB���� : -2
	public int savePicture(int p_member_id,int p_album_id, MultipartFile uploadfile);
}
