package com.server.pica.dto;
// showPictureData.do 결과 데이터
public class ShowPictureDataResultVO {
	private int code;
	private PictureDTOWrapper result;
	// 내가 업로드한 사진인가 여부 'y' 'n'
	private char isMyUpload='n';
	// 내가 좋아요를 누른 사진인가 여부 'y' 'n'
	private char isLikePicture='n';
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
	public char getIsMyUpload() {
		return isMyUpload;
	}
	public void setIsMyUpload(char isMyUpload) {
		this.isMyUpload = isMyUpload;
	}
	public char getIsLikePicture() {
		return isLikePicture;
	}
	public void setIsLikePicture(char isLikePicture) {
		this.isLikePicture = isLikePicture;
	}
	
	
	
}
