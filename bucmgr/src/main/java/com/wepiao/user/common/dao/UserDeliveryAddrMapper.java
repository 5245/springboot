package com.wepiao.user.common.dao;

import java.util.List;

import com.wepiao.user.common.entry.UserDeliveryAddr;

public interface UserDeliveryAddrMapper {
    /**
     * insert: <br/>
     * 插入用户收货地址<br/>
     * @author liujie
     * @param addr
     * @return
     */
    public int insert(UserDeliveryAddr addr);

    /**
     * update: <br/>
     * 更新用户收货地址 <br/>
     * @author liujie
     * @param addr
     * @return
     */
    public int update(UserDeliveryAddr addr);

    /**
     * 取消默认收货地址
     * @param openId
     * @param id
     * @return
     *
     */
    public int updateCancelDefaultAddrByOpenId(String openId, Integer id);

    /**
     * 查询一条收货地址
     * @param openId
     * @param id
     * @return
     */

    public UserDeliveryAddr queryOneById(String openId, Integer id);

    /**
     * 查询收货地址列表
     * @param openId
     * @return
     *
     */
    public List<UserDeliveryAddr> queryByOpenId(String openId);

}
