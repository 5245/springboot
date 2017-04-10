package com.wepiao.admin.user.rest.msg;

public class UserReceiverMobileGetReq extends BaseReq {

    /**openId*/
    private String  id;

    /**id类型*/
    private Integer idtype = 0;

    /**业务类型, 订单1,红包2,*/
    private Integer servicetype;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getIdtype() {
        return idtype;
    }

    public void setIdtype(Integer idtype) {
        this.idtype = idtype;
    }

    public Integer getServicetype() {
        return servicetype;
    }

    public void setServicetype(Integer servicetype) {
        this.servicetype = servicetype;
    }

    public UserReceiverMobileGetReq(String id, Integer idtype, Integer servicetype) {
        super();
        this.id = id;
        this.idtype = idtype;
        this.servicetype = servicetype;
    }

    public UserReceiverMobileGetReq() {
        super();
    }

}
