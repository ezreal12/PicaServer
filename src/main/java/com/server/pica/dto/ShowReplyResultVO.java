package com.server.pica.dto;

import java.util.List;

public class ShowReplyResultVO {
	private int code;
	private String nickName;
	private List<ReplyDTOWrapper> result;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public List<ReplyDTOWrapper> getResult() {
		return result;
	}
	public void setResult(List<ReplyDTOWrapper> result) {
		this.result = result;
	}
	
	
	
}
