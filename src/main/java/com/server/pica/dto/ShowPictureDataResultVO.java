package com.server.pica.dto;
// showPictureData.do 결과 데이터
public class ShowPictureDataResultVO {
	private int code;
	private PictureDTOWrapper result;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	
	public PictureDTOWrapper getResult() {
		return result;
	}
	public void setResult(PictureDTOWrapper result) {
		this.result = result;
	}
	@Override
	public String toString() {
		return "ShowPictureDataResultVO [code=" + code + ", result=" + result + "]";
	}
	
	
}
