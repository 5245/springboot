package com.wepiao.admin.user.rest.msg;

import com.alibaba.fastjson.annotation.JSONField;

public class BaseResArrayWithReqId extends BaseResArray {
    private String requestId;

    @JSONField(name = "requestId")
    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public BaseResArrayWithReqId(int ret, int sub, String msg, String requestId, Object data) {
        super(ret, sub, msg, data);
        this.requestId = requestId;
    }
}
