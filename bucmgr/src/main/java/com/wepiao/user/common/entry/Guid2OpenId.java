package com.wepiao.user.common.entry;

import java.util.Date;

public class Guid2OpenId extends BaseSplittedEntry{
	private Integer id;
    private String guid;
    private String openId;
    private Date createTime;
    private Date updateTime;
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
	public Guid2OpenId(Integer id, String guid, String openId, Date createTime, Date updateTime) {
		super();
		this.id = id;
		this.guid = guid;
		this.openId = openId;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}
	public Guid2OpenId() {
		super();
	}
}
