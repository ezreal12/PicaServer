package com.server.pica.dto;

import java.util.List;

// �ٹ� ��������(showPicture) ���������
public class ShowPictureResultVO {
	// 0 ����(), -1 �ٹ�����, -2 �������� ����, -3 ���Ѿ���
	private int code;
	//�ٹ� �̸�, �ٹ�����, �ٹ� Ÿ��Ʋ����
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
