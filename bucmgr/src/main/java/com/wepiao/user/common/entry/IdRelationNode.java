package com.wepiao.user.common.entry;

import java.util.ArrayList;
import java.util.List;

import com.wepiao.user.common.entry.enumeration.OtherID;
import com.wepiao.user.common.entry.enumeration.SubOtherID;

public class IdRelationNode implements Comparable<IdRelationNode> {
    private String               id;
    private OtherID              idType;
    private SubOtherID           subIdType;
    private List<IdRelationNode> idUnderBound;

    public String getId() {
        return id;
    }

    public OtherID getIdType() {
        return idType;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIdType(OtherID idType) {
        this.idType = idType;
    }

    public SubOtherID getSubIdType() {
		return subIdType;
	}

	public void setSubIdType(SubOtherID subIdType) {
		this.subIdType = subIdType;
	}

	public List<IdRelationNode> getIdUnderBound() {
        return idUnderBound;
    }

    public void setIdUnderBound(List<IdRelationNode> childIdList) {
        this.idUnderBound = childIdList;
    }

    public void addBoundId(IdRelationNode node) {
        if (null == this.idUnderBound) {
            this.idUnderBound = new ArrayList<IdRelationNode>();
        }
        this.idUnderBound.add(node);
    }

    public IdRelationNode(String id, OtherID idType) {
        this(id, idType, SubOtherID.OTHER);
    }
    
    public IdRelationNode(String id, OtherID idType, SubOtherID subIdType) {
        super();
        this.id = id;
        this.idType = idType;
        this.subIdType = subIdType;
    }

    @Override
    public int compareTo(IdRelationNode another) {
        // Note: level标号越小则级别越高
        int thisLevel = this.getIdType().getLevel().getIntVal();
        int anotherLevel = another.getIdType().getLevel().getIntVal();
        if (thisLevel > anotherLevel) {
            return -1;
        } else if (thisLevel < anotherLevel) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return "IdRelationNode [id=" + id + ", idType=" + idType + ", subIdType=" + subIdType + ", idUnderBound=" + idUnderBound + "]";
    }
    
}
