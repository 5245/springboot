package com.wepiao.admin.user.rest.msg;

import com.alibaba.fastjson.JSONObject;

public class BindMobileNoRes extends BaseRes {
    private long       uid;
    private long       memberId;
    private String     extUid;
    private JSONObject tianyuScanResult;

    public JSONObject getTianyuScanResult() {
        return tianyuScanResult;
    }

    public void setTianyuScanResult(JSONObject tianyuScanResult) {
        this.tianyuScanResult = tianyuScanResult;
    }

    public long get_UID() {
        return uid;
    }

    public void set_UID(long uid) {
        this.uid = uid;
    }

    public long get_memberId() {
        return memberId;
    }

    public void set_memberId(long memberId) {
        this.memberId = memberId;
    }

    public String getExtUid() {
        return extUid;
    }

    public void setExtUid(String extUid) {
        this.extUid = extUid;
    }

    public BindMobileNoRes(long memberId, String extUid) {
        super();
        this.memberId = memberId;
        this.uid = memberId;
        this.extUid = extUid;
    }
}
