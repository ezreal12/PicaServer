package com.server.pica.dto;

import java.sql.Date;

//PictureDTO�� �г��Ӹ� �߰��Ѱ�
public class PictureDTOWrapper extends PictureDTO {
	private String nickName;
	/*
	private int picture_id;
	private String file;
	private Date upload_date;
	private int p_member_id;
	private int p_album_id;
	// ���� , �浵
	private double latitude;
	private double longitude;
	// ���뼳��
	private String contents;
	*/
	public PictureDTOWrapper(PictureDTO d) {
		setPicture_id(d.getPicture_id());
		setFile(d.getFile());
		setUpload_date(d.getUpload_date());
		setP_member_id(d.getP_member_id());
		setP_album_id(d.getP_album_id());
		setLatitude(Double.toString(d.getLatitude()));
		setLongitude(Double.toString(d.getLongitude()));
		setContents(d.getContents());
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
}
