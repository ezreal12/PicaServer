package com.server.pica.util;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.server.pica.dto.PicUploadDTO;

public class FileSave {
		//������ �����ϰ� ������ ������ DTO�� ��� ���� 
		public static PicUploadDTO saveFile(MultipartFile file,PicUploadDTO dto,String uploadPath){
			// TODO : ȿ������ �α� ������� ����� �ʿ䰡 ����
			File dir = new File(uploadPath);
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
		    File saveFile = new File(uploadPath,saveName); // ������ ���� �̸�, ������ ���� �̸�

		    try {
		        file.transferTo(saveFile); // ���ε� ���Ͽ� saveFile�̶�� ������ ����
		    } catch (IOException e) {
		    	// ���� �α� �����
		        e.printStackTrace();
		        return null;
		    }
		    dto.setFile(saveName);
		    dto.setPath(uploadPath);
		    return dto;
		} 
		
		//������ �����ϰ� ���� ���� ������
		public static String saveFile(MultipartFile file,String uploadPath){
			// TODO : ȿ������ �α� ������� ����� �ʿ䰡 ����
			File dir = new File(uploadPath);
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
		    File saveFile = new File(uploadPath,saveName); // ������ ���� �̸�, ������ ���� �̸�

		    try {
		        file.transferTo(saveFile); // ���ε� ���Ͽ� saveFile�̶�� ������ ����
		    } catch (IOException e) {
		    	// ���� �α� �����
		        e.printStackTrace();
		        return null;
		    }
		    return saveName;
		} 
		
}
