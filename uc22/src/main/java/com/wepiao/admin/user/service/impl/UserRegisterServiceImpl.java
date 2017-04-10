package com.wepiao.admin.user.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import redis.clients.jedis.exceptions.JedisConnectionException;

import com.wepiao.admin.user.rest.msg.DeviceInfo;
import com.wepiao.admin.user.rest.msg.MobileRegisterReq;
import com.wepiao.admin.user.rest.msg.MobileRegisterRes;
import com.wepiao.admin.user.rest.msg.OpenIdRegisterReq;
import com.wepiao.admin.user.rest.msg.OpenIdRegisterRes;
import com.wepiao.admin.user.service.UserRegisterService;
import com.wepiao.admin.user.service.handler.OpenIdInfoHandler;
import com.wepiao.admin.user.service.handler.UserDeviceHandler;
import com.wepiao.user.common.constant.Constants;
import com.wepiao.user.common.constant.LogMsg;
import com.wepiao.user.common.constant.MsgMqConstants;
import com.wepiao.user.common.dao.OpenIdInfoMapper;
import com.wepiao.user.common.entry.MobileNoMapping;
import com.wepiao.user.common.entry.OpenIdInfo;
import com.wepiao.user.common.entry.Users;
import com.wepiao.user.common.entry.enumeration.BindingStatus;
import com.wepiao.user.common.entry.enumeration.Gender;
import com.wepiao.user.common.entry.enumeration.OtherID;
import com.wepiao.user.common.entry.enumeration.Status;
import com.wepiao.user.common.enumeration.ResponseInfoEnum;
import com.wepiao.user.common.handler.MobileNum2UIDHandler;
import com.wepiao.user.common.handler.UsersHandler;
import com.wepiao.user.common.mq.MsgProducer;
import com.wepiao.user.common.mq.entity.MsgInfo;
import com.wepiao.user.common.mq.entity.UserMsgInfo;
import com.wepiao.user.common.redis.RedisUtils4UidGenerate;
import com.wepiao.user.common.rest.exception.BaseRestException;
import com.wepiao.user.common.service.IdRelationService;
import com.wepiao.user.common.util.EmojiUtils;
import com.wepiao.user.common.util.LogMessageFormatter;
import com.wepiao.user.common.util.MD5Utils;
import com.wepiao.user.common.util.StringUtil;

@Service
public class UserRegisterServiceImpl implements UserRegisterService {

    private static final Logger  logger = LoggerFactory.getLogger(UserRegisterServiceImpl.class);

    @Autowired
    private UsersHandler         usersHandler;

    @Autowired
    private OpenIdInfoMapper     openIdInfoMapper;

    @Autowired
    private OpenIdInfoHandler    openIdInfoHandler;

    @Autowired
    private MobileNum2UIDHandler mobileNum2UIDHandler;

    @Autowired
    private IdRelationService    idRelationService;

    @Autowired
    private UserDeviceHandler    userDeviceHandler;

    @Autowired
    private MsgProducer          msgProducer;

    /**
    * 通过手机号注册用户,手机号已存在抛出错误代码，不存在注册新用户
    * @param req
    * @param deviceinfoList
    * @throws BaseRestException
    */
    @Override
    public MobileRegisterRes registerMobileUser(MobileRegisterReq req, List<DeviceInfo> deviceinfoList) throws BaseRestException {
        MobileRegisterRes res = null;
        try {
            String mobileNo = req.getMobileNo();
            if (Constants.NOT_EXISTED_UID != mobileNum2UIDHandler.getUIDByMobileNo(mobileNo)) {
                // 手机号已存在
                logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMessageFormatter.format(LogMsg.DUPLICATED_MOBILE_REGISTRY_DETAIL, mobileNo));
                throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E20002, LogMsg.DUPLICATED_MOBILE_REGISTRY);
            } else {
                // 1.生成全局uid
                Integer globalId = RedisUtils4UidGenerate.genUid();
                //判断是否获取到了uid,没有则说明redis异常
                if (null == globalId) {
                    logger.error(LogMsg.BASE_MSG, req.getRequestId(), LogMsg.ERR_GEN_MEMBER_ID);
                    throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E50002);
                }
                String password = req.getPassword();
                String photoUrl = req.getPhotoUrl();
                String nickName = EmojiUtils.replaceEmoji(req.getNickname());
                Status status = Status.NORMAL; // 用户状态正常
                BindingStatus bindingStatus = BindingStatus.BOUND; // 手机号已绑定
                Gender sex = Gender.UNKNOWN; // 用户性别，未知
                Date now = new Date();

                // 2.User表中插入用户记录
                Users user = new Users(globalId, mobileNo, MD5Utils.getEncryptedPwd(password), nickName, photoUrl, status, null, sex, null, null,
                        null, null, now, now, null);
                usersHandler.insert(user);

                // 3.OpenIdInfo表中插入用户记录
                String openId = Constants.PREFIX_UID2OPENID_CONVERSION + globalId;
                OtherID otherId = OtherID.MOBILE; // 手机用户
                OpenIdInfo openIdInfo = new OpenIdInfo(null, openId, otherId, nickName, photoUrl, now, null, bindingStatus, null, status);
                openIdInfoMapper.insert(openIdInfo);

                // 4.插入手机号和Uid映射关系
                MobileNoMapping mobileNoMapping = new MobileNoMapping(null, mobileNo, globalId);
                mobileNum2UIDHandler.insertMobileNo2UIDMapping(mobileNoMapping);

                // 5.用户关系表和历史关系表插入
                idRelationService.insertIdRelation(openId, otherId, String.valueOf(globalId), OtherID.UID, now);

                // 6.记录用户的deviceid
                userDeviceHandler.addUserDevice(String.valueOf(globalId), OtherID.UID, deviceinfoList);

                res = new MobileRegisterRes(globalId, openId, otherId.getIntVal());

                // 7.发送手机号注册事件通知到消息队列
                UserMsgInfo userMsgInfo = new UserMsgInfo(openId, otherId, null, globalId, mobileNo);
                msgProducer.sendMsg(new MsgInfo<UserMsgInfo>(MsgMqConstants.MSG_EVENT_MOBILE_REGIST, userMsgInfo));
            }
        } catch (IllegalArgumentException ie) {
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10002, ie.getMessage());
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

    /**
    * 通过第三方（微博、微信、手Q）用户的openId注册用户
    * @param req
    * @param deviceinfoList
    * @throws BaseRestException
    */
    @Override
    public OpenIdRegisterRes registerThirdPartyUser(OpenIdRegisterReq req, List<DeviceInfo> deviceinfoList) throws BaseRestException {
        OpenIdRegisterRes res = null;
        try {
            String openId = req.getOpenID();
            OtherID otherId = OtherID.parseInt(req.getOtherID());
            String unionId = req.getUnionId();
            String photo = req.getPhoto();
            String nickName = EmojiUtils.replaceEmoji(req.getNickName());
            Date now = new Date();
            OpenIdInfo openIdInfo = openIdInfoHandler.getOpenIdInfoByOpenId(req.getOpenID());
            if (null == openIdInfo) {
                Status status = Status.NORMAL; //用户状态正常
                BindingStatus bindingStatus = BindingStatus.UNBOUND; // 手机号未绑定

                // 用户未注册，第三方新用户插入基本信息表
                openIdInfo = new OpenIdInfo(null, openId, otherId, nickName, photo, now, null, bindingStatus, now, status);
                openIdInfoHandler.insert(openIdInfo);
            } else {
                // 用户已注册，则更新第三方用户的Photo和NickName
                openIdInfo.setLastLoginTime(now);
                if (!StringUtil.isEmpty(nickName)) {
                    openIdInfo.setNickName(nickName);
                }
                if (!StringUtil.isEmpty(photo)) {
                    openIdInfo.setPhoto(photo);
                }
                openIdInfoHandler.update(openIdInfo);
            }

            /**
             * 此处不应该做下面操作，只有绑定的时候才能做。
             */
            //			// 获取OpenId的根节点，如果根节点是UID，则什么都不做，否则要生成一个新的User并建立关联关系
            //			IdRelationNode rootNode = idrelationHandler.getRootNode(new IdRelationNode(openId, otherId));
            //			if (null == rootNode || OtherID.UID != rootNode.getIdType()) {
            //				int globalId = idGenMapper.idGen(new GlobalUid("a"));
            //				Status status = Status.NORMAL; // 用户状态正常
            //				Gender sex = Gender.UNKNOWN; // 用户性别，未知
            //
            //				// User表中插入用户记录
            //				Users user = new Users(globalId, null, null, nickName, photo,
            //						status, null, sex, null, null, null, null, now, now, null);
            //				usersMapper.insert(user);
            //
            //				// 插入openid和新生成的UID的关联关系
            //				idRelationService.insertIdRelation(openId, otherId, String.valueOf(globalId), OtherID.UID, now);
            //			}

            // 如果是携带有unionId(主要针对微信), 则更新关系(方法实现里已做非空检查)
            idRelationService.insertIdRelation(openId, otherId, unionId, OtherID.UnionID, now);

            // 记录第三方用户的deviceid
            userDeviceHandler.addUserDevice(openId, otherId, deviceinfoList);

            res = new OpenIdRegisterRes(openIdInfo.getOpenId(), openIdInfo.getOtherId().getIntVal(), openIdInfo.getStatus().getIntVal(),
                    openIdInfo.getNickName(), openIdInfo.getPhoto());

            //当是微信时判断是否有unionId,没有则不需要发送
            if (OtherID.WEIXIN.getIntVal() == otherId.getIntVal()) {
                if (!StringUtil.isEmptyCheckTrim(unionId)) {
                    //发送绑定消息到消息队列做其他业务处理
                    UserMsgInfo userMsgInfo = new UserMsgInfo(openIdInfo.getOpenId(), otherId, unionId, null, null);
                    msgProducer.sendMsg(new MsgInfo<UserMsgInfo>(MsgMqConstants.MSG_EVENT_OPENID_REGIST, userMsgInfo));
                }
            } else {
                UserMsgInfo userMsgInfo = new UserMsgInfo(openIdInfo.getOpenId(), otherId, unionId, null, null);
                msgProducer.sendMsg(new MsgInfo<UserMsgInfo>(MsgMqConstants.MSG_EVENT_OPENID_REGIST, userMsgInfo));
            }
        } catch (IllegalArgumentException ie) {
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10002, ie.getMessage());
        } catch (BaseRestException be) {
            throw be;
        } catch (DataAccessException me) {
            logger.error(LogMsg.BASE_MSG, req.getRequestId(), me.getMessage());
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E50001);
        }
        return res;
    }
}
