package com.wepiao.user.common.dao;

import com.wepiao.user.common.entry.PrivateVipInfoHistory;
import com.wepiao.user.common.entry.enumeration.PrivateVipStatus;

public interface PrivateVipInfoHistoryMapper {

    int insert(PrivateVipInfoHistory privateVipInfoHistory);
    
    PrivateVipInfoHistory queryAvailablePrivateVipByMemberId(Integer memberId,PrivateVipStatus status);
    
    int update(PrivateVipInfoHistory privateVipInfoHistory);

}
