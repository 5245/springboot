package com.wepiao.user.common.dao;

import com.wepiao.user.common.entry.OpenId2Guid;

public interface OpenId2GuidMapper {

    int insert(OpenId2Guid record);

    OpenId2Guid queryAllByOpenId(String openId);
}