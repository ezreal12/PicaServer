package com.server.pica.dto;

import java.util.List;

// 앨범 사진보기(showPicture) 결과데이터
public class ShowPictureResultVO {
	// 0 성공(), -1 앨범없음, -2 가져오기 실패, -3 권한없음
	private int code;
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
	
	
	
}
