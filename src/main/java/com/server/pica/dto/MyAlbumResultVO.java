package com.server.pica.dto;
// "내 앨범 조회" 기능 사용 후 결과 리턴하기

import java.util.List;

public class MyAlbumResultVO {
	// 조회결과 -1이면 결과없음 0이면 조회 성공
	private int code;
	private List<MyAlbumDTO> result;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public List<MyAlbumDTO> getResult() {
		return result;
	}
	public void setResult(List<MyAlbumDTO> result) {
		this.result = result;
	}
	
}
