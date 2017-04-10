package com.wepiao.admin.user.rest.msg;

import com.wepiao.user.common.entry.IdRelationNode;

public class IdRelationGetRes extends BaseRes {
    private IdRelationNode idRelationNode;

    public IdRelationNode getIdRelation() {
        return idRelationNode;
    }

    public void setIdRelation(IdRelationNode idRelationNode) {
        this.idRelationNode = idRelationNode;
    }

    public IdRelationGetRes(IdRelationNode idRelationNode) {
        super();
        this.idRelationNode = idRelationNode;
    }
}
