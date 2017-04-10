package com.wepiao.admin.user.rest.msg;

import io.swagger.annotations.ApiModelProperty;

import java.lang.reflect.Field;

public class BaseReq {

    @ApiModelProperty("Rest API的缩短的请求ID")
    private String requestId;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public BaseReq() {
    }

    public BaseReq(String requestId) {
        super();
        this.requestId = requestId;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        Class<?> clazz = this.getClass();
        Field[] fields = clazz.getDeclaredFields();
        sb.append(clazz.getName() + "{");
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                sb.append(field.getName() + ":" + field.get(this));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        sb.append("}");
        return sb.toString();
    }
}
