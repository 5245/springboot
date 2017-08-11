package com.wepiao.user.common.dao;

import java.util.List;

import com.wepiao.user.common.entry.PrivateVipConfig;

public interface PrivateVipConfigMapper {

    public int insert(PrivateVipConfig privateVipConfig);

    public int update(PrivateVipConfig privateVipConfig);

    public PrivateVipConfig queryOneByVipId(Integer vipId);

    public List<PrivateVipConfig> queryAllPrivateVipConfig();

}
