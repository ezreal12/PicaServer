package com.server.pica.dto;
// "�� �ٹ� ��ȸ" ��� ��� �� ��� �����ϱ�

import java.util.List;

public class MyAlbumResultVO {
	// ��ȸ��� -1�̸� ������� 0�̸� ��ȸ ����
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
