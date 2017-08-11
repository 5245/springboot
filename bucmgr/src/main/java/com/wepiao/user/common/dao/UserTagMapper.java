package com.wepiao.user.common.dao;

import java.util.List;

import com.wepiao.user.common.entry.UserTag;
import com.wepiao.user.common.entry.enumeration.OtherID;

public interface UserTagMapper {
    public UserTag queryUserTagById(String id, OtherID idType);

    public int deleteUserTag(String id, OtherID idType);

    public int insertUserTag(UserTag userTag);

    public int updateUserTag(UserTag userTag);
    
    public int insertSynUserTag(UserTag userTag);
    
    public List<UserTag> queryStaticUserTag4Warm(int dbIndex,int tableIndex,int perNo,int perSize);
    
}
