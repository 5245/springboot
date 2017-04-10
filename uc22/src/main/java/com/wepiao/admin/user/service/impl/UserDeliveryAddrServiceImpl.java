package com.wepiao.admin.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.wepiao.admin.user.rest.msg.SingleResultRes;
import com.wepiao.admin.user.rest.msg.UserDeliveryAddrCRUDReq;
import com.wepiao.admin.user.rest.msg.UserDeliveryAddrCRURes;
import com.wepiao.admin.user.service.UserDeliveryAddrService;
import com.wepiao.user.common.constant.LogMsg;
import com.wepiao.user.common.dao.UserDeliveryAddrMapper;
import com.wepiao.user.common.entry.UserDeliveryAddr;
import com.wepiao.user.common.entry.Users;
import com.wepiao.user.common.enumeration.ResponseInfoEnum;
import com.wepiao.user.common.handler.UsersHandler;
import com.wepiao.user.common.redis.RedisMapper;
import com.wepiao.user.common.rest.exception.BaseRestException;
import com.wepiao.user.common.util.LogMessageFormatter;
import com.wepiao.user.common.util.StringUtil;

@Service
public class UserDeliveryAddrServiceImpl implements UserDeliveryAddrService {

    private static final Logger    logger = LoggerFactory.getLogger(UserDeliveryAddrServiceImpl.class);

    @Autowired
    private UserDeliveryAddrMapper userDeliveryAddrMapper;

    @Autowired
    private UsersHandler           usersHandler;

    /**
     * 添加一条用户收货地址
     * 
     * @param req
     * @return
     *
     */
    @Override
    public UserDeliveryAddrCRURes insertUserDeliverAddr(UserDeliveryAddrCRUDReq req) {
        UserDeliveryAddrCRURes res = null;
        try {
            Integer uid = usersHandler.getUid(req.getMemberId());
            if (null == uid) {
                logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMessageFormatter.format(LogMsg.MEMBER_ID_NOT_FOUND_DETAIL, req.getMemberId()));
                throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10005);
            }
            //校验uid是否实际存在
            Users user = usersHandler.queryOneByUid(uid);
            if (null == user) {
                logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMessageFormatter.format(LogMsg.MEMBER_ID_NOT_FOUND_DETAIL, req.getMemberId()));
                throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10005);
            }

            //先清除对应uid的收货地址缓存
            RedisMapper.removeUserDeliveryAddrs(uid);
            //清除默认收货地址
            RedisMapper.removeDefaultUserDeliveryAddr(uid);

            Integer nowTime = Integer.parseInt(System.currentTimeMillis() / 1000L + "");
            UserDeliveryAddr addr = new UserDeliveryAddr(null, uid, req.getSoureCode(), req.getDeliveryAddress(), req.getReceiver(),
                    req.getReceiverMobile(), req.getDistrictName(), req.getCountryCode(), req.getIsDefault(), 0, nowTime);
            // 处理默认收货地址
            if (null != req.getIsDefault() && 1 == req.getIsDefault()) {
                // 取消当前设置的默认收货地址
                userDeliveryAddrMapper.updateCancelDefaultAddrByUid(uid);
            } else {
                // 查用户可用收货地址数量,若没有则本条为默认收货地址
                if (0 == userDeliveryAddrMapper.countCanUseAddrByUid(uid)) {
                    addr.setIsDefault(1);
                }
            }
            // 插入收货地址
            userDeliveryAddrMapper.insert(addr);

            //查询用户收货地址列表
            List<UserDeliveryAddr> addrList = userDeliveryAddrMapper.queryByUid(uid);
            if (null != addrList && addrList.size() > 0) {
                //设置到缓存
                RedisMapper.setUserDeliveryAddrs(uid, addrList);
                //设置默认收货地址到缓存
                for (UserDeliveryAddr userDeliveryAddr : addrList) {
                    if (null != userDeliveryAddr.getIsDefault() && 1 == userDeliveryAddr.getIsDefault().intValue()) {
                        RedisMapper.setDefaultUserDeliveryAddr(uid, userDeliveryAddr);
                    }
                }
            }

            res = new UserDeliveryAddrCRURes(addr.getId(), uid.toString(), usersHandler.getExtUidByUid(uid), addr.getSourceCode(),
                    addr.getDeliveryAddress(), addr.getReceiver(), addr.getReceiverMobile(), addr.getDistrictName(), addr.getCountryCode(),
                    addr.getIsDefault(), addr.getCreateTime());
        } catch (BaseRestException be) {
            throw be;
        } catch (DataAccessException me) {
            logger.error(LogMsg.BASE_MSG, req.getRequestId(), me.getMessage());
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E50001);
        }
        return res;
    }

    /**
     * 删除一条用户收货地址
     * 
     * @param req
     * @return
     *
     */
    @Override
    public SingleResultRes deleteUserDeliverAddr(UserDeliveryAddrCRUDReq req) {
        SingleResultRes res = null;
        int result = 1;
        try {
            Integer uid = usersHandler.getUid(req.getMemberId());
            if (null == uid) {
                logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMessageFormatter.format(LogMsg.MEMBER_ID_NOT_FOUND_DETAIL, req.getMemberId()));
                throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10005);
            }
            //先清除对应uid的收货地址缓存
            RedisMapper.removeUserDeliveryAddrs(uid);
            //清除默认收货地址
            RedisMapper.removeDefaultUserDeliveryAddr(uid);

            UserDeliveryAddr addr = userDeliveryAddrMapper.queryOneById(uid, req.getId());
            if (null != addr) {
                // 是否需要处理默认收货地址
                boolean isNeedHandler = (1 == addr.getIsDefault());
                // 把本条记录标记为被删除
                addr.setIsDefault(0);
                addr.setIsDel(1);
                userDeliveryAddrMapper.update(addr);
                // 判断被删除的收货地址是否为默认收货地址
                if (isNeedHandler) {
                    // 若是则选取最后的一条收货地址设置为默认收货地址
                    UserDeliveryAddr lastAddr = userDeliveryAddrMapper.queryLastOneByUid(uid);
                    if (null != lastAddr) {
                        lastAddr.setIsDefault(1);
                        userDeliveryAddrMapper.update(lastAddr);
                    }
                }
            } else {
                logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMsg.DELIVERY_ADDR_NOT_FOUND);
                throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10005);
            }

            //查询用户收货地址列表
            List<UserDeliveryAddr> addrList = userDeliveryAddrMapper.queryByUid(uid);
            if (null != addrList && addrList.size() > 0) {
                //设置到缓存
                RedisMapper.setUserDeliveryAddrs(uid, addrList);
                //设置默认收货地址到缓存
                for (UserDeliveryAddr userDeliveryAddr : addrList) {
                    if (null != userDeliveryAddr.getIsDefault() && 1 == userDeliveryAddr.getIsDefault().intValue()) {
                        RedisMapper.setDefaultUserDeliveryAddr(uid, userDeliveryAddr);
                    }
                }
            }

            result = 0;
            res = new SingleResultRes(result);
        } catch (BaseRestException be) {
            throw be;
        } catch (DataAccessException me) {
            logger.error(LogMsg.BASE_MSG, req.getRequestId(), me.getMessage());
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E50001);
        }
        return res;
    }

    /**
     * 更新用户收货地址
     * 
     * @param reqId
     * @param reqMap
     * @return
     */
    @Override
    public UserDeliveryAddrCRURes updateUserDeliverAddr(UserDeliveryAddrCRUDReq req) {
        UserDeliveryAddrCRURes res = null;
        try {
            Integer id = req.getId();
            Integer uid = usersHandler.getUid(req.getMemberId());
            if (null == uid) {
                logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMessageFormatter.format(LogMsg.MEMBER_ID_NOT_FOUND_DETAIL, req.getMemberId()));
                throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10005);
            }

            //先清除对应uid的收货地址缓存
            RedisMapper.removeUserDeliveryAddrs(uid);
            //清除默认收货地址
            RedisMapper.removeDefaultUserDeliveryAddr(uid);

            UserDeliveryAddr addr = userDeliveryAddrMapper.queryOneById(uid, id);
            if (null == addr) {
                logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMsg.DELIVERY_ADDR_NOT_FOUND);
                throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10005);
            } else {
                if (!StringUtil.isEmptyCheckTrim(req.getCountryCode())) {
                    addr.setCountryCode(req.getCountryCode());
                }
                if (!StringUtil.isEmptyCheckTrim(req.getDeliveryAddress())) {
                    addr.setDeliveryAddress(req.getDeliveryAddress());
                }
                if (!StringUtil.isEmptyCheckTrim(req.getDistrictName())) {
                    addr.setDistrictName(req.getDistrictName());
                }
                // 是否需要处理默认收货地址
                if (null != req.getIsDefault() && null != addr.getIsDefault()) {
                    if (1 == req.getIsDefault() && 0 == addr.getIsDefault()) {
                        // 取消当前设置的默认收货地址
                        userDeliveryAddrMapper.updateCancelDefaultAddrByUid(uid);
                    }
                }
                if (null != req.getIsDefault() && (1 == req.getIsDefault() || 0 == req.getIsDefault())) {
                    addr.setIsDefault(req.getIsDefault());
                }
                if (!StringUtil.isEmptyCheckTrim(req.getReceiver())) {
                    addr.setReceiver(req.getReceiver());
                }
                if (!StringUtil.isEmptyCheckTrim(req.getReceiverMobile())) {
                    addr.setReceiverMobile(req.getReceiverMobile());
                }
                // 更新收货地址
                userDeliveryAddrMapper.update(addr);
                res = new UserDeliveryAddrCRURes(id, uid.toString(), usersHandler.getExtUidByUid(uid), addr.getSourceCode(),
                        addr.getDeliveryAddress(), addr.getReceiver(), addr.getReceiverMobile(), addr.getDistrictName(), addr.getCountryCode(),
                        addr.getIsDefault(), addr.getCreateTime());
            }

            //查询用户收货地址列表
            List<UserDeliveryAddr> addrList = userDeliveryAddrMapper.queryByUid(uid);
            if (null != addrList && addrList.size() > 0) {
                //设置到缓存
                RedisMapper.setUserDeliveryAddrs(uid, addrList);
                //设置默认收货地址到缓存
                for (UserDeliveryAddr userDeliveryAddr : addrList) {
                    if (null != userDeliveryAddr.getIsDefault() && 1 == userDeliveryAddr.getIsDefault().intValue()) {
                        RedisMapper.setDefaultUserDeliveryAddr(uid, userDeliveryAddr);
                    }
                }
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
     * 查询用户的收货地址
     * 
     * @param reqId
     * @param reqMap
     * @return
     *
     */
    @Override
    public List<UserDeliveryAddrCRURes> queryUserDeliverAddrListByUid(UserDeliveryAddrCRUDReq req) {
        List<UserDeliveryAddrCRURes> res = new ArrayList<UserDeliveryAddrCRURes>();
        try {
            Integer uid = usersHandler.getUid(req.getMemberId());
            if (null == uid) {
                logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMessageFormatter.format(LogMsg.MEMBER_ID_NOT_FOUND_DETAIL, req.getMemberId()));
                throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10005);
            }

            //先从缓存中获取用户收货地址列表
            List<UserDeliveryAddr> addrList = RedisMapper.getUserDeliveryAddrs(uid);
            if (null == addrList || addrList.size() == 0) {
                //若无则去db查询
                addrList = userDeliveryAddrMapper.queryByUid(uid);
                if (null != addrList && addrList.size() > 0) {
                    //设置到缓存
                    RedisMapper.setUserDeliveryAddrs(uid, addrList);
                }
            }
            if (null != addrList) {
                String extUid = usersHandler.getExtUidByUid(uid);
                for (UserDeliveryAddr addr : addrList) {
                    UserDeliveryAddrCRURes resEle = new UserDeliveryAddrCRURes(addr.getId(), uid.toString(), extUid, addr.getSourceCode(),
                            addr.getDeliveryAddress(), addr.getReceiver(), addr.getReceiverMobile(), addr.getDistrictName(), addr.getCountryCode(),
                            addr.getIsDefault(), addr.getCreateTime());
                    res.add(resEle);
                }
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
     * 获取某一用户的某一条收货地址
     * 
     * @param req
     * @return
     *
     */
    @Override
    public UserDeliveryAddrCRURes queryUserDeliverAddrById(UserDeliveryAddrCRUDReq req) {
        UserDeliveryAddrCRURes res = null;
        try {
            Integer id = req.getId();
            Integer uid = usersHandler.getUid(req.getMemberId());
            if (null == uid) {
                logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMessageFormatter.format(LogMsg.MEMBER_ID_NOT_FOUND_DETAIL, req.getMemberId()));
                throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10005);
            }

            UserDeliveryAddr addr = null;
            //先从缓存中获取用户收货地址列表
            List<UserDeliveryAddr> addrList = RedisMapper.getUserDeliveryAddrs(uid);
            if (null != addrList && addrList.size() > 0) {
                for (UserDeliveryAddr userDeliveryAddr : addrList) {
                    if (id.intValue() == userDeliveryAddr.getId().intValue()) {
                        addr = userDeliveryAddr;
                        break;
                    }
                }
            }
            if (null == addr) {
                //缓存中没有则查db
                addr = userDeliveryAddrMapper.queryOneById(uid, id);
                if (null != addr) {
                    //设置到缓存
                    if (null == addrList) {
                        addrList = new ArrayList<UserDeliveryAddr>();
                    }
                    addrList.add(addr);
                    RedisMapper.setUserDeliveryAddrs(uid, addrList);
                }
            }
            if (null != addr) {
                res = new UserDeliveryAddrCRURes(id, uid.toString(), usersHandler.getExtUidByUid(uid), addr.getSourceCode(),
                        addr.getDeliveryAddress(), addr.getReceiver(), addr.getReceiverMobile(), addr.getDistrictName(), addr.getCountryCode(),
                        addr.getIsDefault(), addr.getCreateTime());
            } else {
                //若缓存和db都没有则返回数据不存在
                logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMessageFormatter.format(LogMsg.MEMBER_ID_NOT_FOUND_DETAIL, req.getMemberId()));
                throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10005);
            }
        } catch (BaseRestException be) {
            throw be;
        } catch (DataAccessException me) {
            logger.error(LogMsg.BASE_MSG, req.getRequestId(), me.getMessage());
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E50001);
        }
        return res;
    }

    @Override
    public int countUserDeliverAddr(UserDeliveryAddrCRUDReq req) {
        int count = 0;
        try {
            Integer uid = usersHandler.getUid(req.getMemberId());
            if (null == uid) {
                logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMessageFormatter.format(LogMsg.MEMBER_ID_NOT_FOUND_DETAIL, req.getMemberId()));
                throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10005);
            }
            count = userDeliveryAddrMapper.countCanUseAddrByUid(uid);
        } catch (BaseRestException be) {
            throw be;
        } catch (DataAccessException me) {
            logger.error(LogMsg.BASE_MSG, req.getRequestId(), me.getMessage());
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E50001);
        }
        return count;
    }

    @Override
    public UserDeliveryAddrCRURes queryDefaultUserDeliverAddr(UserDeliveryAddrCRUDReq req) {
        UserDeliveryAddrCRURes res = null;
        try {
            Integer uid = usersHandler.getUid(req.getMemberId());
            if (null == uid) {
                logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMessageFormatter.format(LogMsg.MEMBER_ID_NOT_FOUND_DETAIL, req.getMemberId()));
                throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10005);
            }

            //先从缓存中获取用户默认收货地址
            UserDeliveryAddr addr = RedisMapper.getDefaultUserDeliveryAddr(uid);
            if (null == addr) {
                addr = userDeliveryAddrMapper.queryDefaultArrByUid(uid);
                if (null != addr) {
                    //设置到缓存
                    RedisMapper.setDefaultUserDeliveryAddr(uid, addr);
                }
            }
            if (null != addr) {
                res = new UserDeliveryAddrCRURes(addr.getId(), uid.toString(), usersHandler.getExtUidByUid(uid), addr.getSourceCode(),
                        addr.getDeliveryAddress(), addr.getReceiver(), addr.getReceiverMobile(), addr.getDistrictName(), addr.getCountryCode(),
                        addr.getIsDefault(), addr.getCreateTime());
            }
        } catch (BaseRestException be) {
            throw be;
        } catch (DataAccessException me) {
            logger.error(LogMsg.BASE_MSG, req.getRequestId(), me.getMessage());
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E50001);
        }
        return res;
    }
}
