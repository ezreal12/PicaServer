package com.server.pica.dto;



public class RegisterMemberDTO {
	// 나중에 회원가입 뿐만 아니라 회원정보 가져올때를 대비해서 ID도 추가
	private int member_id;
	private String nickname;
	private String password;
	private String email;
	private String phonenumber;
	
	
	public int getMember_id() {
		return member_id;
	}
	
	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}
	
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	@Override
	public String toString() {
		return "member_id=" + member_id + ", nickname=" + nickname + ", password=" + password
				+ ", email=" + email + ", phonenumber=" + phonenumber;
	}
	
	
}
