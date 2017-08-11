package com.wepiao.user.common.dao;

import java.util.List;

import com.wepiao.user.common.entry.VipCardOpsHistory;

public interface VipCardOpsHistoryMapper {

    public int insert(VipCardOpsHistory vipCardOpsHistory);

    /**
     * 通过memberId查询操作历史记录
     * @param memberId
     * @return
     *
     */
    public List<VipCardOpsHistory> queryOpsHistoryByMemberId(String memberId);

}
