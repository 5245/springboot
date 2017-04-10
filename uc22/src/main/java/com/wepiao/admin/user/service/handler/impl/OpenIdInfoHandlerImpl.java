/**
 * Project Name:uc <br>
 * File Name:OpenIdInfoHanderImpl.java <br>
 * Package Name:com.wepiao.admin.user.service.handler.impl <br>
 * Date:2015年10月22日下午3:04:36
 *
 */

package com.wepiao.admin.user.service.handler.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wepiao.admin.user.service.handler.OpenIdInfoHandler;
import com.wepiao.user.common.dao.OpenIdInfoMapper;
import com.wepiao.user.common.entry.OpenId;
import com.wepiao.user.common.entry.OpenIdInfo;
import com.wepiao.user.common.entry.enumeration.BindingStatus;
import com.wepiao.user.common.entry.enumeration.OtherID;
import com.wepiao.user.common.entry.enumeration.Status;
import com.wepiao.user.common.redis.RedisMapper;
import com.wepiao.user.common.rest.exception.BaseRestException;

/**
 * ClassName:OpenIdInfoHanderImpl <br>
 * Function: openIdInfoHander实现类. <br>
 * Reason: TODO ADD REASON. <br>
 * Date: 2015年10月22日 下午3:04:36 <br>
 *
 * @author zhiyong.fan
 * @version
 * @see
 */
@Component("openIdInfoHandler")
public class OpenIdInfoHandlerImpl implements OpenIdInfoHandler {

    @Override
    public int insert(OpenIdInfo openIdInfo) {
        int result = openIdInfoMapper.insert(openIdInfo);
        if (result == 1) {
            RedisMapper.setOpenIdInfo(openIdInfo);
        }
        return result;
    }

    @Autowired
    private OpenIdInfoMapper openIdInfoMapper;

    @Override
    public OpenIdInfo update(OpenIdInfo openIdInfo) {
        int result = openIdInfoMapper.update(openIdInfo);
        if (result == 1) {
            OpenIdInfo dbOpenIdInfo = openIdInfoMapper.queryOneByOpenId(openIdInfo.getOpenId());
            if (dbOpenIdInfo != null) {
                RedisMapper.setOpenIdInfo(dbOpenIdInfo);
                // 返回更新后的openIdInfo
                openIdInfo = dbOpenIdInfo;
            }
        }
        return openIdInfo;
    }

    @Override
    public int updateStatus(String openId, OtherID otherId, Status status) {
        int result = openIdInfoMapper.updateStatus(openId, otherId, status);
        if (result == 1) {
            OpenIdInfo openIdInfo = openIdInfoMapper.queryOneByOpenId(openId);
            if (openIdInfo != null)
                RedisMapper.setOpenIdInfo(openIdInfo);
        }
        return result;
    }

    @Override
    public int updateBindingStatus(String openId, OtherID otherId, BindingStatus bindingStatus) {
        int result = openIdInfoMapper.updateBindingStatus(openId, otherId, bindingStatus);
        if (result == 1) {
            OpenIdInfo openIdInfo = openIdInfoMapper.queryOneByOpenId(openId);
            if (openIdInfo != null)
                RedisMapper.setOpenIdInfo(openIdInfo);
        }
        return result;
    }

    @Override
    public int updateCinemaFavorites(String openId, OtherID otherId, String cinemaFavorites) {
        int result = openIdInfoMapper.updateCinemaFavorites(openId, otherId, cinemaFavorites);
        if (result == 1) {
            OpenIdInfo openIdInfo = openIdInfoMapper.queryOneByOpenId(openId);
            if (openIdInfo != null)
                RedisMapper.setOpenIdInfo(openIdInfo);
        }
        return result;
    }

    /**
     * 根据openId得到openInfo
     * 
     * @param openId
     * @return
     *
     */
    @Override
    public OpenIdInfo getOpenIdInfoByOpenId(String openId) throws BaseRestException {
        OpenIdInfo openIdInfo = RedisMapper.getOpenIdInfo(openId);
        if (null == openIdInfo) {
            openIdInfo = openIdInfoMapper.queryOneByOpenId(openId);
            if (null != openIdInfo) {
                RedisMapper.setOpenIdInfo(openIdInfo);
            }
        }
        return openIdInfo;
    }

    @Override
    @Deprecated
    public List<OpenId> getBlackList() {
        // TODO Auto-generated method stub
        return null;
    }
}
