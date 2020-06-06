package com.server.pica.dto;
// 로그인 결과를 반환하는 VO
public class LoginVO {
	private int code;
	// 로그인할때쓰는 이메일이 아니라 DB에 저장된 int형 멤버 ID
	private int member_id;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public int getMember_id() {
		return member_id;
	}
	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}
	
}
