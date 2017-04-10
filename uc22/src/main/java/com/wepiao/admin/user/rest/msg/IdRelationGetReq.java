package com.wepiao.admin.user.rest.msg;

import io.swagger.annotations.ApiModelProperty;

public class IdRelationGetReq extends BaseReq {

    @ApiModelProperty("openid")
    private String id;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public IdRelationGetReq() {
        super();
    }

    public IdRelationGetReq(String id) {
        super();
        this.id = id;
    }

}
