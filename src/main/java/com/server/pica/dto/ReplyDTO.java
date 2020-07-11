package com.server.pica.dto;

import java.sql.Date;



public class ReplyDTO {
	private int reply_id;
	private int member_id;
	private int picture_id;
	private String reply_text;
	private Date upload_date;
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
	public String getReply_text() {
		return reply_text;
	}
	public void setReply_text(String reply_text) {
		this.reply_text = reply_text;
	}
	public Date getUpload_date() {
		return upload_date;
	}
	public void setUpload_date(Date upload_date) {
		this.upload_date = upload_date;
	}
	
	public int getReply_id() {
		return reply_id;
	}
	public void setReply_id(int reply_id) {
		this.reply_id = reply_id;
	}
	@Override
	public String toString() {
		return "ReplyDTO [member_id=" + member_id + ", picture_id=" + picture_id + ", reply_text=" + reply_text
				+ ", upload_date=" + upload_date + "]";
	}
	
	
}
