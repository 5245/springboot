package com.wepiao.user.common.entry;

import com.wepiao.user.common.entry.enumeration.OtherID;

public class UserTag extends BaseSplittedEntry {
    private Integer rid;
    private String  id;
    private OtherID idType;
    private String  tag;

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public OtherID getIdType() {
        return idType;
    }

    public void setIdType(OtherID idType) {
        this.idType = idType;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public UserTag() {
        super();
    }

    public UserTag(Integer rid, String id, OtherID idType, String tag) {
        super();
        this.rid = rid;
        this.id = id;
        this.idType = idType;
        this.tag = tag;
    }
}
