package com.wepiao.admin.user.rest.msg;

/**
 * 当且仅当响应里包含一个名为参数‘result’时用到此类
 * 用于标识操作的结果
 * @author Jin Song
 *
 */
public class SingleResultRes extends BaseRes {
    private int result;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public SingleResultRes(int result) {
        super();
        this.result = result;
    }
}
