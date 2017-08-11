package com.wepiao.user.common.entry;

public class OpenId {
    private String  openId;
    private Integer otherId;
    private Integer subOtherId;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getOtherId() {
        return otherId;
    }

    public void setOtherId(Integer otherId) {
        this.otherId = otherId;
    }

    public Integer getSubOtherId() {
		return subOtherId;
	}

	public void setSubOtherId(Integer subOtherId) {
		this.subOtherId = subOtherId;
	}

	public OpenId() {
    }

    public OpenId(String openId, Integer otherId) {
        this(openId, otherId, 0);
    }
    
    public OpenId(String openId, Integer otherId, Integer subOtherId) {
        super();
        this.openId = openId;
        this.otherId = otherId;
        this.subOtherId = subOtherId;
    }
}
