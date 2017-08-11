package com.wepiao.user.common.dao;

import com.wepiao.user.common.entry.UserDynamicTag;
import com.wepiao.user.common.entry.enumeration.OtherID;

public interface UserDynamicTagMapper {
    public UserDynamicTag queryUserDynamicTagById(String id, OtherID idType);

    public int deleteUserDynamicTag(String id, OtherID idType);

    public int insertUserDynamicTag(UserDynamicTag userDynamicTag);

    public int updateUserDynamicTag(UserDynamicTag userDynamicTag);
    
    /**
     * warmAllDynamicTagData: <br/>
     * 预热所有用户动态标签到redis <br/>
     * @author liujie
     */
    public void warmAllDynamicTagData();
}
