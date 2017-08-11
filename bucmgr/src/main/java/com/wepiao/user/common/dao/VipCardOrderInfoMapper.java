package com.wepiao.user.common.dao;

import java.util.Date;

import com.wepiao.user.common.entry.VipCardOrderInfo;
import com.wepiao.user.common.entry.enumeration.VipCardOrderStatus;

/**
 * 
 * @description  会员卡订单信息表DAO接口（按照orderId为key分库表）
 * @author sxk
 * @date 2016年9月30日
 *
 */
public interface VipCardOrderInfoMapper {

    public int insert(VipCardOrderInfo vipCardOrderInfo);

    public int delete(String orderId);

    public int update(VipCardOrderInfo vipCardOrderInfo);

    public VipCardOrderInfo queryOneByOrderId(String orderId);

    //更改会员卡使用状态
    public int updateStatus(String orderId, VipCardOrderStatus status);

    //更改会员卡锁定时间
    public int updateLockTime(String orderId, Date lockTime);

    //更改会员卡释放时间
    public int updateReleaseTime(String orderId, Date releaseTime);

    //更改会员卡消费时间
    public int updateConsumeTime(String orderId, Date consumeTime);

    //更改会员卡回退时间
    public int updateRefundTime(String orderId, Date refundTime);

}
