/**
 * Project Name:uc <br>
 * File Name:OpenIdInfoHandler.java <br>
 * Package Name:com.wepiao.admin.user.service.handler <br>
 * Date:2015年10月22日上午11:25:05
 *
 */

package com.wepiao.admin.user.service.handler;

import java.util.List;

import com.wepiao.user.common.entry.OpenId;
import com.wepiao.user.common.entry.OpenIdInfo;
import com.wepiao.user.common.entry.enumeration.BindingStatus;
import com.wepiao.user.common.entry.enumeration.OtherID;
import com.wepiao.user.common.entry.enumeration.Status;
import com.wepiao.user.common.rest.exception.BaseRestException;

/**
 * ClassName:OpenIdInfoHandler
 * Function: Data Model-openID的处理类，用于处理其信息在Redis和DB间的同步
 * Reason: TODO ADD REASON.
 * Date: 2015年10月22日 上午11:25:05
 *
 * @author zhiyong.fan
 * @version
 * @see
 */
public interface OpenIdInfoHandler {

    /**
     * insert: 取消使用该方法，因为在第三方用户注册接口有可能
     * 有恶意第三方注册导致Redis内存被写满
     * @param openIdInfo
     * @return
     */
    public int insert(OpenIdInfo openIdInfo);

    /**
     *
     * update:更新DB里的用户信息同时会维护redis里面的相应信息(如果redis里面存在). <br>
     *
     * @param openIdInfo 待更新的openIdInfo信息
     * @return 更新后的OpenIdInfo信息
     */
    public OpenIdInfo update(OpenIdInfo openIdInfo);

    /**
     *
     * updateStatus:根据openId和otherId更新用户状态. <br>
     *
     * @param openId 第三方UID（唯一）
     * @param otherId 第三方平台的编号ID
     * @param status 用户状态
     * @return 更新成功的记录数
     */
    public int updateStatus(String openId, OtherID otherId, Status status);

    /**
     *
     * updateBindingStatus:根据openId和otherId更新用户绑定状态. <br>
     *
     * @param openId 第三方UID（唯一）
     * @param otherId 第三方平台的编号ID
     * @param status 用户绑定状态
     * @return 更新成功的记录数
     */
    public int updateBindingStatus(String openId, OtherID otherId, BindingStatus status);

    /**
     *
     * updateCinemaFavorites:根据openId和otherId更新用户喜欢的影片信息. <br>
     *
     * @param openId 第三方UID（唯一）
     * @param otherId 第三方平台的编号ID
     * @param cinemaFavorites 用户喜欢的影片信息
     * @return 更新成功的记录数
     */
    public int updateCinemaFavorites(String openId, OtherID otherId, String cinemaFavorites);

    /**
     *
     * getOpenIdInfoByOpenId:. <br>
     *
     * @param openId 第三方UID
     * @param otherId 第三方平台的编号ID
     * @return
     * @throws BaseRestException
     */
    public OpenIdInfo getOpenIdInfoByOpenId(String openId) throws BaseRestException;

    /**
     *
     * getBlackList:获取黑名单信息. <br>
     *
     * @return 所有的黑名单信息集合
     */
    @Deprecated
    public List<OpenId> getBlackList();

}
