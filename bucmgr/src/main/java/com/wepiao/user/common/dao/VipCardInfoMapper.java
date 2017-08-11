package com.wepiao.user.common.dao;

import java.util.Date;
import java.util.List;

import com.wepiao.user.common.entry.VipCardInfo;

public interface VipCardInfoMapper {

    public int insert(VipCardInfo VipCardInfo);

    public int delete(String cardNo);

    public int update(VipCardInfo VipCardInfo);

    public VipCardInfo queryOneByCardNo(String cardNo);

    // 更改会员卡资格总使用次数
    public int updateTotalUsed(String cardNo, int totalUsed);

    // 更改会员卡信息改变时间
    public int updateUpdateTime(String cardNo, Date updateTime);
    // 查询表中所有会员卡信息
    public List<VipCardInfo> queryAllVipCardInfo(int dbIndex,int tableIndex);

}
