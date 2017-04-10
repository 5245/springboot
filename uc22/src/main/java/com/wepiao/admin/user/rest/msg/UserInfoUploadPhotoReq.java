package com.wepiao.admin.user.rest.msg;

// 实现需要讨论
@Deprecated
public class UserInfoUploadPhotoReq extends BaseReq {
    private String memberId;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public UserInfoUploadPhotoReq() {
        super();
    }

    public UserInfoUploadPhotoReq(String memberId) {
        super();
        this.memberId = memberId;
    }
}
