package com.server.pica.dto;

public class ReplyDTOWrapper extends ReplyDTO {
	private String nickName;
	// 내가 작성한 댓글인가 여부 'y' 'n'
	private char isMyReply;
	
	public ReplyDTOWrapper(ReplyDTO d) {
		setPicture_id(d.getPicture_id());
		setMember_id(d.getMember_id());
		setReply_id(d.getReply_id());
		setReply_text(d.getReply_text());
		setUpload_date(d.getUpload_date());
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public char getIsMyReply() {
		return isMyReply;
	}

	public void setIsMyReply(char isMyReply) {
		this.isMyReply = isMyReply;
	}

	@Override
	public String toString() {
		return "ReplyDTOWrapper [nickName=" + nickName + ", isMyReply=" + isMyReply + "]";
	}
	
}
