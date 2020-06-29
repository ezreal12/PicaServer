package com.server.pica.dto;

import java.sql.Date;

// 사진 정보를 담아두는 DTO/
/*
 * 
 * PicUploadDTO랑 PictureDTO 를 따로두는이유
 * PicUploadDTO는 사진을 업로드 할때 쓰는거라 picture_id와 upload_date를 필요로 하지않지만
 * PictureDTO는 조회한 사진 데이터를 담는 클래스라  picture_id와 upload_date가 필요함
 * 
 * */

public class PictureDTO {
	private int picture_id;
	private String file;
	private Date upload_date;
	private int p_member_id;
	private int p_album_id;
	// 위도 , 경도
	private String latitude;
	private String longitude;
	// 내용설명
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
