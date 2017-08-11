package com.wepiao.user.common.dao;

import java.util.List;

import com.wepiao.user.common.entry.ReceiverMobile2OpenId;

public interface ReceiverMobile2OpenIdMapper {

    int insert(ReceiverMobile2OpenId record);

    List<ReceiverMobile2OpenId> queryAllByMobile(String mobileNo);

    ReceiverMobile2OpenId queryOneByMobileAndOpenId(String mobileNo, String openId);
}