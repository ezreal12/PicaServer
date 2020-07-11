package com.server.pica.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.server.pica.dto.CreateAlbumDTO;
import com.server.pica.dto.LikePictureDTO;
import com.server.pica.dto.LoginVO;
import com.server.pica.dto.MyAlbumDTO;
import com.server.pica.dto.PicUploadDTO;
import com.server.pica.dto.RegisterMemberDTO;
import com.server.pica.dto.ShowPictureDataResultVO;
import com.server.pica.dto.ShowPictureResultVO;


public interface PicService {
	// ��Ʈ�ѷ����� ���� �Է¹ް� ��������
	// ���� ���� �Ŀ� DB�� �����͸� �Է��ϰ� ����� ������
	// ����:0 / ���� ������� -1 / DB���� : -2
	public int savePicture(PicUploadDTO dto,String tags, MultipartFile uploadfile,String savePath);
	// ����:0 / ���� -1
	public int registerMember(RegisterMemberDTO dto);
	// ����:0 / ���� ���� ���� -1 / DB ���� ���� -2
	public int createAlbum(CreateAlbumDTO dto,MultipartFile uploadfile,String savePath);
	// ���̺� �̸��� �Է¹޾� select * from tableName �� ����� dto�� ��ȯ
	public List showTable(String tableName);
	// �� �ٹ� ��ȸ
	public List<MyAlbumDTO> getMyalbum(int create_p_member_id);
	// �ٹ� id�� �Է¹ް� �ٹ��� ����ִ� ���� ������ ���� ��������
	public ShowPictureResultVO showPictureList(int album_id,int member_id);
	// ���� id�� �Է¹ް� ���� 1�� ��ȸ�ؼ� �����ϱ�
	public ShowPictureDataResultVO showPicture(int picture_id,int member_id);
	// 0 ����, -1 ����(�Ⱦ�), -2 ��й�ȣ ����, -3 ���̵����
	public LoginVO login(String email,String password);
	
	// ����:0 / ���� -1
	//���ƿ� ���� �����ϱ�
	public int addLikePicture(LikePictureDTO dto);
}
