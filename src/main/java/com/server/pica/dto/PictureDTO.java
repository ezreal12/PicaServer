package com.server.pica.dto;

import java.sql.Date;

// ���� ������ ��Ƶδ� DTO/
/*
 * 
 * PicUploadDTO�� PictureDTO �� ���εδ�����
 * PicUploadDTO�� ������ ���ε� �Ҷ� ���°Ŷ� picture_id�� upload_date�� �ʿ�� ����������
 * PictureDTO�� ��ȸ�� ���� �����͸� ��� Ŭ������  picture_id�� upload_date�� �ʿ���
 * 
 * */

public class PictureDTO {
	private int picture_id;
	private String file;
	private Date upload_date;
	private int p_member_id;
	private int p_album_id;
	// ���� , �浵
	private String latitude;
	private String longitude;
	// ���뼳��
	private String contents;
	public int getPicture_id() {
		return picture_id;
	}
	public void setPicture_id(int picture_id) {
		this.picture_id = picture_id;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public Date getUpload_date() {
		return upload_date;
	}
	public void setUpload_date(Date upload_date) {
		this.upload_date = upload_date;
	}
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
	public double getLatitude() {
		return Double.parseDouble(latitude);
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return Double.parseDouble(longitude);
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
	
	
	
}
