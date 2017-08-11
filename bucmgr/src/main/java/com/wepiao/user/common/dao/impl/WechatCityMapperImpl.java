package com.wepiao.user.common.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.wepiao.user.common.base.dao.SqlSessionMutilSourceDaoSupport;
import com.wepiao.user.common.dao.WechatCityMapper;
import com.wepiao.user.common.entry.WechatCity;

@Repository
public class WechatCityMapperImpl implements WechatCityMapper {
    private Logger                          logger    = LoggerFactory.getLogger(getClass());
    private String                          namespace = "com.wepiao.user.common.dao.WechatCityMapper";

    @Resource
    private SqlSessionMutilSourceDaoSupport daoSupport;

    @Override
    public int insert(WechatCity wechatCity) {
        SqlSession session = daoSupport.getSqlSessionsIdBackup();
        int affectRows = session.insert(namespace + ".insert", wechatCity);
        return affectRows;
    }

    @Override
    public List<WechatCity> queryAll() {
        SqlSession session = daoSupport.getSqlSessionsIdBackup();
        return session.selectList(namespace + ".queryAll");
    }

}
