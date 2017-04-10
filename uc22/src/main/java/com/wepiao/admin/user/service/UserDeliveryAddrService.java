package com.wepiao.admin.user.service;

import java.util.List;

import com.wepiao.admin.user.rest.msg.SingleResultRes;
import com.wepiao.admin.user.rest.msg.UserDeliveryAddrCRUDReq;
import com.wepiao.admin.user.rest.msg.UserDeliveryAddrCRURes;

public interface UserDeliveryAddrService {
    /**
     * 添加一条用户收货地址
     * @param req
     * @return
     *
     */
    public UserDeliveryAddrCRURes insertUserDeliverAddr(UserDeliveryAddrCRUDReq req);

    /**
     * 删除一条用户收货地址
     * @param req
     * @return
     *
     */
    public SingleResultRes deleteUserDeliverAddr(UserDeliveryAddrCRUDReq req);

    /**
     * 更新用户收货地址
     * @param reqId
     * @param reqMap
     * @return
     *
     */
    public UserDeliveryAddrCRURes updateUserDeliverAddr(UserDeliveryAddrCRUDReq req);

    /**
     * 查询用户的所有收货地址
     * @param reqId
     * @param reqMap
     * @return
     *
     */
    public List<UserDeliveryAddrCRURes> queryUserDeliverAddrListByUid(UserDeliveryAddrCRUDReq req);

    /**
     * 获取某一用户的某一条收货地址
     * @param req
     * @return
     *
     */
    public UserDeliveryAddrCRURes queryUserDeliverAddrById(UserDeliveryAddrCRUDReq req);

    /**
     * countUserDeliverAddr: <br/>
     * 获取用户所有可用收货地址的数量
     * @author liujie
     * @param uid
     * @return
     */
    public int countUserDeliverAddr(UserDeliveryAddrCRUDReq req);

    /**
     * queryDefaultUserDeliverAddr: <br/>
     * 查询用户默认收货地址<br/>
     * @author liujie
     * @param req
     * @return
     */
    public UserDeliveryAddrCRURes queryDefaultUserDeliverAddr(UserDeliveryAddrCRUDReq req);

}
