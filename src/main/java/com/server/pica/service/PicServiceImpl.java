package com.server.pica.service;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.server.pica.dao.PicDAO;
import com.server.pica.dto.PicUploadDTO;
import com.server.pica.dto.RegisterMemberDTO;
// �� ���� ������ ���񽺶�� ������ִ°� ��������
// ��â�Ѵ� �������� �ֳ����̼��� ����!
// @Repository ������ AutoWired �ֳ����̼� �����ڵ� �ݵ�� ������ �߿�
@Repository
public class PicServiceImpl implements PicService {
	//������� �׽�Ʈ ���� ���� ���� ���
	private static final String UPLOAD_PATH = "C:\\Users\\SiuKim\\Desktop\\sample";
	//��������
	// ����! : ���� ��η� ������ �����Ҽ��ִ´�� �ش� ��ο� ��������� �ݵ�� �ʿ��� chmod 777 filetest
	//private static final String UPLOAD_PATH = "/home/ubuntu/filetest";
	public static final int UPLOAD_ERROR_FILE = -1;
	public static final int UPLOAD_ERROR_DATABASE = -2;
	public static final int UPLOAD_OK = 0;
	@Autowired
	PicDAO dao;

	// ��Ʈ�ѷ����� ���� �Է¹ް� ��������
		// ���� ���� �Ŀ� DB�� �����͸� �Է��ϰ� ����� ������
		// ����:0 / ���� ������� -1 / DB���� : -2
	
	@Override
	public int savePicture(int p_member_id, int p_album_id, MultipartFile uploadfile) {
		PicUploadDTO dto = new PicUploadDTO();
		dto.setP_album_id(p_album_id);
		dto.setP_member_id(p_member_id);
		//1. ���� ����
		dto=saveFile(uploadfile,dto);
		if(dto==null)
			return UPLOAD_ERROR_FILE;
		//2. DB�� �����Է�
		int result=dao.insertPicData(dto);
		//DB ���� �߻���
		if(result<0)
			return UPLOAD_ERROR_DATABASE;
		return UPLOAD_OK;
	}
	//������ �����ϰ� ������ ������ DTO�� ��� ���� 
	private PicUploadDTO saveFile(MultipartFile file,PicUploadDTO dto){
		
		// TODO : ȿ������ �α� ������� ����� �ʿ䰡 ����
		
		File dir = new File(UPLOAD_PATH);
		// ������ �������� �������
		if(!dir.exists()) {
			//���� ����
			if(!dir.mkdirs()) {
				// ���� �������� ���� �߻��� �α� �����
			}
		}
		
		
	   
	    String saveName = file.getOriginalFilename();

	    //logger.info("saveName: {}",saveName);
	    System.out.println("saveName: {"+saveName+"}");

	    // ������ File ��ü�� ����(������ ����)��
	    File saveFile = new File(UPLOAD_PATH,saveName); // ������ ���� �̸�, ������ ���� �̸�

	    try {
	        file.transferTo(saveFile); // ���ε� ���Ͽ� saveFile�̶�� ������ ����
	    } catch (IOException e) {
	    	// ���� �α� �����
	        e.printStackTrace();
	        return null;
	    }
	    dto.setFile(saveName);
	    dto.setPath(UPLOAD_PATH);
	    return dto;
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
	
}
