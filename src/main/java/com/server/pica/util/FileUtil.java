package com.server.pica.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.server.pica.dto.CreateAlbumDTO;
import com.server.pica.dto.PicUploadDTO;

public class FileUtil {
		
		private static final String IMAGE_SRC = "/resource/";
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
		// �ٹ� DTO�� ����Ʈ�� �Է¹޾� �ش� ����Ʈ�� DTO ���� ��ο� ���� �ּҸ� �߰��ؼ� �����Ѵ�.
		public static List<CreateAlbumDTO> insertServerUrlInImages(List<CreateAlbumDTO> list,HttpServletRequest request){
			if(list==null) return null;
			
			for(CreateAlbumDTO e : list) {
				if(e.getDefaultPicture()==null) continue;
				String url = request.getRequestURL().toString().replace(request.getRequestURI(),"");
				url= url+request.getContextPath()+IMAGE_SRC;
				e.setDefaultPicture(url+e.getDefaultPicture());
			}
			return list;
		}
}
