package com.server.pica.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.server.pica.dto.CreateAlbumDTO;
import com.server.pica.dto.MyAlbumDTO;
import com.server.pica.dto.PicUploadDTO;
import com.server.pica.dto.PictureDTO;
import com.server.pica.dto.ShowPictureResultVO;

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
		public static List<MyAlbumDTO> insertServerUrlInImages(List<MyAlbumDTO> list,HttpServletRequest request){
			if(list==null) return null;		
			for(MyAlbumDTO e : list) {
				if(e.getDefaultPicture()==null) continue;
				e.setDefaultPicture(parseImageSrc(e.getDefaultPicture(),request));
			}
			return list;
		}
		// �ٹ� DTO�� ����Ʈ�� �Է¹޾� �ش� ����Ʈ�� DTO ���� ��ο� ���� �ּҸ� �߰��ؼ� �����Ѵ�.
		public static ShowPictureResultVO insertServerUrlInImages(ShowPictureResultVO vo,HttpServletRequest request){
			List<PictureDTO> list = vo.getResult();
			if(list==null) return null;
			for(PictureDTO e : list) {
				if(e.getFile()==null) continue;
				e.setFile(parseImageSrc(e.getFile(),request));
			}
			vo.setResult(list);
			return vo;
		}
		// ���� �̸��� �Է¹޾Ƽ� ���������+���� �̸����� �� ������� �����ϱ�
		private static String parseImageSrc(String fileName,HttpServletRequest request) {
			String url = request.getRequestURL().toString().replace(request.getRequestURI(),"");
			url= url+request.getContextPath()+IMAGE_SRC;
			return url+fileName;
		}
}
