package com.server.pica.dto;
// "�� �ٹ� ��ȸ" ��� ��� �� ��� �����ϱ�

import java.util.List;

public class MyAlbumResultVO {
	// ��ȸ��� -1�̸� ������� 0�̸� ��ȸ ����
	private int code;
	private List<CreateAlbumDTO> result;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public List<CreateAlbumDTO> getResult() {
		return result;
	}
	public void setResult(List<CreateAlbumDTO> result) {
		this.result = result;
	}
	
}
