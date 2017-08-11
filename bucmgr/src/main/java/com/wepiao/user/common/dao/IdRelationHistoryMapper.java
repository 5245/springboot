package com.wepiao.user.common.dao;

import java.util.List;

import com.wepiao.user.common.entry.IdRelationHistory;
import com.wepiao.user.common.entry.enumeration.BindingStatus;

public interface IdRelationHistoryMapper {
    /**
     * 插入用户关系
     * @param relation
     * @return
     */
    public int insertRelation(IdRelationHistory relation);

    /**
     * 查询用户关系
     * 目前仅通过parentId标识唯一性，认为不带idType也可以保证唯一
     * @param parentId
     * @return
     */
    public List<IdRelationHistory> queryRelationByParent(String parentId);

    /**
     * 查询用户关系
     * 目前仅通过parentId标识唯一性，认为不带idType也可以保证唯一
     * @param parentId
     * @param bindingStatus
     * @return
     */
    public List<IdRelationHistory> queryRelationByParentAndStatus(String parentId, BindingStatus bindingStatus);

    /**
     * 更新用户关系的绑定状态
     * @param childId
     * @param parentId
     * @return
     */
    public int updateBindingStatus(String parentId, String childId, BindingStatus bindingStatus);

    /**
     * 更新父节点下面所有用户关系的绑定状态 (先提供给ucmgr删除用户，更新memberId下面所有节点的状态)
     * @param parentId
     * @return
     *
     */
    public int updateAllBindingStatus(String parentId, BindingStatus bindingStatus);
}
