package com.wepiao.user.common.entry;

import com.wepiao.user.common.entry.enumeration.OpType;

public class MemberOpsHistory extends BaseSplittedEntry{
	private String  memberId;
	private OpType  opType;
	private String  userIp;
	private String  mobileNo;
	private Integer channelId;
	private String  openId;
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public OpType getOpType() {
		return opType;
	}
	public void setOpType(OpType opType) {
		this.opType = opType;
	}
	public String getUserIp() {
		return userIp;
	}
	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public Integer getChannelId() {
		return channelId;
	}
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public MemberOpsHistory(String memberId, OpType opType, String userIp, String mobileNo, Integer channelId, String openId) {
		super();
		this.memberId = memberId;
		this.opType = opType;
		this.userIp = userIp;
		this.mobileNo = mobileNo;
		this.channelId = channelId;
		this.openId = openId;
	}
	public MemberOpsHistory() {
	}
}
