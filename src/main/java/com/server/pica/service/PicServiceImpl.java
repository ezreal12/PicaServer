package com.server.pica.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.server.pica.dao.PicDAO;
import com.server.pica.dto.CreateAlbumDTO;
import com.server.pica.dto.MyAlbumDTO;
import com.server.pica.dto.PicUploadDTO;
import com.server.pica.dto.PictureDTO;
import com.server.pica.dto.RegisterMemberDTO;
import com.server.pica.dto.ShowPictureResultVO;
import com.server.pica.util.FileUtil;
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
	// DB���� �����͸� ã�� �������� (select���� ���� �ȳ�������)
	public static final int NOT_FOUND_DATA = -1;
	public static final int ERROR_DATABASE = -2;
	public static final int NO_PERMISSOIN = -3;
	public static final int REQUEST_OK = 0;
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
		dto=FileUtil.saveFile(uploadfile,dto,savePath);
		if(dto==null)
			return UPLOAD_ERROR_FILE;
		//2. DB�� �����Է�
		int result=dao.insertPicData(dto);
		//DB ���� �߻���
		if(result<0)
			return ERROR_DATABASE;
		return REQUEST_OK;
	}
	
	
	@Override
	public int registerMember(RegisterMemberDTO dto) {
		//2. DB�� �����Է�
		int result=dao.registerMember(dto);
		//DB ���� �߻���
		if(result<0)
			return ERROR_DATABASE;
		return REQUEST_OK;
	}
	
	@Override
	public int createAlbum(CreateAlbumDTO dto,MultipartFile uploadfile,String savePath) {
		//1. ���� ���� realFileName= ������ ����� ���ϸ�
		String realFileName=FileUtil.saveFile(uploadfile,savePath);
		if(realFileName==null)
			return UPLOAD_ERROR_FILE;
		// ������ ����� ���ϸ� dto�� �Է�
		dto.setDefaultPicture(realFileName);
		//2. DB�� �����Է�
		int result=dao.createAlbum(dto);
		//DB ���� �߻���
		if(result<0)
			return ERROR_DATABASE;
		return REQUEST_OK;
	}
	
	@Override
	public List showTable(String tableName) {
		return dao.showTable(tableName);
	}
	
	@Override
	public List<MyAlbumDTO> getMyalbum(int create_p_member_id) {
		List<CreateAlbumDTO> list = dao.getMyalbum(create_p_member_id);
		if (list==null) return null;
		List<MyAlbumDTO> result = new ArrayList<MyAlbumDTO>();
		for(CreateAlbumDTO d : list) {
			String nick = dao.getNickNameFromId(d.getCreate_p_member_id());
			MyAlbumDTO data = new MyAlbumDTO(d, nick);
			result.add(data);
		}
		return result;
	}
	// �ٹ� id�� �Է¹ް� �ٹ��� ����ִ� ���� ������ ���� ��������
	// 0 ����(), -1 �ٹ�����, -2 �������� ����, -3 ���Ѿ���
	@Override
	public ShowPictureResultVO showPicture(int album_id,int member_id) {
		ShowPictureResultVO result=new ShowPictureResultVO();
		List<PictureDTO> list = dao.showPicture(album_id);
		if (list==null) {
			result.setCode(NOT_FOUND_DATA);
			return result;
		}
		/*
		 * TODO : member_id�� p_member_id�� ������ Ȯ�� (���Ŀ� ģ���ʴ� ��ɱ����� AlbumMember���� ã�ƾ���)
			�� �ٹ����⵵ create_p_member_id�� �ƴ� AlbumMember���� ã�ƾ��� 
		 * */
		// �ӽ÷� ��� ID�� �´��� Ȯ���ؼ� ����
		int pMemberID = list.get(0).getP_member_id();
		if(pMemberID!=member_id) {
			result.setCode(NO_PERMISSOIN);
			return result;
		}
		result.setCode(REQUEST_OK);
		result.setResult(list);
		return result;
	}
	
}
