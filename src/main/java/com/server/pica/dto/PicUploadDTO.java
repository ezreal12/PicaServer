package com.server.pica.dto;
/*
 * 
 * PicUploadDTO�� PictureDTO �� ���εδ�����
 * PicUploadDTO�� ������ ���ε� �Ҷ� ���°Ŷ� picture_id�� upload_date�� �ʿ�� ����������
 * PictureDTO�� ��ȸ�� ���� �����͸� ��� Ŭ������  picture_id�� upload_date�� �ʿ���
 * 
 * */
public class PicUploadDTO {
	// ���� �̸�
	private String file;
	// ���� , �浵
	private String latitude;
	private String longitude;
	// ���뼳��
	private String contents;
	// ���� ������ id
	private int p_member_id;
	// �Ҽ� �ٹ� id
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
	
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	@Override
	public String toString() {
		return "file=" + file + ", latitude=" + latitude + ", longitude=" + longitude + ", contents="
				+ contents + ", p_member_id=" + p_member_id + ", p_album_id=" + p_album_id;
	}
	
	
}
