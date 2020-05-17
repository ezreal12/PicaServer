package com.server.pica.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.server.pica.dao.PicDAO;
import com.server.pica.dto.CreateAlbumDTO;
import com.server.pica.dto.PicUploadDTO;
import com.server.pica.dto.RegisterMemberDTO;
import com.server.pica.util.FileSave;
// �� ���� ������ ���񽺶�� ������ִ°� ��������
// ��â�Ѵ� �������� �ֳ����̼��� ����!
// @Repository ������ AutoWired �ֳ����̼� �����ڵ� �ݵ�� ������ �߿�
@Repository
public class PicServiceImpl implements PicService {
	//������� �׽�Ʈ ���� ���� ���� ���
	//private static final String UPLOAD_PATH = "C:\\Users\\SiuKim\\Desktop\\sample";
	//��������
	// ����! : ���� ��η� ������ �����Ҽ��ִ´�� �ش� ��ο� ��������� �ݵ�� �ʿ��� chmod 777 filetest
	
	public static final int UPLOAD_ERROR_FILE = -1;
	public static final int UPLOAD_ERROR_DATABASE = -2;
	public static final int UPLOAD_OK = 0;
	@Autowired
	PicDAO dao;

	// ��Ʈ�ѷ����� ���� �Է¹ް� ��������
		// ���� ���� �Ŀ� DB�� �����͸� �Է��ϰ� ����� ������
		// ����:0 / ���� ������� -1 / DB���� : -2
	
	@Override
	public int savePicture(int p_member_id, int p_album_id, MultipartFile uploadfile,String savePath) {
		PicUploadDTO dto = new PicUploadDTO();
		dto.setP_album_id(p_album_id);
		dto.setP_member_id(p_member_id);
		//1. ���� ����
		dto=FileSave.saveFile(uploadfile,dto,savePath);
		if(dto==null)
			return UPLOAD_ERROR_FILE;
		//2. DB�� �����Է�
		int result=dao.insertPicData(dto);
		//DB ���� �߻���
		if(result<0)
			return UPLOAD_ERROR_DATABASE;
		return UPLOAD_OK;
	}
	
	
	@Override
	public int registerMember(RegisterMemberDTO dto) {
		//2. DB�� �����Է�
		int result=dao.registerMember(dto);
		//DB ���� �߻���
		if(result<0)
			return UPLOAD_ERROR_DATABASE;
		return UPLOAD_OK;
	}
	
	@Override
	public int createAlbum(CreateAlbumDTO dto,MultipartFile uploadfile,String savePath) {
		//1. ���� ���� realFileName= ������ ����� ���ϸ�
		String realFileName=FileSave.saveFile(uploadfile,savePath);
		if(realFileName==null)
			return UPLOAD_ERROR_FILE;
		// ������ ����� ���ϸ� dto�� �Է�
		dto.setDefaultPicture(realFileName);
		//2. DB�� �����Է�
		int result=dao.createAlbum(dto);
		//DB ���� �߻���
		if(result<0)
			return UPLOAD_ERROR_DATABASE;
		return UPLOAD_OK;
	}
	
	@Override
	public List showTable(String tableName) {
		return dao.showTable(tableName);
	}
	
}
