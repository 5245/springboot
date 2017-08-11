package com.wepiao.user.common.entry;

public class Mobile2Guid extends BaseSplittedEntry{
	private Integer id;
    private String mobileNo;
    private String guid;
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
	public Mobile2Guid(Integer id, String mobileNo, String guid) {
		super();
		this.id = id;
		this.mobileNo = mobileNo;
		this.guid = guid;
	}
	public Mobile2Guid() {
		super();
	}
}
