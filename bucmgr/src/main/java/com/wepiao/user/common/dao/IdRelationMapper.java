package com.wepiao.user.common.dao;

import java.util.List;

import com.wepiao.user.common.entry.IdRelation;

public interface IdRelationMapper {

    public int insertRelation(IdRelation relation);

    /**
     * 目前仅通过childId标识唯一性，认为不带idType也可以保证唯一
     * @param childId
     * @return
     */
    public List<IdRelation> queryRelationByChild(String childId);

    public IdRelation isRelationExisted(IdRelation relation);

    public int deleteRelation(String childId, String parentId);
}
