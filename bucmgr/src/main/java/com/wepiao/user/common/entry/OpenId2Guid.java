package com.wepiao.user.common.entry;

public class OpenId2Guid extends BaseSplittedEntry {
	private Integer id;
    private String guid;
    private String openId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public OpenId2Guid(Integer id, String openId, String guid) {
		super();
		this.id = id;
		this.guid = guid;
		this.openId = openId;
	}
	public OpenId2Guid() {
		super();
	}
}
