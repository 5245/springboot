package com.wepiao.admin.user.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import redis.clients.jedis.exceptions.JedisConnectionException;

import com.wepiao.admin.user.rest.msg.BindMobileNoReq;
import com.wepiao.admin.user.rest.msg.BindMobileNoRes;
import com.wepiao.admin.user.rest.msg.ChangePasswordReq;
import com.wepiao.admin.user.rest.msg.SingleResultRes;
import com.wepiao.admin.user.rest.msg.UnBindOpenIDReq;
import com.wepiao.admin.user.rest.msg.UpdateMobileNoReq;
import com.wepiao.admin.user.rest.msg.UserInfoUpdateRes;
import com.wepiao.admin.user.service.UserInfoUpdateService;
import com.wepiao.admin.user.service.handler.OpenIdInfoHandler;
import com.wepiao.admin.user.service.handler.UserTagHandler;
import com.wepiao.admin.user.service.handler.UsersOperationLimitHandler;
import com.wepiao.user.common.constant.Constants;
import com.wepiao.user.common.constant.LogMsg;
import com.wepiao.user.common.constant.MsgMqConstants;
import com.wepiao.user.common.dao.OpenIdInfoMapper;
import com.wepiao.user.common.entry.IdRelationNode;
import com.wepiao.user.common.entry.MobileNoMapping;
import com.wepiao.user.common.entry.OpenIdInfo;
import com.wepiao.user.common.entry.Users;
import com.wepiao.user.common.entry.enumeration.BindingStatus;
import com.wepiao.user.common.entry.enumeration.Gender;
import com.wepiao.user.common.entry.enumeration.OtherID;
import com.wepiao.user.common.entry.enumeration.Status;
import com.wepiao.user.common.enumeration.ResponseInfoEnum;
import com.wepiao.user.common.handler.IdRelationHandler;
import com.wepiao.user.common.handler.MobileNum2UIDHandler;
import com.wepiao.user.common.handler.UsersHandler;
import com.wepiao.user.common.mq.MsgProducer;
import com.wepiao.user.common.mq.entity.MobileUpdateMsgInfo;
import com.wepiao.user.common.mq.entity.MsgInfo;
import com.wepiao.user.common.mq.entity.UserMsgInfo;
import com.wepiao.user.common.redis.RedisKey;
import com.wepiao.user.common.redis.RedisUtils;
import com.wepiao.user.common.redis.RedisUtils4UidGenerate;
import com.wepiao.user.common.rest.exception.BaseRestException;
import com.wepiao.user.common.service.IdRelationService;
import com.wepiao.user.common.util.DateUtils;
import com.wepiao.user.common.util.EmojiUtils;
import com.wepiao.user.common.util.LogMessageFormatter;
import com.wepiao.user.common.util.MD5Utils;
import com.wepiao.user.common.util.StringUtil;

@Service
public class UserInfoUpdateServiceImpl implements UserInfoUpdateService {

    private static final Logger        logger         = LoggerFactory.getLogger(UserInfoUpdateServiceImpl.class);

    private static final Logger        securityLogger = LoggerFactory.getLogger(Constants.LOG_SECURITY);

    // 120年的毫秒数
    public static final long           MAX_AGE        = 3784320000000L;

    @Autowired
    private UsersHandler               usersHandler;

    @Autowired
    private UserTagHandler             userTagHandler;

    @Autowired
    private OpenIdInfoHandler          openIdInfoHandler;

    @Autowired
    private OpenIdInfoMapper           openIdInfoMapper;

    @Autowired
    private MobileNum2UIDHandler       mobileNum2UIDHandler;

    @Autowired
    private IdRelationHandler          idRelationHandler;

    @Autowired
    private IdRelationService          idRelationService;

    @Autowired
    private UsersOperationLimitHandler usersOperationLimitHandler;

    @Autowired
    private MsgProducer                msgProducer;

    /**
     * 根据用户的uid修改用户的信息
     * 
     * @param reqId
     * @param reqMap
     * @return
     * @throws BaseRestException
     *
     */
    @Override
    public UserInfoUpdateRes updateUserInfo(String reqId, Map<String, Object> reqMap) throws BaseRestException {
        UserInfoUpdateRes res = null;
        try {
            Object unknownUidObj = reqMap.get("memberid");
            String unknownUid = String.valueOf(unknownUidObj);
            Integer uid = usersHandler.getUid(unknownUid);
            if (null == uid) {
                logger.warn(LogMsg.BASE_MSG, reqId, LogMessageFormatter.format(LogMsg.MEMBER_ID_NOT_FOUND_DETAIL, unknownUid));
                throw new BaseRestException(reqId, ResponseInfoEnum.E10005);
            }
            Users user = usersHandler.queryOneByUid(uid);
            if (null == user) {
                logger.warn(LogMsg.BASE_MSG, reqId, LogMessageFormatter.format(LogMsg.MEMBER_ID_NOT_FOUND_DETAIL, uid));
                throw new BaseRestException(reqId, ResponseInfoEnum.E10005);
            } else {
                // 额外的user profile，定义成静态标签
                String signature = null;
                int maritalStat = 0;
                int carrer = 0;
                int enrollmentYear = 0;
                int highestEdu = 0;
                String school = null;
                int watchCPNum = 0;
                Map<String, String> tagMap = userTagHandler.queryAllUserTag(String.valueOf(user.getUid()), OtherID.UID);
                if (null != tagMap && 0 < tagMap.size()) {
                    signature = (null != tagMap.get("signature")) ? tagMap.get("signature").split(RedisKey.COMMON_SEPARATOR)[0] : "";
                    maritalStat = (null != tagMap.get("maritalstat")) ? Integer
                            .parseInt(tagMap.get("maritalstat").split(RedisKey.COMMON_SEPARATOR)[0]) : 0;
                    carrer = (null != tagMap.get("carrer")) ? Integer.parseInt(tagMap.get("carrer").split(RedisKey.COMMON_SEPARATOR)[0]) : 0;
                    enrollmentYear = (null != tagMap.get("enrollmentyear")) ? Integer.parseInt(tagMap.get("enrollmentyear").split(
                            RedisKey.COMMON_SEPARATOR)[0]) : 0;
                    highestEdu = (null != tagMap.get("highestedu")) ? Integer.parseInt(tagMap.get("highestedu").split(RedisKey.COMMON_SEPARATOR)[0])
                            : 0;
                    school = (null != tagMap.get("school")) ? tagMap.get("school").split(RedisKey.COMMON_SEPARATOR)[0] : "";
                    watchCPNum = (null != tagMap.get("watchcpnum")) ? Integer.parseInt(tagMap.get("watchcpnum").split(RedisKey.COMMON_SEPARATOR)[0])
                            : 0;
                }
                // 遍历请求中所有的可能允许的key(key已在Controller预先统一变成小写)...
                for (Map.Entry<String, Object> entry : reqMap.entrySet()) {
                    if ("city".equalsIgnoreCase(entry.getKey())) {
                        user.setArea((String) entry.getValue());
                    }
                    if ("nickname".equalsIgnoreCase(entry.getKey())) {
                        user.setNickName(EmojiUtils.replaceEmoji((String) entry.getValue()));
                    }
                    if ("sex".equalsIgnoreCase(entry.getKey())) {
                        if (null != entry.getValue()) {
                            try {
                                user.setSex(Gender.parseInt((Integer.parseInt(entry.getValue().toString()))));
                            } catch (NumberFormatException nfe) {
                                logger.warn(LogMsg.BASE_MSG, reqId, LogMessageFormatter.format(LogMsg.ERR_STRING2INTEGER, entry.getValue()));
                                throw new BaseRestException(ResponseInfoEnum.E10002, LogMessageFormatter.format(LogMsg.ERR_STRING2INTEGER,
                                        entry.getValue()));
                            } catch (IllegalArgumentException iae) {
                                logger.error(LogMsg.BASE_MSG, reqId, iae.getMessage());
                                throw new BaseRestException(ResponseInfoEnum.E10002, LogMessageFormatter.format(LogMsg.ERR_GENDER_TYPE,
                                        entry.getValue()));
                            }
                        }
                    }
                    if ("birthday".equalsIgnoreCase(entry.getKey())) {
                        String birthdayStr = (String) entry.getValue();
                        if (null != birthdayStr) {
                            try {
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                Date birthday = sdf.parse(birthdayStr);
                                long minTime = System.currentTimeMillis() - MAX_AGE;
                                if (minTime < birthday.getTime() && birthday.getTime() < System.currentTimeMillis()) {
                                    user.setBirthday(birthday);
                                } else {
                                    logger.warn(LogMsg.BASE_MSG, reqId, LogMessageFormatter.format(LogMsg.ERR_STRING2DATE, birthdayStr));
                                    throw new BaseRestException(ResponseInfoEnum.E10002, birthdayStr);
                                }
                            } catch (ParseException pe) {
                                logger.warn(LogMsg.BASE_MSG, reqId, LogMessageFormatter.format(LogMsg.ERR_STRING2DATE, birthdayStr));
                                throw new BaseRestException(ResponseInfoEnum.E10002, birthdayStr);
                            }
                        }
                    }
                    if ("email".equalsIgnoreCase(entry.getKey())) {
                        user.setEmail((String) entry.getValue());
                    }
                    if ("photourl".equalsIgnoreCase(entry.getKey())) {
                        user.setPhoto((String) entry.getValue());
                    }
                    if ("name".equalsIgnoreCase(entry.getKey())) {
                        user.setUserName((String) entry.getValue());
                    }
                    if ("userkey".equalsIgnoreCase(entry.getKey())) {
                        user.setUserKey((String) entry.getValue());
                    }
                    if ("signature".equalsIgnoreCase(entry.getKey())) {
                        signature = EmojiUtils.replaceEmoji((String) entry.getValue());
                        userTagHandler.addUserTag(String.valueOf(user.getUid()), OtherID.UID, entry.getKey(), signature, true,
                                System.currentTimeMillis() / 1000 + RedisUtils.DEFAULT_EXPIRE);
                    }
                    if ("maritalstat".equalsIgnoreCase(entry.getKey())) {
                        try {
                            maritalStat = Integer.parseInt(entry.getValue().toString());
                        } catch (NumberFormatException nfe) {
                            logger.warn(LogMsg.BASE_MSG, reqId, LogMessageFormatter.format(LogMsg.ERR_STRING2INTEGER, entry.getValue()));
                            throw new BaseRestException(ResponseInfoEnum.E10002, LogMessageFormatter.format(LogMsg.ERR_STRING2INTEGER,
                                    entry.getValue()));
                        }
                        userTagHandler.addUserTag(String.valueOf(user.getUid()), OtherID.UID, entry.getKey(), entry.getValue().toString(), true,
                                System.currentTimeMillis() / 1000 + RedisUtils.DEFAULT_EXPIRE);
                    }
                    if ("carrer".equalsIgnoreCase(entry.getKey())) {
                        try {
                            carrer = Integer.parseInt(entry.getValue().toString());
                        } catch (NumberFormatException nfe) {
                            logger.warn(LogMsg.BASE_MSG, reqId, LogMessageFormatter.format(LogMsg.ERR_STRING2INTEGER, entry.getValue()));
                            throw new BaseRestException(ResponseInfoEnum.E10002, LogMessageFormatter.format(LogMsg.ERR_STRING2INTEGER,
                                    entry.getValue()));
                        }
                        userTagHandler.addUserTag(String.valueOf(user.getUid()), OtherID.UID, entry.getKey(), entry.getValue().toString(), true,
                                System.currentTimeMillis() / 1000 + RedisUtils.DEFAULT_EXPIRE);
                    }
                    if ("enrollmentyear".equalsIgnoreCase(entry.getKey())) {
                        try {
                            enrollmentYear = Integer.parseInt(entry.getValue().toString());
                        } catch (NumberFormatException nfe) {
                            logger.warn(LogMsg.BASE_MSG, reqId, LogMessageFormatter.format(LogMsg.ERR_STRING2INTEGER, entry.getValue()));
                            throw new BaseRestException(ResponseInfoEnum.E10002, LogMessageFormatter.format(LogMsg.ERR_STRING2INTEGER,
                                    entry.getValue()));
                        }
                        userTagHandler.addUserTag(String.valueOf(user.getUid()), OtherID.UID, entry.getKey(), entry.getValue().toString(), true,
                                System.currentTimeMillis() / 1000 + RedisUtils.DEFAULT_EXPIRE);
                    }
                    if ("highestedu".equalsIgnoreCase(entry.getKey())) {
                        try {
                            highestEdu = Integer.parseInt(entry.getValue().toString());
                        } catch (NumberFormatException nfe) {
                            logger.warn(LogMsg.BASE_MSG, reqId, LogMessageFormatter.format(LogMsg.ERR_STRING2INTEGER, entry.getValue()));
                            throw new BaseRestException(ResponseInfoEnum.E10002, LogMessageFormatter.format(LogMsg.ERR_STRING2INTEGER,
                                    entry.getValue()));
                        }
                        userTagHandler.addUserTag(String.valueOf(user.getUid()), OtherID.UID, entry.getKey(), entry.getValue().toString(), true,
                                System.currentTimeMillis() / 1000 + RedisUtils.DEFAULT_EXPIRE);
                    }
                    if ("school".equalsIgnoreCase(entry.getKey())) {
                        school = EmojiUtils.replaceEmoji((String) entry.getValue());
                        userTagHandler.addUserTag(String.valueOf(user.getUid()), OtherID.UID, entry.getKey(), school, true,
                                System.currentTimeMillis() / 1000 + RedisUtils.DEFAULT_EXPIRE);
                    }
                    if ("watchcpnum".equalsIgnoreCase(entry.getKey())) {
                        try {
                            watchCPNum = Integer.parseInt(entry.getValue().toString());
                        } catch (NumberFormatException nfe) {
                            logger.warn(LogMsg.BASE_MSG, reqId, LogMessageFormatter.format(LogMsg.ERR_STRING2INTEGER, entry.getValue()));
                            throw new BaseRestException(ResponseInfoEnum.E10002, LogMessageFormatter.format(LogMsg.ERR_STRING2INTEGER,
                                    entry.getValue()));
                        }
                        userTagHandler.addUserTag(String.valueOf(user.getUid()), OtherID.UID, entry.getKey(), entry.getValue().toString(), true,
                                System.currentTimeMillis() / 1000 + RedisUtils.DEFAULT_EXPIRE);
                    }
                }
                usersHandler.update(user);
                // 更新weiying_前缀的openid信息
                boolean isHaveNickName = !StringUtil.isEmpty(user.getNickName());
                boolean isHavePhoto = !StringUtil.isEmpty(user.getPhoto());
                if (isHaveNickName || isHavePhoto) {
                    String openid = Constants.PREFIX_UID2OPENID_CONVERSION + user.getUid();
                    OpenIdInfo openIdInfo = openIdInfoHandler.getOpenIdInfoByOpenId(openid);
                    if (null != openIdInfo) {
                        if (isHaveNickName) {
                            openIdInfo.setNickName(user.getNickName());
                        }
                        if (isHavePhoto) {
                            openIdInfo.setPhoto(user.getPhoto());
                        }
                        openIdInfo.setLastLoginTime(new Date());
                        openIdInfoHandler.update(openIdInfo);
                    }
                }
                res = new UserInfoUpdateRes(uid, usersHandler.getExtUidByUid(user.getUid()), user.getMobileNo(), user.getNickName(), user.getSex()
                        .getIntVal(), user.getArea(), user.getBirthday(), user.getEmail(), user.getPhoto(), user.getUserName(), user.getUserKey(),
                        signature, maritalStat, carrer, enrollmentYear, highestEdu, school, watchCPNum);
            }
        } catch (BaseRestException be) {
            throw be;
        } catch (DataAccessException me) {
            logger.error(LogMsg.BASE_MSG, reqId, me.getMessage());
            throw new BaseRestException(reqId, ResponseInfoEnum.E50001);
        }
        return res;
    }

    /**
     * 第三方用户绑定手机号
     * 
     * @param req
     * @return
     * @throws BaseRestException
     *
     */
    @Override
    public BindMobileNoRes bindMobileNo(BindMobileNoReq req) throws BaseRestException {
        BindMobileNoRes res = null;
        try {
            String openId = req.getOpenId();
            OtherID otherId = OtherID.parseInt(req.getOtherId());
            String unionId = req.getUnionId();
            String mobileNo = req.getMobileNo();

            // 检查openid绑定次数是否达到限制(若是微信则检查unionid)
            usersOperationLimitHandler.checkOpenIdBindCount(req);

            Date now = new Date();
            // 为记录操作次数用
            Integer memberId = null;

            // 首先取出第三方用户基本信息，备用
            OpenIdInfo openIdInfo = openIdInfoHandler.getOpenIdInfoByOpenId(openId);
            // 与UnionId的level比较的用意是为了验证仅仅在openid确实为一个真正的openid时刻才去把OpenIdInfo空值异常抛出
            // 有时openid字段可能传入uid或者unionId
            if (null == openIdInfo && otherId.compare(OtherID.UnionID) < 0) {
                logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMessageFormatter.format(LogMsg.OPEN_ID_NOT_FOUND_DETAIL, openId));
                throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10005);
            }

            // 取手机号-Uid映射关系
            int uidFromMobileNoMapping = mobileNum2UIDHandler.getUIDByMobileNo(req.getMobileNo());
            boolean isMobileNoMappingExisted = true;
            if (Constants.NOT_EXISTED_UID == uidFromMobileNoMapping) {
                isMobileNoMappingExisted = false;
            } else {
                memberId = uidFromMobileNoMapping;
            }

            // 取OpenId-Uid映射关系(取当前关系)
            int uidFromOpenIdMapping = Constants.NOT_EXISTED_UID;
            IdRelationNode rootRelationNode = idRelationHandler.getRootNode(new IdRelationNode(openId, otherId));
            boolean isOpenIdMappingExisted = false;
            if (rootRelationNode.getIdType() == OtherID.UID) {
                uidFromOpenIdMapping = Integer.parseInt(rootRelationNode.getId());
                isOpenIdMappingExisted = true;
                memberId = uidFromOpenIdMapping;
            }

            // 业务分支 1: 手机号-Uid 和 OpenId-Uid的映射关系都存在
            if (isMobileNoMappingExisted && isOpenIdMappingExisted) {
                // 两个映射关系表中取出的uid不一致，手机号已经被其他用户绑定; 一致，则自己已经绑定过
                if (uidFromMobileNoMapping != uidFromOpenIdMapping) {
                    logger.warn(LogMsg.BASE_MSG, req.getRequestId(),
                            LogMessageFormatter.format(LogMsg.OCCUPIED_MOBILE_BINDING_DETAIL, mobileNo, uidFromMobileNoMapping));
                    throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E20004, LogMsg.OCCUPIED_MOBILE_BINDING);
                } else {
                    logger.warn(LogMsg.BASE_MSG, req.getRequestId(),
                            LogMessageFormatter.format(LogMsg.DUPLICATED_MOBILE_BINDING, mobileNo, uidFromMobileNoMapping));
                    throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E20003, LogMsg.DUPLICATED_MOBILE_BINDING);
                }
            }
            // 业务分支 2: 手机号-Uid 映射关系存在， OpenId-Uid的映射关系不存在
            else if (isMobileNoMappingExisted && !isOpenIdMappingExisted) {
                // 检查memberid时段内绑定次数是否达到限制
                usersOperationLimitHandler.checkMemberIdBindCount(uidFromMobileNoMapping, req.getRequestId());
                // 检查是否存在同一接入平台（QQ 微信 手机 微博）的账号出现绑定数>1的情况
                if (checkIdRelationForMultiPlatform(openId, otherId, unionId, uidFromMobileNoMapping)) {
                    // 同步OpenIdInfo的昵称和头像到用户表
                    Users user = usersHandler.queryOneByUid(uidFromMobileNoMapping);
                    mergeOpenIdInfo2User(user, openIdInfo);
                    usersHandler.update(user);

                    // 插入openId、unionId和UID的映射关系
                    handleIdRelationInsert(openId, otherId, unionId, uidFromMobileNoMapping, now);

                    // 产生pseudo openid
                    createPseudoOpenIdForMobileUser(uidFromMobileNoMapping, user);

                    res = new BindMobileNoRes(uidFromMobileNoMapping, usersHandler.getExtUidByUid(uidFromMobileNoMapping));
                } else {
                    logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMessageFormatter.format(LogMsg.DUPLICATED_PLATFORM_BINDING_DETAIL, mobileNo));
                    throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E20009, otherId.toString());
                }
            }
            // 业务分支 3: 手机号-Uid 映射关系不存在，
            // OpenId-Uid的映射关系存在(手机号更新)<注：此分支在实际应用中被接口updateMobileNo替代>
            else if (!isMobileNoMappingExisted && isOpenIdMappingExisted) {
                // 检查memberid时段内绑定次数是否达到限制
                usersOperationLimitHandler.checkMemberIdBindCount(uidFromOpenIdMapping, req.getRequestId());
                // 查询user, 原则上，OpenId-Uid的映射关系存在，则用户表里必然有一条uid对应的记录,
                Users user = usersHandler.queryOneByUid(uidFromOpenIdMapping);
                if (null == user) {
                    logger.warn(LogMsg.BASE_MSG, req.getRequestId(),
                            LogMessageFormatter.format(LogMsg.MEMBER_ID_NOT_FOUND_DETAIL, uidFromOpenIdMapping));
                    throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E20006);
                }
                String oldMobileNo = user.getMobileNo();

                if (mobileNo.equals(oldMobileNo)) {
                    // /-
                    // 此分支应不存在，因为oldMobileNo如果等于mobileNo，则系统中必然存在该手机号与Uid的映射关系
                    logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMessageFormatter.format(LogMsg.MOBILE2UID_NOT_FOUND, mobileNo));
                    throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E20008, mobileNo);
                } else {
                    // 更新手机号，并同步OpenIdInfo的昵称和头像到用户表
                    user.setMobileNo(mobileNo);
                    mergeOpenIdInfo2User(user, openIdInfo);
                    usersHandler.update(user);

                    // 首先需要删除Uid下面既有的可能存在的旧手机号-Uid的映射关系
                    mobileNum2UIDHandler.deleteMobileNo2UIDMapping(oldMobileNo);

                    // 插入一条新手机号-Uid的映射关系
                    mobileNum2UIDHandler.insertMobileNo2UIDMapping(new MobileNoMapping(null, mobileNo, uidFromOpenIdMapping));

                    res = new BindMobileNoRes(uidFromOpenIdMapping, usersHandler.getExtUidByUid(uidFromOpenIdMapping));
                }
            }
            // 业务分支 4: 手机号-Uid 映射关系和OpenId-Uid的映射关系均不存在
            else if (!isMobileNoMappingExisted && !isOpenIdMappingExisted) {

                // 1.生成全局uid
                Integer globalId = RedisUtils4UidGenerate.genUid();
                // 判断是否获取到了uid,没有则说明redis异常
                if (null == globalId) {
                    logger.error(LogMsg.BASE_MSG, req.getRequestId(), LogMsg.ERR_GEN_MEMBER_ID);
                    throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E50002);
                }

                Gender sex = Gender.UNKNOWN; // 用户性别，未知
                Status status = Status.NORMAL;

                // 同步OpenIdInfo的昵称和头像到用户,并在用户表中插入该用户
                Users newUser = new Users(globalId, mobileNo, null, null, null, status, null, sex, null, null, null, null, now, now, null);
                mergeOpenIdInfo2User(newUser, openIdInfo);
                usersHandler.insert(newUser);

                // 插入一条手机号-Uid的映射关系
                mobileNum2UIDHandler.insertMobileNo2UIDMapping(new MobileNoMapping(null, mobileNo, globalId));

                // 插入openId、unionId和UID的映射关系
                handleIdRelationInsert(openId, otherId, unionId, globalId, now);

                // 产生pseudo openid
                createPseudoOpenIdForMobileUser(globalId, newUser);

                memberId = globalId;
                res = new BindMobileNoRes(globalId, String.valueOf(globalId));
            } else {
                // /- 条件已遍历，不可能进入此分支
                logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMsg.HTTP_METHOD_FORBIDDEN);
                throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10001);
            }

            // 记录用户绑定成功操作
            usersOperationLimitHandler.recordUserBindOperate(req, memberId);
            // 发送绑定消息到消息队列做其他业务处理
            UserMsgInfo userMsgInfo = new UserMsgInfo(openId, otherId, unionId, Long.valueOf(res.get_memberId()).intValue(), mobileNo);
            msgProducer.sendMsg(new MsgInfo<UserMsgInfo>(MsgMqConstants.MSG_EVENT_BIND, userMsgInfo));
        } catch (IllegalArgumentException ie) {
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10002, ie.getMessage());
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

    /**
     * 根据uid或extUid更改用户手机号
     * 
     * @param req
     * @return
     * @throws BaseRestException
     *
     */
    @Override
    public SingleResultRes updateMobileNo(UpdateMobileNoReq req) throws BaseRestException {
        SingleResultRes res = null;
        try {
            String oldMobileNo = req.getOldMobileNo();
            String newMobileNo = req.getNewMobileNo();
            Object unknownUidObj = req.getMemberId();
            String unknownUid = String.valueOf(unknownUidObj);
            Integer uid = usersHandler.getUid(unknownUid);
            if (null == uid) {
                logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMessageFormatter.format(LogMsg.MEMBER_ID_NOT_FOUND_DETAIL, unknownUid));
                throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10005);
            }

            // 检查是否超过时段修改次数的限制
            usersOperationLimitHandler.checkUpdateMobileCount(uid.toString(), req.getRequestId());

            Users user = usersHandler.queryOneByUid(uid);
            if (null == user) {
                logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMessageFormatter.format(LogMsg.MEMBER_ID_NOT_FOUND_DETAIL, uid));
                throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10005);
            } else {
                // 对比DB，查看旧手机号是否确实是被该用户目前使用, 不一致则抛出异常
                if (!oldMobileNo.equals(user.getMobileNo())) {
                    logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMessageFormatter.format(LogMsg.ERR_OLD_MOBILE_DETAIL, oldMobileNo));
                    throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E20010, LogMsg.ERR_OLD_MOBILE);
                }
                int newMobileNum2Uid = mobileNum2UIDHandler.getUIDByMobileNo(newMobileNo);
                // 查看新手机号是否已经被其他用户占用，占用则抛出异常
                if (Constants.NOT_EXISTED_UID != newMobileNum2Uid) {
                    logger.warn(LogMsg.BASE_MSG, req.getRequestId(),
                            LogMessageFormatter.format(LogMsg.OCCUPIED_MOBILE_BINDING_DETAIL, newMobileNo, newMobileNum2Uid));
                    throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E20004, LogMsg.OCCUPIED_MOBILE_BINDING);
                }

                // 更新手机号，并同步OpenIdInfo的昵称和头像到用户表
                user.setMobileNo(newMobileNo);
                usersHandler.update(user);

                // 首先需要删除Uid下面既有的可能存在的旧手机号-Uid的映射关系
                mobileNum2UIDHandler.deleteMobileNo2UIDMapping(oldMobileNo);

                // 插入一条新手机号-Uid的映射关系
                mobileNum2UIDHandler.insertMobileNo2UIDMapping(new MobileNoMapping(null, newMobileNo, uid));

                res = new SingleResultRes(0);

                // 记录用户修改手机号成功
                String redisKey = String.format(RedisKey.USER_UPDATE_MOBILE_NO_LIMITED_KEY, uid.toString());
                usersOperationLimitHandler.recordUserLimitOperate(redisKey, DateUtils.getCurrentTimestamp());

                MobileUpdateMsgInfo mobileUpdateMsgInfo = new MobileUpdateMsgInfo(uid, oldMobileNo, newMobileNo);
                msgProducer.sendMsg(new MsgInfo<MobileUpdateMsgInfo>(MsgMqConstants.MSG_EVENT_MOBILE_UPDATE, mobileUpdateMsgInfo));
            }
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

    @Override
    public SingleResultRes unbindOpenID(UnBindOpenIDReq req) throws BaseRestException {
        try {
            String openId = req.getOpenID();
            int otherId = req.getOtherID();

            // 首先取出第三方用户基本信息，备用
            OpenIdInfo openIdInfo = openIdInfoHandler.getOpenIdInfoByOpenId(openId);
            if (null == openIdInfo) {
                logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMessageFormatter.format(LogMsg.OPEN_ID_NOT_FOUND_DETAIL, openId));
                throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10005);
            } else {
                if (BindingStatus.UNBOUND == openIdInfo.getBindingStatus()) {
                    logger.warn(LogMsg.BASE_MSG, req.getRequestId(),
                            LogMessageFormatter.format(LogMsg.OPEN_ID_ALREADY_UNBOUND_DETAIL, openId, otherId));
                    throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10008, LogMsg.OPEN_ID_ALREADY_UNBOUND);
                }
            }

            // 取OpenId-Uid映射关系并删除
            // 先从redis缓存中取值，如果万一redis key失效后从db中取值，两处的记录均删除
            // int uidFromOpenIdMapping =
            // RedisMapper.isOpenID2UIDExisted(openId,
            // OtherID.parseInt(otherId));
            // OpenIdMapping openIdMapping = null;
            boolean isOpenIdMappingExisted = true;
            // if (Constants.NOT_EXISTED_UID == uidFromOpenIdMapping){
            // openIdMapping = openIdMappingMapper.queryOneByOpenId(openId,
            // OtherID.parseInt(otherId));
            // if (null == openIdMapping) {
            // isOpenIdMappingExisted = false;
            // } else {
            // openIdMappingMapper.delete(openId, OtherID.parseInt(otherId));
            // }
            // } else {
            // RedisMapper.removeOpenID2UID(openId, OtherID.parseInt(otherId));
            // }
            // 删除OpenID2UID表中记录并设置为已绑定
            if (!isOpenIdMappingExisted) {
                logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMsg.OPENID2UID_NOT_FOUND);
                throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E20006);
            } else {
                openIdInfoHandler.updateBindingStatus(openId, OtherID.parseInt(otherId), BindingStatus.UNBOUND);
            }
        } catch (IllegalArgumentException ie) {
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10002, ie.getMessage());
        } catch (BaseRestException be) {
            throw be;
        } catch (JedisConnectionException je) {
            logger.error(LogMsg.BASE_MSG, req.getRequestId(), je.getMessage());
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E50002);
        } catch (DataAccessException me) {
            logger.error(LogMsg.BASE_MSG, req.getRequestId(), me.getMessage());
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E50001);
        }
        return null;
    }

    /**
     * 根据uid或extUid修改用户密码
     * 
     * @param req
     * @return
     * @throws BaseRestException
     *
     */
    @Override
    public SingleResultRes changePassword(ChangePasswordReq req) throws BaseRestException {
        SingleResultRes res = null;
        try {
            res = changeUidPassword(req);
        } catch (BaseRestException be) {
            throw be;
        } catch (DataAccessException me) {
            logger.error(LogMsg.BASE_MSG, req.getRequestId(), me.getMessage());
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E50001);
        }
        return res;
    }

    // /**
    // * 遍历uid名下所有的OpenId，一旦有一个对应的OpenID为老用户，
    // * 则把所有对应的OpenID都插入老用户缓存, 注意老用户同步区分不同产品线
    // * @param uid
    // * @throws BaseRestException
    // */
    // private void handleOldUserStore(int uid) throws BaseRestException {
    // boolean isMovieOldUser = false;
    // boolean isShowOldUser = false;
    // List<OpenIdMapping> openIdMappingList =
    // openIdMappingMapper.queryByUid(uid);
    // if (openIdMappingList != null) {
    // for (OpenIdMapping oidMapping : openIdMappingList) {
    // //#// Hack here, 仅仅传入值为20的OtherId才有真实意义，其余的一律映射为0，此操作为了方便旧数据的线上迁移。
    // OtherID hackOtherId = (oidMapping.getOtherID() == OtherID.UID) ?
    // OtherID.UID : OtherID.LEGACY;
    // if(RedisMapper.isUserHaveTag(oidMapping.getOpenID(), hackOtherId,
    // UserTagEnum.MOVIE_OLD_USR.getIntVal())) {
    // isMovieOldUser = true;
    // }
    // if(RedisMapper.isUserHaveTag(oidMapping.getOpenID(), hackOtherId,
    // UserTagEnum.SHOW_OLD_USR.getIntVal())) {
    // isShowOldUser = true;
    // }
    // }
    // // 时间戳为秒
    // if(isMovieOldUser) {
    // for (OpenIdMapping oidMapping : openIdMappingList) {
    // //#// Hack here, 仅仅传入值为20的OtherId才有真实意义，其余的一律映射为0，此操作为了方便旧数据的线上迁移。
    // OtherID hackOtherId = (oidMapping.getOtherID() == OtherID.UID) ?
    // OtherID.UID : OtherID.LEGACY;
    // RedisMapper.setUserTag(oidMapping.getOpenID(), hackOtherId,
    // UserTagEnum.MOVIE_OLD_USR.getIntVal(), System.currentTimeMillis()/1000);
    // }
    // }
    // if(isShowOldUser) {
    // for (OpenIdMapping oidMapping : openIdMappingList) {
    // //#// Hack here, 仅仅传入值为20的OtherId才有真实意义，其余的一律映射为0，此操作为了方便旧数据的线上迁移。
    // OtherID hackOtherId = (oidMapping.getOtherID() == OtherID.UID) ?
    // OtherID.UID : OtherID.LEGACY;
    // RedisMapper.setUserTag(oidMapping.getOpenID(), hackOtherId,
    // UserTagEnum.SHOW_OLD_USR.getIntVal(), System.currentTimeMillis()/1000);
    // }
    // }
    // }
    // }
    //
    // /**
    // * 遍历uid名下所有的OpenIdInfo，将其绑定状态均变为‘已绑定’
    // * @param uid
    // * @throws BaseRestException
    // */
    // private void updateAllOfOpenIdBinding(int uid) throws BaseRestException {
    // List<OpenIdMapping> openIdMappingList =
    // openIdMappingMapper.queryByUid(uid);
    // if (openIdMappingList != null) {
    // for (OpenIdMapping oidMapping : openIdMappingList) {
    // updateBindingStatus(oidMapping.getOpenID(), oidMapping.getOtherID(),
    // BindingStatus.BOUND);
    // }
    // }
    // }
    //
    // /**
    // * 更新第三方用户的手机号绑定状态
    // */
    // private void updateBindingStatus(String openId, OtherID otherId,
    // BindingStatus bindingStatus) {
    // openIdInfoMapper.updateBindingStatus(openId, otherId, bindingStatus);
    // }

    /**
     * 将第三方用户的某些信息同步到User
     */
    private void mergeOpenIdInfo2User(Users user, OpenIdInfo thirdPartyUser) {
        if (null != user && null != thirdPartyUser) {
            if (null == user.getNickName() || 0 == user.getNickName().length()) {
                user.setNickName(thirdPartyUser.getNickName());
            }
            if (null == user.getPhoto() || 0 == user.getPhoto().length()) {
                user.setPhoto(thirdPartyUser.getPhoto());
            }
        }
        // 合并用户状态，取用户和第三方用户状态的最大值
        // int statusOfUser = user.getStatus().getIntVal();
        // int statusOf3rdPartyUser = thirdPartyUser.getStatus().getIntVal();
        // int mergedStatus = ((statusOfUser > statusOf3rdPartyUser) ?
        // statusOfUser :
        // statusOf3rdPartyUser);
        // user.setStatus(Status.parseInt(mergedStatus));

        // 合并影院收藏
        // String cinemaOfUser = user.getCinemaFavorites();
        // String cinemaOf3rdPartyUser = thirdPartyUser.getCinemaFavorites();
        // String mergedCinema = null;
        // if (cinemaOfUser == null) {
        // mergedCinema = cinemaOf3rdPartyUser;
        // } else if (cinemaOf3rdPartyUser == null) {
        // mergedCinema = cinemaOfUser;
        // } else {
        // mergedCinema = "";
        // HashSet<String> mergedCinemaSet = new HashSet<String>();
        // String[] cinemaArrayOfUser = cinemaOfUser.split(",");
        // String[] cinemaArrayOf3rdPartyUser = cinemaOf3rdPartyUser.split(",");
        // CollectionUtils.addAll(mergedCinemaSet, cinemaArrayOfUser);
        // CollectionUtils.addAll(mergedCinemaSet, cinemaArrayOf3rdPartyUser);
        // Iterator<String> i = mergedCinemaSet.iterator();
        // while(i.hasNext()){
        // mergedCinema += i.next() + ",";
        // }
        // mergedCinema = mergedCinema.substring(0, mergedCinema.length() -1);
        // }
        // user.setCinemaFavorites(mergedCinema);
        // if (null == usersMapper.queryOneByUid(user.getUid())) {
        // usersMapper.insert(user);
        // } else {
        // usersMapper.update(user);
        // }
    }

    private SingleResultRes changeUidPassword(ChangePasswordReq req) throws BaseRestException {
        int result = 0;
        SingleResultRes res = null;
        try {
            boolean isUpdatePWLimit = usersOperationLimitHandler.isUpdatePWLimit(req.getMemberId());
            if (isUpdatePWLimit) {
                securityLogger.warn(LogMsg.BASE_MSG, req.getRequestId(),
                        LogMessageFormatter.format(LogMsg.MEMBER_UPDATE_PW_FAIL_MORE, req.getMemberId()));
                throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E20015, req.getMemberId());
            }
            Integer uid = usersHandler.getUid(req.getMemberId());
            if (null == uid) {
                logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMessageFormatter.format(LogMsg.MEMBER_ID_NOT_FOUND_DETAIL, req.getMemberId()));
                throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10005);
            }
            Users user = usersHandler.queryOneByUid(uid);
            if (null == user) {
                // 加上计数以防止恶意撞库
                usersOperationLimitHandler.addUpdatePWCount(req.getMemberId());
                logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMessageFormatter.format(LogMsg.MEMBER_ID_NOT_FOUND_DETAIL, req.getMemberId()));
                throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10005);
            } else {
                // 用于绑定手机之后初始化密码的操作
                if (Constants.INIT_PW_OP_TYPE == req.getOpType() && (null != user.getPassword() && user.getPassword().length() > 0)) {
                    usersOperationLimitHandler.addUpdatePWCount(req.getMemberId());
                    logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMsg.PASSWORD_NOT_NULL_IN_DB);
                    throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E20017, LogMsg.PASSWORD_NOT_NULL_IN_DB);
                }
                if (Constants.RESET_PW_OP_TYPE == req.getOpType() && !req.getMobileNo().equals(user.getMobileNo())) {
                    usersOperationLimitHandler.addUpdatePWCount(req.getMemberId());
                    logger.warn(LogMsg.BASE_MSG, req.getRequestId(),
                            LogMessageFormatter.format(LogMsg.USER_MOBILENO_NOT_THE_SAME_ONE, user.getMobileNo(), req.getMobileNo()));
                    throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E20014);
                }
                if (Constants.CHANGE_PW_OP_TYPE == req.getOpType() && !MD5Utils.validatePassword(req.getOldPassword(), user.getPassword())) {
                    usersOperationLimitHandler.addUpdatePWCount(req.getMemberId());
                    logger.warn(LogMsg.BASE_MSG, req.getRequestId(),
                            LogMessageFormatter.format(LogMsg.OLD_PASSWORD_VERIFY_FAILED_DETAIL, req.getOldPassword()));
                    throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E20012);
                }
                int updateResult = usersHandler.updatePwd(user.getUid(), MD5Utils.getEncryptedPwd(req.getNewPassword()));
                if (updateResult != 1) {
                    result = 1;
                }
                res = new SingleResultRes(result);
            }
        } catch (NoSuchAlgorithmException ne) {
            logger.error(LogMsg.BASE_MSG, req.getRequestId(), ne.getMessage());
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10002, ne.getMessage());
        } catch (UnsupportedEncodingException ue) {
            logger.error(LogMsg.BASE_MSG, req.getRequestId(), ue.getMessage());
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10002, ue.getMessage());
        } catch (BaseRestException be) {
            throw be;
        } catch (DataAccessException me) {
            logger.error(LogMsg.BASE_MSG, req.getRequestId(), me.getMessage());
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E50001);
        }
        return res;
    }

    /**
     * 根据unionId的存在与否插入关系
     * 
     * @param openId
     * @param otherId
     * @param unionId
     * @param uid
     * @param insertTime
     */
    private void handleIdRelationInsert(String openId, OtherID otherId, String unionId, int uid, Date insertTime) {
        if (null != unionId && 0 < unionId.length()) {
            idRelationService.insertIdRelation(openId, otherId, unionId, OtherID.UnionID, insertTime);
            idRelationService.insertIdRelation(unionId, OtherID.UnionID, String.valueOf(uid), OtherID.UID, insertTime);
        } else {
            idRelationService.insertIdRelation(openId, otherId, String.valueOf(uid), OtherID.UID, insertTime);
        }
    }

    /**
     * 绑定手机时刻，使用此方法产生一个伪造的的openId，其otherId标识为手机用户，
     * 该openId为了兼容旧系统以openId作为用户id基本标识的设计
     * 
     * @param uid
     */
    private void createPseudoOpenIdForMobileUser(int uid, Users users) {
        // 首先查询是否具有此伪造的openId，没有则创建一个
        List<IdRelationNode> openIdList = idRelationService.getIdListFromRootByType(new IdRelationNode(String.valueOf(uid), OtherID.UID),
                OtherID.MOBILE);
        if (null == openIdList || openIdList.isEmpty()) {
            OpenIdInfo pseudoOpenId = new OpenIdInfo(null, Constants.PREFIX_UID2OPENID_CONVERSION + uid, OtherID.MOBILE, users.getNickName(),
                    users.getPhoto(), null, null, BindingStatus.BOUND, null, Status.NORMAL);
            openIdInfoMapper.insert(pseudoOpenId);
            idRelationService.insertIdRelation(pseudoOpenId.getOpenId(), pseudoOpenId.getOtherId(), String.valueOf(uid), OtherID.UID, new Date());
        } else {
            // 更新伪造openid的昵称头像信息
            String openId = Constants.PREFIX_UID2OPENID_CONVERSION + uid;
            OpenIdInfo openIdInfo = openIdInfoHandler.getOpenIdInfoByOpenId(openId);
            if (null != openIdInfo) {
                openIdInfo.setNickName(users.getNickName());
                openIdInfo.setPhoto(users.getPhoto());
                // 更新
                openIdInfoHandler.update(openIdInfo);
            }
        }
    }

    /**
     * 检查是否存在同一接入平台（QQ 微信 手机 微博）的账号出现绑定数>1的情况
     * 
     * @param openId
     * @param otherId
     * @param unionId
     * @param uid
     * @return 如果有某平台已经绑定了id，则返回false，否则返回true
     */
    private boolean checkIdRelationForMultiPlatform(String openId, OtherID otherId, String unionId, int uid) {
        // 假定可以绑定
        boolean result = true;
        IdRelationNode uidNode = new IdRelationNode(String.valueOf(uid), OtherID.UID);
        List<IdRelationNode> nonUidList = idRelationService.getIdListFromRoot(uidNode);
        if (null != nonUidList && 0 != nonUidList.size()) {
            for (IdRelationNode id : nonUidList) {
                if (null != unionId && 0 < unionId.length()) {
                    // 绑定另一个不同的uionId，拒绝
                    if (OtherID.UnionID == id.getIdType() && !unionId.equals(id.getId())) {
                        result = false;
                        break;
                    }
                } else {
                    // 非微信，同一平台绑定数>1,拒绝
                    if (otherId == id.getIdType()) {
                        result = false;
                        break;
                    }
                }
            }
        }
        return result;
    }

}
