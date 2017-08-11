//package com.wepiao.user.common.dao.impl;
//
//import com.wepiao.user.common.base.dao.SqlSessionMutilSourceDaoSupport;
//import com.wepiao.user.common.constant.LogMsg;
//import com.wepiao.user.common.dao.Yp2GwrIdMapMapper;
//import com.wepiao.user.common.entry.Yp2GwrIdMap;
//import org.apache.ibatis.session.SqlSession;
//import com.alibaba.fastjson.JSON;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Repository;
//
//import javax.annotation.Resource;
//import java.util.HashMap;
//import java.util.Map;
//
//@Repository
//public class Yp2GwrIdMapMapperImpl implements Yp2GwrIdMapMapper {
//    private Logger                          logger    = LoggerFactory.getLogger(getClass());
//    private String                          namespace = "com.wepiao.user.common.dao.Yp2GwrIdMapMapper";
//
//    @Resource
//    private SqlSessionMutilSourceDaoSupport daoSupport;
//
//    @Override
//    public int insert(Yp2GwrIdMap yp2GwrIdMap) {
//        Integer memberId = yp2GwrIdMap.getMemberId();
//        yp2GwrIdMap.setTableIndex(daoSupport.getTableIndex(memberId));
//        SqlSession session = daoSupport.getSqlSession(memberId);
//        int affectRows = session.insert(namespace + ".insert", yp2GwrIdMap);
//        logger.info(LogMsg.INSERT_DB_FOR_YUPIAO2GEWARA_IDMAP, JSON.toJSONString(yp2GwrIdMap));
//        return affectRows;
//    }
//
//    @Override
//    public Yp2GwrIdMap queryOne(Integer memberId) {
//        int tableIndex = daoSupport.getTableIndex(memberId);
//        Map<String, Object> memberIdMap = new HashMap<String, Object>();
//        memberIdMap.put("memberId", memberId);
//        memberIdMap.put("tableIndex", tableIndex);
//        SqlSession session = daoSupport.getSqlSession(memberId);
//        return (Yp2GwrIdMap) session.selectOne(namespace + ".queryOne", memberIdMap);
//    }
//}
