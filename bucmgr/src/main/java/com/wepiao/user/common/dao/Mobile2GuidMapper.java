package com.wepiao.user.common.dao;

import com.wepiao.user.common.entry.Mobile2Guid;

public interface Mobile2GuidMapper {

    int insert(Mobile2Guid record);

    Mobile2Guid queryOneByMobile(String mobileNo);
    
    int deleteByMobileAndGuid(String mobileNo, String guid);
}