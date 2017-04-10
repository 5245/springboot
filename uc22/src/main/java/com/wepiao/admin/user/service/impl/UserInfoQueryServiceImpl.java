package com.wepiao.admin.user.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import redis.clients.jedis.exceptions.JedisConnectionException;

import com.wepiao.admin.user.rest.msg.IdRelationGetReq;
import com.wepiao.admin.user.rest.msg.IdRelationGetRes;
import com.wepiao.admin.user.rest.msg.OpenIdInfoGetReq;
import com.wepiao.admin.user.rest.msg.OpenIdInfoGetRes;
import com.wepiao.admin.user.rest.msg.OpenIdListGetByMobileReq;
import com.wepiao.admin.user.rest.msg.OpenIdListGetByMobileRes;
import com.wepiao.admin.user.rest.msg.UserFirstRegistTimeRes;
import com.wepiao.admin.user.rest.msg.UserInfoGetByMobileNoReq;
import com.wepiao.admin.user.rest.msg.UserInfoGetByOpenIdReq;
import com.wepiao.admin.user.rest.msg.UserInfoGetByOpenIdRes;
import com.wepiao.admin.user.rest.msg.UserInfoGetByUIDReq;
import com.wepiao.admin.user.rest.msg.UserInfoGetRes;
import com.wepiao.admin.user.rest.msg.UserInfoUploadPhotoReq;
import com.wepiao.admin.user.rest.msg.UserInfoUploadPhotoRes;
import com.wepiao.admin.user.rest.msg.UserProfileGetByUIDReq;
import com.wepiao.admin.user.rest.msg.UserProfileGetRes;
import com.wepiao.admin.user.service.UserInfoQueryService;
import com.wepiao.admin.user.service.handler.OpenIdInfoHandler;
import com.wepiao.admin.user.service.handler.UserTagHandler;
import com.wepiao.user.common.constant.Constants;
import com.wepiao.user.common.constant.LogMsg;
import com.wepiao.user.common.entry.IdRelationHistory;
import com.wepiao.user.common.entry.IdRelationNode;
import com.wepiao.user.common.entry.OpenId;
import com.wepiao.user.common.entry.OpenIdInfo;
import com.wepiao.user.common.entry.Users;
import com.wepiao.user.common.entry.enumeration.Gender;
import com.wepiao.user.common.entry.enumeration.OtherID;
import com.wepiao.user.common.enumeration.ResponseInfoEnum;
import com.wepiao.user.common.handler.IdRelationHandler;
import com.wepiao.user.common.handler.IdRelationHistoryHandler;
import com.wepiao.user.common.handler.MobileNum2UIDHandler;
import com.wepiao.user.common.handler.UsersHandler;
import com.wepiao.user.common.redis.RedisKey;
import com.wepiao.user.common.rest.exception.BaseRestException;
import com.wepiao.user.common.service.IdRelationService;
import com.wepiao.user.common.util.LogMessageFormatter;

@Service
public class UserInfoQueryServiceImpl implements UserInfoQueryService {

    private static final Logger      logger = LoggerFactory.getLogger(UserInfoQueryServiceImpl.class);

    @Autowired
    private UsersHandler             usersHandler;

    @Autowired
    private OpenIdInfoHandler        openIdInfoHandler;

    @Autowired
    private IdRelationHandler        idRelationHandler;

    @Autowired
    private IdRelationHistoryHandler idRelationHistoryHandler;

    @Autowired
    private IdRelationService        idRelationService;

    @Autowired
    private MobileNum2UIDHandler     mobileNum2UIDHandler;

    @Autowired
    private UserTagHandler           userTagHandler;

    @Override
    public UserProfileGetRes getUserProfileByUID(UserProfileGetByUIDReq req) throws BaseRestException {
        UserProfileGetRes res = null;
        try {
            Integer uid = usersHandler.getUid(req.getMemberId());
            if (null == uid) {
                logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMessageFormatter.format(LogMsg.MEMBER_ID_NOT_FOUND_DETAIL, req.getMemberId()));
                throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10005);
            }
            Users user = usersHandler.queryOneByUid(uid);
            if (user == null) {
                logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMessageFormatter.format(LogMsg.MEMBER_ID_NOT_FOUND_DETAIL, req.getMemberId()));
                throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10005);
            } else {
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
                // 构造Response
                boolean hasCredential = (null != user.getPassword() && 0 < user.getPassword().length());
                res = new UserProfileGetRes(String.valueOf(user.getUid()), hasCredential, usersHandler.getExtUidByUid(user.getUid()), user
                        .getStatus().getIntVal(), user.getMobileNo(), user.getNickName(), user.getArea(), user.getSex().getIntVal(),
                        user.getBirthday(), user.getEmail(), user.getPhoto(), user.getUserName(), user.getUserKey(), signature, maritalStat, carrer,
                        enrollmentYear, highestEdu, school, watchCPNum);
            }
        } catch (BaseRestException be) {
            throw be;
        } catch (DataAccessException me) {
            logger.error(LogMsg.BASE_MSG, req.getRequestId(), me.getMessage());
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E50001);
        }
        return res;
    }

    /**
     * 通过uid得到用户基本信息
     * 
     * @param req
     * @return
     * @throws BaseRestException
     *
     */
    @Override
    public UserInfoGetRes getUserByUID(UserInfoGetByUIDReq req) throws BaseRestException {
        UserInfoGetRes res = null;
        try {
            Integer uid = usersHandler.getUid(req.getMemberId());
            if (null == uid) {
                logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMessageFormatter.format(LogMsg.MEMBER_ID_NOT_FOUND_DETAIL, req.getMemberId()));
                throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10005);
            }
            Users user = usersHandler.queryOneByUid(uid);
            if (user == null) {
                logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMessageFormatter.format(LogMsg.MEMBER_ID_NOT_FOUND_DETAIL, req.getMemberId()));
                throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10005);
            } else {
                // 构造Response
                boolean hasCredential = (null != user.getPassword() && 0 < user.getPassword().length());
                res = new UserInfoGetRes(String.valueOf(user.getUid()), hasCredential, usersHandler.getExtUidByUid(user.getUid()), user.getStatus()
                        .getIntVal(), user.getMobileNo(), user.getNickName(), user.getArea(), user.getSex().getIntVal(), user.getBirthday(),
                        user.getEmail(), user.getPhoto(), user.getUserName(), user.getUserKey());
            }
        } catch (BaseRestException be) {
            throw be;
        } catch (DataAccessException me) {
            logger.error(LogMsg.BASE_MSG, req.getRequestId(), me.getMessage());
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E50001);
        }
        return res;
    }

    /**
     * 通过openId得到用户基本信息
     * 
     * @param req
     * @return
     * @throws BaseRestException
     *
     */
    @Override
    public UserInfoGetRes getUserByOpenId(UserInfoGetByOpenIdReq req) throws BaseRestException {
        UserInfoGetRes res = null;
        try {
            String openId = req.getOpenID();
            OtherID otherId = OtherID.parseInt(req.getOtherID());
            String unionId = null;
            int uid = Constants.NOT_EXISTED_UID;

            // 查询id间的关联关系
            IdRelationNode parentRelationNode = idRelationHandler.getParentNode(new IdRelationNode(openId, otherId));
            if (null != parentRelationNode) {
                if (OtherID.UnionID == parentRelationNode.getIdType()) {
                    unionId = parentRelationNode.getId();
                    parentRelationNode = idRelationHandler.getParentNode(parentRelationNode);
                    if (null != parentRelationNode && OtherID.UID == parentRelationNode.getIdType()) {
                        uid = Integer.parseInt(parentRelationNode.getId());
                    }
                } else if (OtherID.UID == parentRelationNode.getIdType()) {
                    uid = Integer.parseInt(parentRelationNode.getId());
                } else {
                    // 目前没这种情况
                }
            }

            // openid为微信情形下，存在第三方信息并未记录的情况，需要遍历unionid下的所有openid并取出存在的最近一次登录的第三方信息。
            OpenIdInfo openIdInfo = null;
            if (null == unionId) {
                openIdInfo = openIdInfoHandler.getOpenIdInfoByOpenId(openId);
            } else {
                List<IdRelationHistory> weixinOpenIdNodeList = idRelationHistoryHandler.getRelationHistory(new IdRelationNode(unionId,
                        OtherID.UnionID));
                if (null != weixinOpenIdNodeList && !weixinOpenIdNodeList.isEmpty()) {
                    OpenIdInfo latestOpenIdInfo = openIdInfoHandler.getOpenIdInfoByOpenId(weixinOpenIdNodeList.get(0).getChildId());
                    // 仅在unionId超过2个子节点的时候才去遍历
                    if (1 < weixinOpenIdNodeList.size()) {
                        for (int i = 1; i < weixinOpenIdNodeList.size(); i++) {
                            OpenIdInfo currentOpenIdInfo = openIdInfoHandler.getOpenIdInfoByOpenId(weixinOpenIdNodeList.get(i).getChildId());
                            if (null != currentOpenIdInfo) {
                                if (null != latestOpenIdInfo) {
                                    if (currentOpenIdInfo.getLastLoginTime().after(latestOpenIdInfo.getLastLoginTime())) {
                                        latestOpenIdInfo = currentOpenIdInfo;
                                    }
                                } else {
                                    latestOpenIdInfo = currentOpenIdInfo;
                                }
                            } else {
                                continue;
                            }
                        }
                    }
                    openIdInfo = latestOpenIdInfo;
                }
            }

            if (openIdInfo == null) {
                logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMessageFormatter.format(LogMsg.OPEN_ID_NOT_FOUND_DETAIL, openId));
                throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10005);
            } else {
                // 存在Uid信息的话，则返回openid、unionid和uid; 否则只返回openIdInfo
                if (Constants.NOT_EXISTED_UID != uid) {
                    // 查询用户详细信息
                    Users user = usersHandler.queryOneByUid(uid);
                    if (user == null) {
                        logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMessageFormatter.format(LogMsg.MEMBER_ID_NOT_FOUND_DETAIL, uid));
                        throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E20006);
                    } else {
                        // 取User的Status作为用户状态
                        int status = user.getStatus().getIntVal();

                        // 构造Response
                        boolean hasCredential = (null != user.getPassword() && 0 < user.getPassword().length());
                        res = new UserInfoGetByOpenIdRes(String.valueOf(user.getUid()), hasCredential, usersHandler.getExtUidByUid(user.getUid()),
                                unionId, openId, openIdInfo.getOtherId().getIntVal(), status, user.getMobileNo(), user.getNickName(), user.getArea(),
                                user.getSex().getIntVal(), user.getBirthday(), user.getEmail(), user.getPhoto(), user.getUserName(),
                                user.getUserKey());
                    }
                } else {
                    // 构造Response
                    res = new UserInfoGetByOpenIdRes(null, false, null, unionId, openId, openIdInfo.getOtherId().getIntVal(), openIdInfo.getStatus()
                            .getIntVal(), null, openIdInfo.getNickName(), null, Gender.UNKNOWN.getIntVal(), null, null, openIdInfo.getPhoto(), null,
                            null);
                }
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
        return res;
    }

    /**
     * 通过电话号码得到用户基本信息
     * 
     * @param req
     * @return
     * @throws BaseRestException
     *
     */
    @Override
    public UserInfoGetRes getUserByMobileNo(UserInfoGetByMobileNoReq req) throws BaseRestException {
        UserInfoGetRes res = null;
        try {

            // 取手机号-Uid映射关系
            int uid = mobileNum2UIDHandler.getUIDByMobileNo(req.getMobileNo());
            boolean isMobileNoMappingExisted = true;
            if (Constants.NOT_EXISTED_UID == uid) {
                isMobileNoMappingExisted = false;
            }

            if (!isMobileNoMappingExisted) {
                logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMessageFormatter.format(LogMsg.MOBILE2UID_NOT_FOUND, req.getMobileNo()));
                throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E20008, req.getMobileNo());
            } else {
                // 查询用户详细信息
                Users user = usersHandler.queryOneByUid(uid);
                if (user == null) {
                    logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMessageFormatter.format(LogMsg.MEMBER_ID_NOT_FOUND_DETAIL, uid));
                    throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E20006);
                } else {
                    // 构造Response
                    boolean hasCredential = (null != user.getPassword() && 0 < user.getPassword().length());
                    res = new UserInfoGetRes(String.valueOf(user.getUid()), hasCredential, usersHandler.getExtUidByUid(user.getUid()), user
                            .getStatus().getIntVal(), user.getMobileNo(), user.getNickName(), user.getArea(), user.getSex().getIntVal(),
                            user.getBirthday(), user.getEmail(), user.getPhoto(), user.getUserName(), user.getUserKey());
                }
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
    @Deprecated
    public UserInfoUploadPhotoRes uploadUserPhoto(UserInfoUploadPhotoReq req) throws BaseRestException {
        // Not Supported
        logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMsg.HTTP_METHOD_FORBIDDEN);
        throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10001);
    }

    /**
     * 根据openId得到openInfo
     * 
     * @param req
     * @return
     * @throws BaseRestException
     *
     */
    @Override
    public OpenIdInfoGetRes getOpenIdInfo(OpenIdInfoGetReq req) throws BaseRestException {
        OpenIdInfoGetRes res = null;
        try {
            String openId = req.getOpenID();
            String unionId = null;

            // 查询id间的关联关系
            IdRelationNode parentRelationNode = idRelationHandler.getParentNode(new IdRelationNode(openId, OtherID.LEGACY));
            if (null != parentRelationNode) {
                if (OtherID.UnionID == parentRelationNode.getIdType()) {
                    unionId = parentRelationNode.getId();
                }
            }

            OpenIdInfo openIdInfo = null;
            // openid为微信情形下，存在第三方信息并未记录的情况，需要遍历unionid下的所有openid并取出存在的最近一次登录的第三方信息。
            if (null == unionId) {
                openIdInfo = openIdInfoHandler.getOpenIdInfoByOpenId(openId);
            } else {
                List<IdRelationHistory> weixinOpenIdNodeList = idRelationHistoryHandler.getRelationHistory(new IdRelationNode(unionId,
                        OtherID.UnionID));
                if (null != weixinOpenIdNodeList && !weixinOpenIdNodeList.isEmpty()) {
                    OpenIdInfo latestOpenIdInfo = openIdInfoHandler.getOpenIdInfoByOpenId(weixinOpenIdNodeList.get(0).getChildId());
                    // 仅在unionId超过2个子节点的时候才去遍历
                    if (1 < weixinOpenIdNodeList.size()) {
                        for (int i = 1; i < weixinOpenIdNodeList.size(); i++) {
                            OpenIdInfo currentOpenIdInfo = openIdInfoHandler.getOpenIdInfoByOpenId(weixinOpenIdNodeList.get(i).getChildId());
                            if (null != currentOpenIdInfo) {
                                if (null != latestOpenIdInfo) {
                                    if (currentOpenIdInfo.getLastLoginTime().after(latestOpenIdInfo.getLastLoginTime())) {
                                        latestOpenIdInfo = currentOpenIdInfo;
                                    }
                                } else {
                                    latestOpenIdInfo = currentOpenIdInfo;
                                }
                            } else {
                                continue;
                            }
                        }
                    }
                    openIdInfo = latestOpenIdInfo;
                }
            }

            if (openIdInfo == null) {
                logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMessageFormatter.format(LogMsg.OPEN_ID_NOT_FOUND_DETAIL, openId));
                throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10005);
            } else {
                // 构造Response
                res = new OpenIdInfoGetRes(openIdInfo.getOpenId(), openIdInfo.getOtherId().getIntVal(), openIdInfo.getStatus().getIntVal(),
                        openIdInfo.getBindingStatus().getIntVal(), openIdInfo.getNickName(), openIdInfo.getPhoto());
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

    /**
     * 通过uid获取用户的关系
     * 
     * @param req
     * @return
     * @throws BaseRestException
     *
     */
    @Override
    public IdRelationGetRes getIdRelation(IdRelationGetReq req) throws BaseRestException {
        IdRelationGetRes res = null;
        try {
            // 若id传入为extUid则需要预处理一下, 此处会访问extUidMapping太多次，暂时屏蔽
            String realId = req.getId();
            // Integer uid = extUIDHandler.getUIDByUnknowUID(req.getId());
            // if (uid != null) {
            // realId = String.valueOf(uid);
            // }
            IdRelationNode rootNode = idRelationHandler.getRootNode(new IdRelationNode(realId, OtherID.LEGACY));
            IdRelationNode idTree = idRelationService.getIdTreeFromRoot(rootNode);
            // 构造Response
            res = new IdRelationGetRes(idTree);
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
     * 通过用户的手机号获取openID的列表
     * 
     * @param req
     * @return
     * @throws BaseRestException
     *
     */
    @Override
    public OpenIdListGetByMobileRes getOpenIdListByMobile(OpenIdListGetByMobileReq req) throws BaseRestException {
        OpenIdListGetByMobileRes res = null;
        try {
            // 取手机号-Uid映射关系
            int uid = mobileNum2UIDHandler.getUIDByMobileNo(req.getMobileNo());
            boolean isMobileNoMappingExisted = true;
            if (Constants.NOT_EXISTED_UID == uid) {
                isMobileNoMappingExisted = false;
            }

            if (!isMobileNoMappingExisted) {
                logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMessageFormatter.format(LogMsg.MOBILE2UID_NOT_FOUND, req.getMobileNo()));
                throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E20008, req.getMobileNo());
            } else {
                // 获取所有的非根节点
                IdRelationNode uidNode = new IdRelationNode(String.valueOf(uid), OtherID.UID);
                List<IdRelationNode> nonRootIdList = idRelationService.getIdListFromRoot(uidNode);
                if (null != nonRootIdList) {
                    List<OpenId> openIdList = new ArrayList<OpenId>();
                    for (IdRelationNode id : nonRootIdList) {
                        OpenId openId = new OpenId(id.getId(), id.getIdType().getIntVal());
                        openIdList.add(openId);
                    }
                    // 构造Response
                    res = new OpenIdListGetByMobileRes(openIdList);
                }
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

    /**
     * 通过传入的openid获取最早的用户注册时间<br />
     * 1.根据openid查询到根节点 <br />
     * 2.通过根节点查询所有子节点 <br />
     * 3.遍历所有子节点查询,选出最早的注册时间返回
     */
    @Override
    public UserFirstRegistTimeRes getUserFirstRegistTime(OpenIdInfoGetReq req) throws BaseRestException {
        UserFirstRegistTimeRes userFirstRegistTimeRes = null;
        List<Integer> registTimeList = new ArrayList<Integer>();
        String openId = req.getOpenID();
        //查询openId对应的信息
        OpenIdInfo openIdInfo = openIdInfoHandler.getOpenIdInfoByOpenId(openId);
        OtherID otherId = null;
        if (null != openIdInfo) {
            otherId = openIdInfo.getOtherId();
            registTimeList.add(Integer.valueOf("" + openIdInfo.getCreateTime().getTime() / 1000L));
        } else {
            otherId = OtherID.parseInt(0);
        }

        IdRelationNode idRelationNode = new IdRelationNode(openId, otherId);
        // 1.找到根节点
        IdRelationNode rootNode = idRelationHandler.getRootNode(idRelationNode);
        // 判断根节点是不是自身
        if (!openId.equals(rootNode.getId())) {
            // 2.找到所有的非根节点。
            List<IdRelationNode> nonRootNodeList = idRelationService.getIdListFromRoot(rootNode);
            // 3.根据idType查询对应的表获取注册时间戳
            for (IdRelationNode subIdRelationNode : nonRootNodeList) {
                //判断是否跟之前的openId查询重复
                if (!openId.equals(subIdRelationNode.getId())) {
                    OpenIdInfo subOpenIdInfo = openIdInfoHandler.getOpenIdInfoByOpenId(subIdRelationNode.getId());
                    if (null != subOpenIdInfo) {
                        registTimeList.add(Integer.valueOf("" + subOpenIdInfo.getCreateTime().getTime() / 1000L));
                    }
                }
            }
        }

        //排序选出最早的一个注册时间
        if (registTimeList.size() > 0) {
            Object[] registArr = registTimeList.toArray();
            Arrays.sort(registArr);
            userFirstRegistTimeRes = new UserFirstRegistTimeRes((Integer) registArr[0]);
        } else {
            //返回当前的时间戳
            userFirstRegistTimeRes = new UserFirstRegistTimeRes(Integer.valueOf("" + System.currentTimeMillis() / 1000L));
        }

        return userFirstRegistTimeRes;
    }

    /**
     * 构造一个打标签的请求去重组用户id关系树上的全部标签
     * 
     * @param id
     * @param idType
     * @param tag
     * @param tagVal
     */
    //  private void handleStaticTagReOrganized(String id, int idType, String tag, String tagVal) {
    //    // 重组用户id关系树上的全部标签,为所有的用户id打上静态标签, syncMode = 1 且needPersist = true
    //    UserTagAddReq addTagReq = new UserTagAddReq(id, idType, tag, tagVal, 0, true, 0);
    //    String reqStr = JSON.toJSONString(addTagReq);
    //    MessageQueues.getInstance().put(reqStr, MessageQueues.USER_TAG_ADD_QUEUE_NAME);
    //  }
}
