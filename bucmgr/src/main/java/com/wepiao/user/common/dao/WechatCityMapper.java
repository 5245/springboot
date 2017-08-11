package com.wepiao.user.common.dao;

import java.util.List;

import com.wepiao.user.common.entry.WechatCity;

public interface WechatCityMapper {

    public int insert(WechatCity wechatCity);

    /**
     * 查询国标list 
     * @return
     */
    public List<WechatCity> queryAll();

}
