package com.server.pica.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.server.pica.dto.CreateAlbumDTO;
import com.server.pica.dto.MyAlbumDTO;
import com.server.pica.dto.PicUploadDTO;

public class FileUtil {
		
		private static final String IMAGE_SRC = "/resource/";
		//파일을 저장하고 저장한 정보를 DTO에 담아 리턴 
		public static PicUploadDTO saveFile(MultipartFile file,PicUploadDTO dto,String uploadPath){
			// TODO : 효과적인 로그 저장법을 고민할 필요가 있음
			File dir = new File(uploadPath);
			// 폴더가 존재하지 않을경우
			if(!dir.exists()) {
				//폴더 생성
				if(!dir.mkdirs()) {
					// 폴더 생성에서 에러 발생시 로그 남기기
				}
			}
		    String saveName = file.getOriginalFilename();

		    //logger.info("saveName: {}",saveName);
		    System.out.println("saveName: {"+saveName+"}");

		    // 저장할 File 객체를 생성(껍데기 파일)ㄴ
		    File saveFile = new File(uploadPath,saveName); // 저장할 폴더 이름, 저장할 파일 이름

		    try {
		        file.transferTo(saveFile); // 업로드 파일에 saveFile이라는 껍데기 입힘
		    } catch (IOException e) {
		    	// 에러 로그 남기기
		        e.printStackTrace();
		        return null;
		    }
		    dto.setFile(saveName);
		    return dto;
		} 
		
		//파일을 저장하고 파일 명을 리턴함
		public static String saveFile(MultipartFile file,String uploadPath){
			// TODO : 효과적인 로그 저장법을 고민할 필요가 있음
			File dir = new File(uploadPath);
			// 폴더가 존재하지 않을경우
			if(!dir.exists()) {
				//폴더 생성
				if(!dir.mkdirs()) {
					// 폴더 생성에서 에러 발생시 로그 남기기
				}
			}
		    String saveName = file.getOriginalFilename();

		    //logger.info("saveName: {}",saveName);
		    System.out.println("saveName: {"+saveName+"}");

		    // 저장할 File 객체를 생성(껍데기 파일)ㄴ
		    File saveFile = new File(uploadPath,saveName); // 저장할 폴더 이름, 저장할 파일 이름

		    try {
		        file.transferTo(saveFile); // 업로드 파일에 saveFile이라는 껍데기 입힘
		    } catch (IOException e) {
		    	// 에러 로그 남기기
		        e.printStackTrace();
		        return null;
		    }
		    return saveName;
		} 
		// 앨범 DTO의 리스트를 입력받아 해당 리스트의 DTO 파일 경로에 서버 주소를 추가해서 리턴한다.
		public static List<MyAlbumDTO> insertServerUrlInImages(List<MyAlbumDTO> list,HttpServletRequest request){
			if(list==null) return null;
			
			for(MyAlbumDTO e : list) {
				if(e.getDefaultPicture()==null) continue;
				String url = request.getRequestURL().toString().replace(request.getRequestURI(),"");
				url= url+request.getContextPath()+IMAGE_SRC;
				e.setDefaultPicture(url+e.getDefaultPicture());
			}
			return list;
		}
}
