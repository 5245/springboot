package com.wepiao.admin.user.rest.msg;

public class BindMobileNoReq extends BaseReq {
    private String openId;
    private int    otherId;
    private String unionId;
    private String mobileNo;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public int getOtherId() {
        return otherId;
    }

    public void setOtherId(int otherId) {
        this.otherId = otherId;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public BindMobileNoReq() {
        super();
    }

    public BindMobileNoReq(String openId, int otherId, String mobileNo) {
        super();
        this.openId = openId;
        this.otherId = otherId;
        this.mobileNo = mobileNo;
    }
}
