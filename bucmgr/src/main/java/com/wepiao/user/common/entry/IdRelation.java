package com.wepiao.user.common.entry;

import java.util.Date;

import com.wepiao.user.common.entry.enumeration.OtherID;

public class IdRelation extends BaseSplittedEntry {
    private Integer id;
    private String  childId;
    private OtherID childIdType;
    private String  parentId;
    private OtherID parentIdType;
    private Date    updateTime;

    public Integer getId() {
        return id;
    }

    public String getChildId() {
        return childId;
    }

    public OtherID getChildIdType() {
        return childIdType;
    }

    public String getParentId() {
        return parentId;
    }

    public OtherID getParentIdType() {
        return parentIdType;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }

    public void setChildIdType(OtherID childIdType) {
        this.childIdType = childIdType;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public void setParentIdType(OtherID parentIdType) {
        this.parentIdType = parentIdType;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public IdRelation(Integer id, String childId, OtherID childIdType, String parentId, OtherID parentIdType, Date updateTime) {
        super();
        this.id = id;
        this.childId = childId;
        this.childIdType = childIdType;
        this.parentId = parentId;
        this.parentIdType = parentIdType;
        this.updateTime = updateTime;
    }

    public IdRelation(IdRelationNode childNode, IdRelationNode parentNode, Date updateTime) {
        super();
        this.childId = childNode.getId();
        this.childIdType = childNode.getIdType();
        this.parentId = parentNode.getId();
        this.parentIdType = parentNode.getIdType();
        this.updateTime = updateTime;
    }
}
