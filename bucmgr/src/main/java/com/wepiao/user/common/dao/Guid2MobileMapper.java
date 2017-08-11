package com.wepiao.user.common.dao;

import com.wepiao.user.common.entry.Guid2Mobile;

public interface Guid2MobileMapper {

    int insert(Guid2Mobile record);
    
    int updateByGuidAndId(Guid2Mobile record);

    Guid2Mobile queryOneByGuid(String guid);
}