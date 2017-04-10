package com.wepiao.admin.user.service.dragon.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import redis.clients.jedis.exceptions.JedisConnectionException;

import com.wepiao.admin.user.rest.msg.dragon.OpenIdGetByUIDReq;
import com.wepiao.admin.user.rest.msg.dragon.OpenIdGetByUIDRes;
import com.wepiao.admin.user.rest.msg.dragon.OpenIdInfoGetReq;
import com.wepiao.admin.user.rest.msg.dragon.OpenIdInfoGetRes;
import com.wepiao.admin.user.rest.msg.dragon.UnionIdGetByOpenIdReq;
import com.wepiao.admin.user.rest.msg.dragon.UnionIdGetByOpenIdRes;
import com.wepiao.admin.user.service.dragon.UserInfoQueryService4Dragon;
import com.wepiao.admin.user.service.handler.OpenIdInfoHandler;
import com.wepiao.user.common.constant.LogMsg;
import com.wepiao.user.common.dao.dragon.TWeiyingUsersMapper;
import com.wepiao.user.common.dao.dragon.TWeiyingUsersUnionIdMapper;
import com.wepiao.user.common.entry.IdRelationNode;
import com.wepiao.user.common.entry.OpenIdInfo;
import com.wepiao.user.common.entry.Users;
import com.wepiao.user.common.entry.dragon.TWeiyingUsers;
import com.wepiao.user.common.entry.enumeration.OtherID;
import com.wepiao.user.common.enumeration.ResponseInfoEnum;
import com.wepiao.user.common.handler.IdRelationHandler;
import com.wepiao.user.common.handler.UsersHandler;
import com.wepiao.user.common.redis.RedisMapper;
import com.wepiao.user.common.rest.exception.BaseRestException;
import com.wepiao.user.common.service.IdRelationService;

@Service
public class UserInfoQueryService4DragonImpl implements UserInfoQueryService4Dragon {

    private static final Logger        logger = LoggerFactory.getLogger(UserInfoQueryService4DragonImpl.class);

    @Autowired
    private TWeiyingUsersMapper        tWeiyingUsersMapper;

    @Autowired
    private TWeiyingUsersUnionIdMapper tWeiyingUsersUnionIdMapper;

    @Autowired
    private IdRelationHandler          idRelationHandler;

    @Autowired
    private IdRelationService          idRelationService;

    @Autowired
    private OpenIdInfoHandler          openIdInfoHandler;

    @Autowired
    private UsersHandler               usersHandler;

    /**
     * 通过用户的uid按照一定顺序获取openID, 不支持extUid做入参
     * 参考顺序为：unionId、手机号对应的openid、qq和weibo的openid
     * @param req
     * @return
     * @throws BaseRestException
     *
     */
    @Override
    public OpenIdGetByUIDRes getOpenIdByUID(OpenIdGetByUIDReq req) throws BaseRestException {
        OpenIdGetByUIDRes res = null;
        String result = null;
        try {
            String uid = req.getMemberId();
            String unionId = null;

            // 临时解决方案，认为uid是2亿-3亿之间的为2016年春节期间可能新增的app用户
            if (Integer.parseInt(uid) < 200000000 || Integer.parseInt(uid) >= 300000000) {
                // 取旧用户体系的数据
                TWeiyingUsers users = RedisMapper.getLegacyUsers(uid);
                if (null == users) {
                    users = tWeiyingUsersMapper.queryOneByUid(Integer.parseInt(uid));
                    if (null != users) {
                        RedisMapper.setLegacyUsers(users);
                    }
                }

                // 首先取旧用户体系的数据
                if (null != users) {
                    unionId = users.getUnionId();
                    if (null != unionId && 0 < unionId.length()) {
                        result = unionId;
                    } else {
                        result = users.getOpenId();
                    }
                }
            }

            // 从旧用户体系没取到，则从新用户体系取
            if (null == result) {
                // 获取新用户体系的非根节点
                IdRelationNode uidNode = new IdRelationNode(uid, OtherID.UID);
                List<IdRelationNode> nonRootIdList = idRelationService.getIdListFromRoot(uidNode);
                if (null != nonRootIdList && !nonRootIdList.isEmpty()) {
                    for (IdRelationNode id : nonRootIdList) {
                        if (OtherID.MOBILE == id.getIdType()) {
                            result = id.getId();
                            break;
                        }
                    }
                }
            }

            res = new OpenIdGetByUIDRes(result);
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
     * 根据openId或者unionId得到openInfo
     * 规则为：
     * 1.入参为openid或者unionid（如果微信）
     * 2.有uid则先获取uid的头像昵称
     * 3.无uid，则区分是否微信，是微信unionid的话
     * 则获取最近一次登录的微信openid的头像昵称；
     * 不是微信的，则直接获取openid对应的头像昵称。
     * @param req
     * @return
     * @throws BaseRestException
     *
     */
    @Override
    public OpenIdInfoGetRes getOpenIdInfo(OpenIdInfoGetReq req) throws BaseRestException {
        OpenIdInfoGetRes res = null;
        try {
            String openId = req.getOpenId();
            // 构造一个伪造的parent node
            IdRelationNode currentNode = new IdRelationNode(openId, OtherID.LEGACY);
            IdRelationNode parentNode = idRelationHandler.getParentNode(currentNode);
            //如果父节点不为空并且为uid直接得到用户信息
            // 获取uid的用户信息
            if (null != parentNode && parentNode.getIdType() == OtherID.UID) {
                Users userInfo = usersHandler.queryOneByUid(Integer.parseInt(parentNode.getId()));
                if (null != userInfo) {
                    res = new OpenIdInfoGetRes(userInfo.getNickName(), userInfo.getPhoto());
                }
            } else {
                List<IdRelationNode> childIdList = idRelationService.getIdListFromRoot(currentNode);
                // 若含有子节点,则当前节点一定是微信的unionId,返回最近一次登录的子节点的信息
                if (null != childIdList && !childIdList.isEmpty()) {
                    OpenIdInfo latestOpenIdInfo = openIdInfoHandler.getOpenIdInfoByOpenId(childIdList.get(0).getId());
                    // 仅在unionId超过2个子节点的时候才去遍历
                    if (1 < childIdList.size()) {
                        for (int i = 1; i < childIdList.size(); i++) {
                            OpenIdInfo currentOpenIdInfo = openIdInfoHandler.getOpenIdInfoByOpenId(childIdList.get(i).getId());
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
                    if (null != latestOpenIdInfo) {
                        res = new OpenIdInfoGetRes(latestOpenIdInfo.getNickName(), latestOpenIdInfo.getPhoto());
                    } else {
                        res = new OpenIdInfoGetRes(null, null);
                    }
                } else { // 当前节点是纯openId
                    OpenIdInfo selfOpenIdInfo = openIdInfoHandler.getOpenIdInfoByOpenId(openId);
                    if (null != selfOpenIdInfo) {
                        res = new OpenIdInfoGetRes(selfOpenIdInfo.getNickName(), selfOpenIdInfo.getPhoto());
                    } else {
                        res = new OpenIdInfoGetRes(null, null);
                    }
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
     * 通过用户的openid获取unionid,如果没有返回其本身
     * @param req
     * @return
     * @throws BaseRestException
     */
    @Override
    public UnionIdGetByOpenIdRes getUniqueIdByOpenId(UnionIdGetByOpenIdReq req) throws BaseRestException {
        UnionIdGetByOpenIdRes res = null;
        try {
            String openId = req.getOpenId();
            // 构造一个伪造的child node获取unionId，获取不到则返回其本身
            IdRelationNode pseudoChildNode = new IdRelationNode(openId, OtherID.LEGACY);
            IdRelationNode parentNode = idRelationHandler.getParentNode(pseudoChildNode);
            if (null != parentNode && parentNode.getIdType() == OtherID.UnionID) {
                res = new UnionIdGetByOpenIdRes(parentNode.getId());
            } else {
                res = new UnionIdGetByOpenIdRes(pseudoChildNode.getId());
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
}
