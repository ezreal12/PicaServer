package com.server.pica.dto;


public class CreateAlbumDTO {
	// ���߿� �ٹ��߰��Ӹ� �ƴ϶� �ٹ����� �����ö��� ����ؼ� ID�� �߰�
	private int album_id;
	private String name;
	private String description;
	private String defaultPicture;
	private int create_p_member_id;
	public int getAlbum_id() {
		return album_id;
	}
	public void setAlbum_id(int album_id) {
		this.album_id = album_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDefaultPicture() {
		return defaultPicture;
	}
	public void setDefaultPicture(String defaultPicture) {
		this.defaultPicture = defaultPicture;
	}
	public int getCreate_p_member_id() {
		return create_p_member_id;
	}
	public void setCreate_p_member_id(int create_p_member_id) {
		this.create_p_member_id = create_p_member_id;
	}
	
	
	
}
