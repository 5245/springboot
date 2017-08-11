package com.wepiao.user.common.entry;

import java.util.Date;

import com.wepiao.user.common.entry.enumeration.BindingStatus;
import com.wepiao.user.common.entry.enumeration.OtherID;

public class IdRelationHistory extends BaseSplittedEntry {
    private Integer       id;
    private String        parentId;
    private OtherID       parentIdType;
    private String        childId;
    private OtherID       childIdType;
    private BindingStatus bindingStatus;
    private Date          updateTime;

    public Integer getId() {
        return id;
    }

    public String getParentId() {
        return parentId;
    }

    public OtherID getParentIdType() {
        return parentIdType;
    }

    public String getChildId() {
        return childId;
    }

    public OtherID getChildIdType() {
        return childIdType;
    }

    public BindingStatus getBindingStatus() {
        return bindingStatus;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public void setParentIdType(OtherID parentIdType) {
        this.parentIdType = parentIdType;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }

    public void setChildIdType(OtherID childIdType) {
        this.childIdType = childIdType;
    }

    public void setBindingStatus(BindingStatus bindingStatus) {
        this.bindingStatus = bindingStatus;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public IdRelationHistory(Integer id, String parentId, OtherID parentIdType, String childId, OtherID childIdType, BindingStatus bindingStatus,
            Date updateTime) {
        super();
        this.id = id;
        this.parentId = parentId;
        this.parentIdType = parentIdType;
        this.childId = childId;
        this.childIdType = childIdType;
        this.bindingStatus = bindingStatus;
        this.updateTime = updateTime;
    }

    public IdRelationHistory(IdRelationNode parentNode, IdRelationNode childNode, BindingStatus bindingStatus, Date updateTime) {
        super();
        this.childId = childNode.getId();
        this.childIdType = childNode.getIdType();
        this.parentId = parentNode.getId();
        this.parentIdType = parentNode.getIdType();
        this.bindingStatus = bindingStatus;
        this.updateTime = updateTime;
    }

}
