package com.sxk.model;

import io.swagger.annotations.ApiModelProperty;

import org.hibernate.validator.constraints.NotEmpty;

public class UserDO extends BaseDO {
    @ApiModelProperty("用户名称")
    @NotEmpty(message = "username is null")
    private String userName;

    private String password;
    @ApiModelProperty("手机号")
    @NotEmpty(message = "mobileNo is null")
    private String mobileNo;

    private String email;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserDO() {
        super();
    }

    public UserDO(int id, String userName, String mobileNo) {
        super(id);
        this.userName = userName;
        this.mobileNo = mobileNo;
    }

    public UserDO(String userName, String mobileNo) {
        super();
        this.userName = userName;
        this.mobileNo = mobileNo;
    }

    public UserDO(String userName, String mobileNo, String email) {
        super();
        this.userName = userName;
        this.mobileNo = mobileNo;
        this.email = email;
    }

    public UserDO(String userName, String password, String mobileNo, String email) {
        super();
        this.userName = userName;
        this.password = password;
        this.mobileNo = mobileNo;
        this.email = email;
    }
}
