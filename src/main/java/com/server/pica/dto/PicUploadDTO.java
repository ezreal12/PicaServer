package com.server.pica.dto;

public class PicUploadDTO {
	// ���� ������
	private String path;
	// ���� �̸�
	private String file;
		
	private int p_member_id;
	private int p_album_id;
	
	public int getP_member_id() {
		return p_member_id;
	}
	public void setP_member_id(int p_member_id) {
		this.p_member_id = p_member_id;
	}
	public int getP_album_id() {
		return p_album_id;
	}
	public void setP_album_id(int p_album_id) {
		this.p_album_id = p_album_id;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
}