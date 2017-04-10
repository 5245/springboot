package com.wepiao.admin.user.service.handler;

import java.util.Map;

import com.wepiao.user.common.entry.enumeration.OtherID;
import com.wepiao.user.common.rest.exception.BaseRestException;

public interface UserTagHandler {
    /**
     * 添加用户静态标签
     * @param id
     * @param idType
     * @param tag
     * @param tagVal
     * @param needPersist
     * @param expire
     * @return
     * @throws BaseRestException
     */
    public boolean addUserTag(String id, OtherID idType, String tag, String tagVal, boolean needPersist, long expire) throws BaseRestException;

    @Deprecated
    public boolean removeUserTag(String id, OtherID idType, String tag) throws BaseRestException;

    /**
     * 查询某个用户静态标签的值
     * @param id
     * @param idType
     * @param tag
     * @return
     * @throws BaseRestException
     */
    public String queryUserTag(String id, OtherID idType, String tag) throws BaseRestException;

    /**
     * 查询某个用户所有静态标签的值
     * @param id
     * @param idType
     * @return
     * @throws BaseRestException
     */
    public Map<String, String> queryAllUserTag(String id, OtherID idType) throws BaseRestException;

    /**
     * 添加动态标签
     * @param id
     * @param idType
     * @param tag
     * @param tagVal
     * @param expire
     * @return
     * @throws BaseRestException
     */
    public boolean addDynamicUserTag(String id, OtherID idType, String tag, String tagVal, long expire) throws BaseRestException;

    /**
     * 查询某个动态标签的值
     * @param id
     * @param idType
     * @param tag
     * @return
     * @throws BaseRestException
     */
    public String queryDynamicUserTag(String id, OtherID idType, String tag) throws BaseRestException;

    /**
     * 获取某id对应的所有动态标签的值
     * @param id
     * @param idType
     * @return
     * @throws BaseRestException
     */
    public Map<String, String> queryAllDynamicUserTag(String id, OtherID idType) throws BaseRestException;
}
