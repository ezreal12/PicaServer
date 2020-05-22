package com.server.pica.dto;
// showPictureData.do 결과 데이터
public class ShowPictureDataResultVO {
	private int code;
	private PictureDTO dto;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public PictureDTO getDto() {
		return dto;
	}
	public void setDto(PictureDTO dto) {
		this.dto = dto;
	}
	@Override
	public String toString() {
		return "ShowPictureDataResultVO [code=" + code + ", dto=" + dto + "]";
	}
	
}
