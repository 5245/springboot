package com.wepiao.admin.user.rest.msg;

import com.alibaba.fastjson.JSONObject;

public class MobileRegisterRes extends BaseRes {
    private int        uid;
    private int        memberId;
    private String     openId;
    private int        otherId;
    private JSONObject tianyuScanResult;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

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

    public JSONObject getTianyuScanResult() {
        return tianyuScanResult;
    }

    public void setTianyuScanResult(JSONObject tianyuScanResult) {
        this.tianyuScanResult = tianyuScanResult;
    }

    public MobileRegisterRes() {
    }

    public MobileRegisterRes(int memberId, String openId, int otherId) {
        super();
        this.memberId = memberId;
        this.uid = memberId;
        this.openId = openId;
        this.otherId = otherId;
    }
}
