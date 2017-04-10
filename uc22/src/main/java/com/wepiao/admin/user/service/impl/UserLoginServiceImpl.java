package com.wepiao.admin.user.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import redis.clients.jedis.exceptions.JedisConnectionException;

import com.wepiao.admin.user.rest.msg.DeviceInfo;
import com.wepiao.admin.user.rest.msg.UserLoginReq;
import com.wepiao.admin.user.rest.msg.UserLoginRes;
import com.wepiao.admin.user.service.UserLoginService;
import com.wepiao.admin.user.service.handler.UserDeviceHandler;
import com.wepiao.admin.user.service.handler.UserTagHandler;
import com.wepiao.admin.user.service.handler.UsersOperationLimitHandler;
import com.wepiao.user.common.constant.Constants;
import com.wepiao.user.common.constant.LogMsg;
import com.wepiao.user.common.entry.IdRelationNode;
import com.wepiao.user.common.entry.Users;
import com.wepiao.user.common.entry.enumeration.OtherID;
import com.wepiao.user.common.enumeration.ResponseInfoEnum;
import com.wepiao.user.common.handler.MobileNum2UIDHandler;
import com.wepiao.user.common.handler.UsersHandler;
import com.wepiao.user.common.redis.RedisKey;
import com.wepiao.user.common.rest.exception.BaseRestException;
import com.wepiao.user.common.service.IdRelationService;
import com.wepiao.user.common.util.LogMessageFormatter;
import com.wepiao.user.common.util.MD5Utils;

@Service
public class UserLoginServiceImpl implements UserLoginService {

    private static final Logger        logger         = LoggerFactory.getLogger(UserLoginServiceImpl.class);

    private static final Logger        securityLogger = LoggerFactory.getLogger(Constants.LOG_SECURITY);

    @Autowired
    private UsersHandler               usersHandler;

    @Autowired
    private MobileNum2UIDHandler       mobileNum2UIDHandler;

    @Autowired
    private UserDeviceHandler          userDeviceHandler;

    @Autowired
    private IdRelationService          idRelationService;

    @Autowired
    private UserTagHandler             userTagHandler;

    @Autowired
    private UsersOperationLimitHandler usersOperationLimitHandler;

    /**
    * 通过手机号和密码登录
    * @param req
    * @param deviceinfoList
    * @throws BaseRestException
    *
    */
    @Override
    public UserLoginRes login(UserLoginReq req, List<DeviceInfo> deviceinfoList) throws BaseRestException {
        UserLoginRes res = null;
        try {
            boolean isLoginLimit = usersOperationLimitHandler.isLoginLimit(req.getMobileNo());
            if (isLoginLimit) {
                securityLogger
                        .warn(LogMsg.BASE_MSG, req.getRequestId(), LogMessageFormatter.format(LogMsg.MOBILE_LOGIN_FAIL_MORE, req.getMobileNo()));
                throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E20013, req.getMobileNo());
            }
            // 取mobile对应的UID
            int uid = mobileNum2UIDHandler.getUIDByMobileNo(req.getMobileNo());
            if (Constants.NOT_EXISTED_UID == uid) {
                usersOperationLimitHandler.addLoginFailRecord(req.getMobileNo());
                logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMessageFormatter.format(LogMsg.MOBILE2UID_NOT_FOUND, req.getMobileNo()));
                throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E20008, req.getMobileNo());
            }

            Users usr = usersHandler.queryOneByUid(uid);
            // 没有用户记录，抛出异常
            if (null == usr) {
                usersOperationLimitHandler.addLoginFailRecord(req.getMobileNo());
                logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMessageFormatter.format(LogMsg.MEMBER_ID_NOT_FOUND_DETAIL, uid));
                throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10005);
            } else {
                String password = usr.getPassword();
                if (null == password) {
                    // 数据库中无密码
                    logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMsg.PASSWORD_IS_NULL_IN_DB);
                    throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E20007, LogMsg.PASSWORD_IS_NULL_IN_DB);
                } else {
                    // 密码匹配检查，均为MD5加密
                    String reqPassword = req.getPassword();
                    if (MD5Utils.validatePassword(reqPassword, password)) {
                        String openId = null;
                        // 获取OtherID类型为手机注册的openId
                        List<IdRelationNode> openIdList = idRelationService.getIdListFromRootByType(new IdRelationNode(String.valueOf(uid),
                                OtherID.UID), OtherID.MOBILE);
                        if (null != openIdList && !openIdList.isEmpty()) {
                            // 因为一个uid仅可能有一个otherId为手机注册的openId， 则取第1个即可
                            openId = openIdList.get(0).getId();
                        }

                        String signature = null;
                        int maritalStat = 0;
                        int carrer = 0;
                        int enrollmentYear = 0;
                        int highestEdu = 0;
                        String school = null;
                        int watchCPNum = 0;
                        Map<String, String> tagMap = userTagHandler.queryAllUserTag(String.valueOf(uid), OtherID.UID);
                        if (null != tagMap && 0 < tagMap.size()) {
                            signature = (null != tagMap.get("signature")) ? tagMap.get("signature").split(RedisKey.COMMON_SEPARATOR)[0] : "";
                            maritalStat = (null != tagMap.get("maritalstat")) ? Integer.parseInt(tagMap.get("maritalstat").split(
                                    RedisKey.COMMON_SEPARATOR)[0]) : 0;
                            carrer = (null != tagMap.get("carrer")) ? Integer.parseInt(tagMap.get("carrer").split(RedisKey.COMMON_SEPARATOR)[0]) : 0;
                            enrollmentYear = (null != tagMap.get("enrollmentyear")) ? Integer.parseInt(tagMap.get("enrollmentyear").split(
                                    RedisKey.COMMON_SEPARATOR)[0]) : 0;
                            highestEdu = (null != tagMap.get("highestedu")) ? Integer.parseInt(tagMap.get("highestedu").split(
                                    RedisKey.COMMON_SEPARATOR)[0]) : 0;
                            school = (null != tagMap.get("school")) ? tagMap.get("school").split(RedisKey.COMMON_SEPARATOR)[0] : "";
                            watchCPNum = (null != tagMap.get("watchcpnum")) ? Integer.parseInt(tagMap.get("watchcpnum").split(
                                    RedisKey.COMMON_SEPARATOR)[0]) : 0;
                        }
                        res = new UserLoginRes(uid, usersHandler.getExtUidByUid(uid), openId, usr.getMobileNo(), usr.getNickName(), usr.getPhoto(),
                                usr.getStatus().getIntVal(), usr.getArea(), usr.getSex().getIntVal(), usr.getBirthday(), usr.getEmail(),
                                usr.getUserName(), usr.getUserKey(), signature, maritalStat, carrer, enrollmentYear, highestEdu, school, watchCPNum);
                    } else {// 密码错误
                        usersOperationLimitHandler.addLoginFailRecord(req.getMobileNo());
                        logger.warn(LogMsg.BASE_MSG, req.getRequestId(),
                                LogMessageFormatter.format(LogMsg.PASSWORD_VERIFY_FAILED_DETAIL, req.getMobileNo(), reqPassword));
                        throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10002, LogMsg.PASSWORD_VERIFY_FAILED);
                    }
                }

                // 6.记录用户的deviceid
                userDeviceHandler.addUserDevice(String.valueOf(uid), OtherID.UID, deviceinfoList);
            }
        } catch (NoSuchAlgorithmException ne) {
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10002, ne.getMessage());
        } catch (UnsupportedEncodingException ue) {
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10002, ue.getMessage());
        } catch (BaseRestException be) {
            throw be;
        } catch (JedisConnectionException je) {
            logger.error(LogMsg.BASE_MSG, req.getRequestId(), je.getMessage());
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E50002);
        } catch (DataAccessException me) {
            logger.error(LogMsg.BASE_MSG, req.getRequestId(), me.getMessage());
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E50001);
        }
        return res;
    }
}
