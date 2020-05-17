package com.server.pica.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.server.pica.dto.CreateAlbumDTO;
import com.server.pica.dto.RegisterMemberDTO;


public interface PicService {
	// ��Ʈ�ѷ����� ���� �Է¹ް� ��������
	// ���� ���� �Ŀ� DB�� �����͸� �Է��ϰ� ����� ������
	// ����:0 / ���� ������� -1 / DB���� : -2
	public int savePicture(int p_member_id,int p_album_id, MultipartFile uploadfile,String savePath);
	// ����:0 / ���� -1
	public int registerMember(RegisterMemberDTO dto);
	// ����:0 / ���� ���� ���� -1 / DB ���� ���� -2
	public int createAlbum(CreateAlbumDTO dto,MultipartFile uploadfile,String savePath);
	// ���̺� �̸��� �Է¹޾� select * from tableName �� ����� dto�� ��ȯ
	public List showTable(String tableName);
	
}
