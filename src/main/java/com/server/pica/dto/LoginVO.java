package com.server.pica.dto;
// �α��� ����� ��ȯ�ϴ� VO
public class LoginVO {
	private int code;
	// �α����Ҷ����� �̸����� �ƴ϶� DB�� ����� int�� ��� ID
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
