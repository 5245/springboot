package com.wepiao.user.common.dao;

import java.util.Date;
import java.util.List;

import com.wepiao.user.common.entry.OpenId;
import com.wepiao.user.common.entry.OpenIdInfo;
import com.wepiao.user.common.entry.enumeration.BindingStatus;
import com.wepiao.user.common.entry.enumeration.OtherID;
import com.wepiao.user.common.entry.enumeration.Status;

public interface OpenIdInfoMapper {
    public int insert(OpenIdInfo openIdInfo);

    public int update(OpenIdInfo openIdInfo);
    /**
     * 根据oepnid和otherId删除openIdInfo信息 
     * @param openId
     * @param otherId
     */
    public int delete(String openId, OtherID otherId);

    public int updateStatus(String openId, OtherID otherId, Status status);

    public int updateBindingStatus(String openId, OtherID otherId, BindingStatus status);

    public int updateCinemaFavorites(String openId, OtherID otherId, String cinemaFavorites);

    /**
     * 根据openId得到openInfo
     * @param openId
     * @return
     *
     */
    public OpenIdInfo queryOneByOpenId(String openId);

    public List<OpenId> queryBlackList();
    
    public List<OpenIdInfo> queryOpenIdInfoList(int dbIndex,int tableIndex,int perNo,int perSize,Date lastLoginTime);
}
