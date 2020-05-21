package com.server.pica.dto;

public class PicUploadDTO {
	// 파일 이름
	private String file;
	// 위도 , 경도
	private double latitude;
	private double longitude;
	// 내용설명
	private String contents;
	// 사진 소유자 id
	private int p_member_id;
	// 소속 앨범 id
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
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
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
