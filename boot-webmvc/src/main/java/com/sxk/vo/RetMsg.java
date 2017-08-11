package com.sxk.vo;

import lombok.ToString;

import com.sxk.model.UserDO;

@ToString
public class RetMsg<T> {
    public static final int OK    = 200;
    public static final int ERROR = 500;
    private int             ret;
    private int             sub;
    private String          msg;
    private T               data;

    public static <T> RetMsg<T> success(T data) {
        RetMsg<T> retMsg = new RetMsg<T>();
        retMsg.ret = RetMsg.OK;
        retMsg.sub = 0;
        retMsg.msg = "success";
        retMsg.data = data;
        return retMsg;
    }

    public static <T> RetMsg<T> error(T data) {
        RetMsg<T> retMsg = new RetMsg<T>();
        retMsg.ret = RetMsg.ERROR;
        retMsg.sub = 0;
        retMsg.msg = "error";
        retMsg.data = data;
        return retMsg;
    }

    public static void main(String[] args) {
        UserDO u = new UserDO(1, "life", "life123456");
        System.out.println(u.toString());
        RetMsg<UserDO> rs = RetMsg.success(u);
        System.out.println(rs.toString());
    }

}
