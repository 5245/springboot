package com.wepiao.admin.user.rest.msg;

import java.util.List;

public class UserTagGetReq extends BaseReq {
    private String       id;
    private int          idType;
    private List<String> tagList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public List<String> getTagList() {
        return tagList;
    }

    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
    }

    public UserTagGetReq() {
        super();
    }

    public UserTagGetReq(String id, int idType, List<String> tagList) {
        super();
        this.id = id;
        this.idType = idType;
        this.tagList = tagList;
    }

}
