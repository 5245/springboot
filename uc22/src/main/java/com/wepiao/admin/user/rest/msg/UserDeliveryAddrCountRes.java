package com.wepiao.admin.user.rest.msg;

public class UserDeliveryAddrCountRes extends BaseRes {
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public UserDeliveryAddrCountRes() {
        super();
    }

    public UserDeliveryAddrCountRes(int count) {
        super();
        this.count = count;
    }
}
