package com.wepiao.admin.user.service.handler.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.wepiao.admin.user.rest.msg.BindMobileNoReq;
import com.wepiao.admin.user.service.handler.UsersOperationLimitHandler;
import com.wepiao.admin.user.service.impl.UserInfoUpdateServiceImpl;
import com.wepiao.user.common.constant.Constants;
import com.wepiao.user.common.constant.LogMsg;
import com.wepiao.user.common.entry.enumeration.OtherID;
import com.wepiao.user.common.enumeration.ResponseInfoEnum;
import com.wepiao.user.common.redis.RedisKey;
import com.wepiao.user.common.redis.RedisMapper;
import com.wepiao.user.common.rest.exception.BaseRestException;
import com.wepiao.user.common.util.ConfigPropertiesUtil;
import com.wepiao.user.common.util.DateUtils;
import com.wepiao.user.common.util.LogMessageFormatter;

@Component
public class UsersOperationLimitHandlerImpl implements UsersOperationLimitHandler {

    private static final Logger               logger     = LoggerFactory.getLogger(UserInfoUpdateServiceImpl.class);

    private static final ConfigPropertiesUtil configUtil = ConfigPropertiesUtil.getInstance();

    /**
     * 添加登录失败的记录
     * 
     * @param mobileNo
     */
    public void addLoginFailRecord(String mobileNo) {
        int count = RedisMapper.setUserLoginFail(mobileNo);
        if (count >= Constants.LOGIN_FAIL_LOCK_COUNT) {
            addLoginLimit(mobileNo);
        }
    }

    /**
     * 添加登录失败的记录
     * 
     * @param mobileNo
     */
    public void addUpdatePWCount(String memberId) {
        int count = RedisMapper.setUserUpdatePW(memberId);
        if (count >= Constants.UPDATE_PW_LOCK_COUNT) {
            addUpdatePWLimit(memberId);
        }
    }

    /**
     * 判断手机号是否是登录限制用户
     * 
     * @param mobileNo
     * @return
     */
    public boolean isLoginLimit(String mobileNo) {
        return RedisMapper.isUserLoginLimit(mobileNo);
    }

    /**
     * 将手机号加入限制登录
     * 
     * @param mobileNo
     */
    public void addLoginLimit(String mobileNo) {
        RedisMapper.setUserLoginLimit(mobileNo);
    }

    /**
     * 判断memberId是否是密码操作限制用户
     * 
     * @param memberId
     * @return
     */
    public boolean isUpdatePWLimit(String memberId) {
        return RedisMapper.isUserUpdatePWLimit(memberId);
    }

    /**
     * 将memberId加入密码操作限制用户
     * 
     * @param mobileNo
     */
    public void addUpdatePWLimit(String memberId) {
        RedisMapper.setUserUpdatePWLimit(memberId);
    }

    /**
     * checkMemberIdBindCount: <br/>
     * 检查memberId时段内是否已达到绑定次数限制 <br/>
     * 
     * @param memberId
     * @return
     */
    public void checkMemberIdBindCount(Integer memberId, String reqId) {
        // 用当前时间-往前30天的时间, 从区间中获取该用户修改手机号的次数
        Integer days = Integer.parseInt(configUtil.get(Constants.BIND_MEMBERID_LIMIT_TIME_SCOPE));
        Integer allowCount = Integer.parseInt(configUtil.get(Constants.BIND_MEMBERID_LIMIT_COUNT));
        Integer startTime = DateUtils.getBeforeDayTimestamp(DateUtils.getCurrentTimestamp(), days);
        String redisKey = String.format(RedisKey.USER_MEMBERID_BIND_LIMITED_KEY, memberId);
        Long count = RedisMapper.getUserLimitOperateCount(redisKey, startTime, DateUtils.getCurrentTimestamp());
        // 删除已超过时限的记录
        RedisMapper.removeUserLimitOperate(redisKey, DateUtils.getBeforeDayTimestamp(startTime, days), startTime);
        // 达到限制的返回错误
        if (count >= allowCount) {
            String msg = LogMessageFormatter.format(LogMsg.BIND_MEMBERID_REACHED_LIMIT, memberId);
            logger.warn(LogMsg.BASE_MSG, memberId, msg);
            throw new BaseRestException(reqId, ResponseInfoEnum.E20019, days.toString());
        }
    }

    /**
     * checkOpenIdBindCount: <br/>
     * 检查openid是否达到绑定次数限制<br/>
     * 
     * @param openId
     * @param reqId
     */
    public void checkOpenIdBindCount(BindMobileNoReq req) {
        int otherId = req.getOtherId();
        String recordId = null;
        if (OtherID.WEIXIN.getIntVal() == otherId) {
            recordId = req.getUnionId();
        } else {
            recordId = req.getOpenId();
        }
        // 用当前时间-往前30天的时间, 从区间中获取该用户修改手机号的次数
        Integer days = Integer.parseInt(configUtil.get(Constants.BIND_OPENID_LIMIT_TIME_SCOPE));
        Integer allowCount = Integer.parseInt(configUtil.get(Constants.BIND_OPENID_LIMIT_COUNT));
        Integer startTime = DateUtils.getBeforeDayTimestamp(DateUtils.getCurrentTimestamp(), days);
        String redisKey = String.format(RedisKey.USER_OPENID_BIND_LIMITED_KEY, recordId);
        Long count = RedisMapper.getUserLimitOperateCount(redisKey, startTime, DateUtils.getCurrentTimestamp());
        // 删除已超过时限的记录
        RedisMapper.removeUserLimitOperate(redisKey, DateUtils.getBeforeDayTimestamp(startTime, days), startTime);
        // 达到限制的返回错误
        if (count >= allowCount) {
            String msg = LogMessageFormatter.format(LogMsg.BIND_OPENID_REACHED_LIMIT, recordId);
            logger.warn(LogMsg.BASE_MSG, req.getRequestId(), msg);
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E20019, days.toString());
        }
    }

    /**
     * checkUpdateMobileCount: <br/>
     * 检查该memberId是否超过手机号修改的限制<br/>
     * 
     * @param memberId
     * @param reqId
     */
    public void checkUpdateMobileCount(String memberId, String reqId) {
        // 用当前时间-往前30天的时间, 从区间中获取该用户修改手机号的次数
        Integer days = Integer.parseInt(configUtil.get(Constants.UPDATE_MOBILE_LIMIT_TIME_SCOPE));
        Integer allowCount = Integer.parseInt(configUtil.get(Constants.UPDATE_MOBILE_LIMIT_COUNT));
        Integer startTime = DateUtils.getBeforeDayTimestamp(DateUtils.getCurrentTimestamp(), days);
        String redisKey = String.format(RedisKey.USER_UPDATE_MOBILE_NO_LIMITED_KEY, memberId);
        Long count = RedisMapper.getUserLimitOperateCount(redisKey, startTime, DateUtils.getCurrentTimestamp());
        // 删除已超过时限的记录
        RedisMapper.removeUserLimitOperate(redisKey, DateUtils.getBeforeDayTimestamp(startTime, days), startTime);
        // 达到限制的返回错误
        if (count >= allowCount) {
            String msg = LogMessageFormatter.format(LogMsg.UPDATE_MOBILE_REACHED_LIMIT, memberId);
            logger.warn(LogMsg.BASE_MSG, reqId, msg);
            throw new BaseRestException(reqId, ResponseInfoEnum.E20018, days.toString());
        }
    }

    /**
     * recordUserBindOperate: <br/>
     * 记录用户绑定成功操作 <br/>
     * 
     * @param memberId
     * @param openId
     */
    public void recordUserBindOperate(BindMobileNoReq req, Integer memberId) {
        if (null != memberId) {
            int otherId = req.getOtherId();
            String recordId = null;
            if (OtherID.WEIXIN.getIntVal() == otherId) {
                recordId = req.getUnionId();
            } else {
                recordId = req.getOpenId();
            }

            String memberIdRedisKey = String.format(RedisKey.USER_MEMBERID_BIND_LIMITED_KEY, memberId.toString());
            RedisMapper.recordUserLimitOperate(memberIdRedisKey, DateUtils.getCurrentTimestamp());
            String openIdRedisKey = String.format(RedisKey.USER_OPENID_BIND_LIMITED_KEY, recordId);
            RedisMapper.recordUserLimitOperate(openIdRedisKey, DateUtils.getCurrentTimestamp());
        }
    }

    /**
     * recordUserBindOperate: <br/>
     * 记录用户修改手机号成功操作 <br/>
     * 
     * @param redisKey
     * @param time
     */
    public void recordUserLimitOperate(String redisKey, Integer time) {
        RedisMapper.recordUserLimitOperate(redisKey, time);
    }
}
