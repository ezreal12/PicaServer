package com.server.pica.dto;
// showPictureData.do ��� ������
public class ShowPictureDataResultVO {
	private int code;
	private PictureDTOWrapper result;
	// ���� ���ε��� �����ΰ� ���� 'y' 'n'
	private char isMyUpload='n';
	// ���� ���ƿ並 ���� �����ΰ� ���� 'y' 'n'
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
