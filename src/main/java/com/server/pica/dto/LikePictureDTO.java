package com.server.pica.dto;

public class LikePictureDTO {	
	private int member_id ;
	private int picture_id;
	public int getMember_id() {
		return member_id;
	}
	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}
	public int getPicture_id() {
		return picture_id;
	}
	public void setPicture_id(int picture_id) {
		this.picture_id = picture_id;
	}
	@Override
	public String toString() {
		return "LikePictureDTO [member_id=" + member_id + ", picture_id=" + picture_id + "]";
	}
	
	
	
	

}
