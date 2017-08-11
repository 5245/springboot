package com.wepiao.user.common.dao;

import com.wepiao.user.common.entry.MobileNoMapping;

public interface MobileNoMappingMapper {
    public int insert(MobileNoMapping mobileNoMapping);

    public int delete(String mobileNo);

    public MobileNoMapping queryOneByMobileNo(String mobileNo);
}
