package com.wepiao.user.common.entry;

public class MobileNoMapping extends BaseSplittedEntry {
    private Integer id;
    private String  mobileNo;
    private Integer uid;

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

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public MobileNoMapping() {
    }

    public MobileNoMapping(Integer id, String mobileNo, Integer uid) {
        super();
        this.id = id;
        this.mobileNo = mobileNo;
        this.uid = uid;
    }
}
