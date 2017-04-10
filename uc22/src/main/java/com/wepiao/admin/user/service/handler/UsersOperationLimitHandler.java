package com.wepiao.admin.user.service.handler;

import com.wepiao.admin.user.rest.msg.BindMobileNoReq;

/**
 * Data Model-User Operation Limit的处理类，用于处理其信息在Redis和DB间的同步
 *
 */
public interface UsersOperationLimitHandler {

    /**
     * 添加登录失败的记录
     * 
     * @param mobileNo
     */
    void addLoginFailRecord(String mobileNo);

    /**
     * 添加登录失败的记录
     * 
     * @param mobileNo
     */
    void addUpdatePWCount(String memberId);

    /**
     * 判断手机号是否是登录限制用户
     * 
     * @param mobileNo
     * @return
     */
    boolean isLoginLimit(String mobileNo);

    /**
     * 将手机号加入限制登录
     * 
     * @param mobileNo
     */
    void addLoginLimit(String mobileNo);

    /**
     * 判断memberId是否是密码操作限制用户
     * 
     * @param memberId
     * @return
     */
    boolean isUpdatePWLimit(String memberId);

    /**
     * 将memberId加入密码操作限制用户
     * 
     * @param mobileNo
     */
    void addUpdatePWLimit(String memberId);

    /**
     * 检查memberId时段内是否已达到绑定次数限制 <br/>
     * 
     * @param memberId
     */
    void checkMemberIdBindCount(Integer memberId, String reqId);

    /**
     * checkOpenIdBindCount: <br/>
     * 检查openid是否达到绑定次数限制<br/>
     * 
     * @param openId
     * @param reqId
     */
    void checkOpenIdBindCount(BindMobileNoReq req);

    /**
     * checkUpdateMobileCount: <br/>
     * 检查该memberId是否超过手机号修改的限制<br/>
     * 
     * @param memberId
     * @param reqId
     */
    void checkUpdateMobileCount(String memberId, String reqId);

    /**
     * recordUserBindOperate: <br/>
     * 记录用户绑定成功操作 <br/>
     * 
     * @param memberId
     * @param openId
     */
    void recordUserBindOperate(BindMobileNoReq req, Integer memberId);

    /**
     * recordUserBindOperate: <br/>
     * 记录用户修改手机号成功操作 <br/>
     * 
     * @param redisKey
     * @param time
     */
    void recordUserLimitOperate(String redisKey, Integer time);
}
