package com.server.pica.dto;

import java.util.List;

// 앨범 사진보기(showPicture) 결과데이터
public class ShowPictureResultVO {
	// 0 성공(), -1 앨범없음, -2 가져오기 실패, -3 권한없음
	private int code;
	//앨범 이름, 앨범설명, 앨범 타이틀사진
	private String name;
	private String description;
	private String defaultPicture;
	private List<PictureDTO> result;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public List<PictureDTO> getResult() {
		return result;
	}
	public void setResult(List<PictureDTO> result) {
		this.result = result;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDefaultPicture() {
		return defaultPicture;
	}
	public void setDefaultPicture(String defaultPicture) {
		this.defaultPicture = defaultPicture;
	}
	
	
	
}
