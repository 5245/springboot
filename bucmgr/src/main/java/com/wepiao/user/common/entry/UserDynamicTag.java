package com.wepiao.user.common.entry;

import com.wepiao.user.common.entry.enumeration.OtherID;

public class UserDynamicTag extends BaseSplittedEntry {
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

    public UserDynamicTag() {
        super();
    }

    public UserDynamicTag(Integer rid, String id, OtherID idType, String tag) {
        super();
        this.rid = rid;
        this.id = id;
        this.idType = idType;
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "UserDynamicTag [rid=" + rid + ", id=" + id + ", idType=" + idType + ", tag=" + tag + "]";
    }
}
