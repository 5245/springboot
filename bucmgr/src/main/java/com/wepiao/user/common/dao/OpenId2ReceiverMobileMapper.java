package com.wepiao.user.common.dao;

import java.util.List;

import com.wepiao.user.common.entry.OpenId2ReceiverMobile;
import com.wepiao.user.common.entry.enumeration.InternalServiceType;

public interface OpenId2ReceiverMobileMapper {

    int deleteByOpenIdAndId(String id, Integer rid);

    int insert(OpenId2ReceiverMobile record);

    int updateByOpenIdAndId(OpenId2ReceiverMobile record);

    List<OpenId2ReceiverMobile> queryAllByOpenIdAndServiceType(String openId, InternalServiceType servicetype);
    
    OpenId2ReceiverMobile queryLatestOneByOpenIdAndServiceType(String openId, InternalServiceType serviceType);
    
    List<OpenId2ReceiverMobile> queryAllByOpenId(String openId);

    OpenId2ReceiverMobile queryOneByOpenIdAndMobileAndServiceType(String openId, String mobileno, InternalServiceType servicetype);
}