package com.wepiao.user.common.entry;

import java.util.Date;

public class Guid2Mobile extends BaseSplittedEntry{
	private Integer id;
	private String guid;
    private String mobileNo;
    private Boolean isMember;
    private Date createTime;
    private Date updateTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public Boolean isMember() {
		return isMember;
	}
	public void setMember(Boolean isMember) {
		this.isMember = isMember;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Guid2Mobile(Integer id, String guid, String mobileNo, Boolean isMember, Date createTime, Date updateTime) {
		super();
		this.id = id;
		this.mobileNo = mobileNo;
		this.isMember = isMember;
		this.guid = guid;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}
	public Guid2Mobile() {
		super();
	}
}
