package com.server.pica.dto;

import java.util.List;

// �ٹ� ��������(showPicture) ���������
public class ShowPictureResultVO {
	// 0 ����(), -1 �ٹ�����, -2 �������� ����, -3 ���Ѿ���
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
