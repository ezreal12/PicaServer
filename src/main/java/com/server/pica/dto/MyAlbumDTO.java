package com.server.pica.dto;
// CreateAlbumDTO에서 닉네임 추가된 버전
public class MyAlbumDTO extends CreateAlbumDTO {
	/*
	private int album_id;
	private String name;
	private String description;
	private String defaultPicture;
	private int create_p_member_id;
	*/
	private String nickName;

	public MyAlbumDTO(CreateAlbumDTO d,String nickName) {
		super();
		this.nickName = nickName;
		setAlbum_id(d.getAlbum_id());
		setName(d.getName());
		setDescription(d.getDescription());
		setDefaultPicture(d.getDefaultPicture());
		setCreate_p_member_id(d.getCreate_p_member_id());
	}
	
	public String getNickName() {
		return nickName;
	}
}
