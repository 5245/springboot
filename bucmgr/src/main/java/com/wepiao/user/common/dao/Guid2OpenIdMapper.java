package com.wepiao.user.common.dao;

import java.util.List;

import com.wepiao.user.common.entry.Guid2OpenId;

public interface Guid2OpenIdMapper {

    int insert(Guid2OpenId record);

    List<Guid2OpenId> queryAllByGuid(String guid);
}