package com.wepiao.admin.user.service.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import redis.clients.jedis.exceptions.JedisConnectionException;

import com.wepiao.admin.user.rest.msg.CinemaFavoriteAddReq;
import com.wepiao.admin.user.rest.msg.CinemaFavoriteListReq;
import com.wepiao.admin.user.rest.msg.CinemaFavoriteListRes;
import com.wepiao.admin.user.rest.msg.CinemaFavoriteRemoveReq;
import com.wepiao.admin.user.rest.msg.SingleResultRes;
import com.wepiao.admin.user.service.CinemaFavoriteService;
import com.wepiao.admin.user.service.handler.OpenIdInfoHandler;
import com.wepiao.user.common.constant.LogMsg;
import com.wepiao.user.common.entry.IdRelationNode;
import com.wepiao.user.common.entry.OpenIdInfo;
import com.wepiao.user.common.entry.enumeration.OtherID;
import com.wepiao.user.common.enumeration.ResponseInfoEnum;
import com.wepiao.user.common.handler.IdRelationHandler;
import com.wepiao.user.common.rest.exception.BaseRestException;
import com.wepiao.user.common.service.IdRelationService;
import com.wepiao.user.common.util.LogMessageFormatter;

@Service
public class CinemaFavoriteServiceImpl implements CinemaFavoriteService {

    private static final Logger logger                      = LoggerFactory.getLogger(CinemaFavoriteServiceImpl.class);

    @Autowired
    private OpenIdInfoHandler   openIdInfoHandler;

    @Autowired
    private IdRelationHandler   idRelationHandler;

    @Autowired
    private IdRelationService   idRelationService;
    /**
     * 最大允许影院ID收藏数
     */
    private static final int    MAX_CINEMA_FAVORITES_NUMBER = 10;

    @Override
    public SingleResultRes addCinemaToFavorites(CinemaFavoriteAddReq req) throws BaseRestException {
        SingleResultRes res = null;
        int result = 0;
        try {
            OpenIdInfo openIdInfo = openIdInfoHandler.getOpenIdInfoByOpenId(req.getOpenID());
            if (openIdInfo == null) {
                logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMessageFormatter.format(LogMsg.OPEN_ID_NOT_FOUND_DETAIL, req.getOpenID()));
                throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10005);
            } else {
                result = addCinemaFavorites(openIdInfo, req);
            }
            res = new SingleResultRes(result);
        } catch (IllegalArgumentException ie) {
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10002, ie.getMessage());
        } catch (BaseRestException be) {
            throw be;
        } catch (JedisConnectionException je) {
            logger.error(LogMsg.BASE_MSG, req.getRequestId(), je.getMessage());
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E50002);
        } catch (DataAccessException me) {
            logger.error(LogMsg.BASE_MSG, req.getRequestId(), me.getMessage());
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E50001);
        }
        return res;
    }

    @Override
    public SingleResultRes removeCinemaFromFavorites(CinemaFavoriteRemoveReq req) throws BaseRestException {
        SingleResultRes res = null;
        int result = 0;
        try {
            OpenIdInfo openIdInfo = openIdInfoHandler.getOpenIdInfoByOpenId(req.getOpenID());
            if (openIdInfo == null) {
                logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMessageFormatter.format(LogMsg.OPEN_ID_NOT_FOUND_DETAIL, req.getOpenID()));
                throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10005);
            } else {
                result = removeCinemaFavorites(openIdInfo, req);
            }
            res = new SingleResultRes(result);
        } catch (IllegalArgumentException ie) {
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10002, ie.getMessage());
        } catch (BaseRestException be) {
            throw be;
        } catch (JedisConnectionException je) {
            logger.error(LogMsg.BASE_MSG, req.getRequestId(), je.getMessage());
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E50002);
        } catch (DataAccessException me) {
            logger.error(LogMsg.BASE_MSG, req.getRequestId(), me.getMessage());
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E50001);
        }
        return res;
    }

    @Override
    public CinemaFavoriteListRes getCinemaFavoritesByOpenId(CinemaFavoriteListReq req) throws BaseRestException {
        CinemaFavoriteListRes res = null;
        try {
            OpenIdInfo openIdInfo = openIdInfoHandler.getOpenIdInfoByOpenId(req.getOpenID());
            if (openIdInfo == null) {
                logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMessageFormatter.format(LogMsg.OPEN_ID_NOT_FOUND_DETAIL, req.getOpenID()));
                throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10005);
            } else {
                res = new CinemaFavoriteListRes();
                HashSet<String> cinemaSet = getCinemaFavorites(openIdInfo, req.getRequestId());
                res.addCinemaFavoriteHashSet(cinemaSet);
            }
        } catch (IllegalArgumentException ie) {
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10002, ie.getMessage());
        } catch (BaseRestException be) {
            throw be;
        } catch (JedisConnectionException je) {
            logger.error(LogMsg.BASE_MSG, req.getRequestId(), je.getMessage());
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E50002);
        } catch (DataAccessException me) {
            logger.error(LogMsg.BASE_MSG, req.getRequestId(), me.getMessage());
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E50001);
        }
        return res;
    }

    /**
     * 添加影院收藏
     * @param openIdInfo
     * @param req
     * @return
     */
    private int addCinemaFavorites(OpenIdInfo openIdInfo, CinemaFavoriteAddReq req) {
        int result = 0;
        String cinemaFavorites = openIdInfo.getCinemaFavorites();
        // 检查cinema是否重复或超过最大数限制
        checkCinemaId(cinemaFavorites, req);
        if (cinemaFavorites == null || cinemaFavorites.length() == 0) {
            cinemaFavorites = "" + req.getCinemaID();
        } else {
            cinemaFavorites += ("," + req.getCinemaID());
        }
        result = (openIdInfoHandler.updateCinemaFavorites(openIdInfo.getOpenId(), openIdInfo.getOtherId(), cinemaFavorites) == 1) ? 0 : 1;
        return result;
    }

    /**
     * 移除影院收藏
     * @param openIdInfo
     * @param req
     * @return
     */
    private int removeCinemaFavorites(OpenIdInfo openIdInfo, CinemaFavoriteRemoveReq req) {
        int result = 0;
        String cinemaFavorites = openIdInfo.getCinemaFavorites();
        cinemaFavorites = removeCinemaFromFavorites(cinemaFavorites, req);
        result = (openIdInfoHandler.updateCinemaFavorites(openIdInfo.getOpenId(), openIdInfo.getOtherId(), cinemaFavorites) == 1) ? 0 : 1;
        return result;
    }

    /**
     * 获取所有的OpenId下的影院收藏并做并集
     * @param openIdInfo
     * @return
     */
    private HashSet<String> getCinemaFavorites(OpenIdInfo openIdInfo, String reqId) {
        String cinemaFavorites = openIdInfo.getCinemaFavorites();
        IdRelationNode childNode = new IdRelationNode(openIdInfo.getOpenId(), openIdInfo.getOtherId());
        IdRelationNode rootNode = idRelationHandler.getRootNode(childNode);
        List<IdRelationNode> nodeList = idRelationService.getIdListFromRoot(rootNode);
        HashSet<String> cinemaSet = new HashSet<String>();
        if (nodeList.size() > 1) {
            for (IdRelationNode node : nodeList) {
                if (OtherID.UID != node.getIdType() && !node.getId().equals(openIdInfo.getOpenId())) {
                    OpenIdInfo otherOpenIdInfo = openIdInfoHandler.getOpenIdInfoByOpenId(node.getId());
                    if (null != otherOpenIdInfo && null != otherOpenIdInfo.getCinemaFavorites()) {
                        String[] cinemaArray = otherOpenIdInfo.getCinemaFavorites().split(",");
                        CollectionUtils.addAll(cinemaSet, cinemaArray);
                    }
                }
            }
        }
        if (null != cinemaFavorites) {
            String[] cinemaArray = cinemaFavorites.split(",");
            CollectionUtils.addAll(cinemaSet, cinemaArray);
        }
        return cinemaSet;
    }

    /**
     * 检查影院id是否重复，是否超过最大收藏数
     * @param cinemaFavorites 原始的 favorite cinema
     * @param req 添加影院收藏的请求，包含requestId和影院id
     * @return true 如果检查通过
     */
    private boolean checkCinemaId(String cinemaFavorites, CinemaFavoriteAddReq req) throws BaseRestException {
        boolean result = true;
        if (cinemaFavorites != null) {
            HashSet<String> cinemaSet = new HashSet<String>();
            String[] cinemaArray = cinemaFavorites.split(",");
            // 超限
            if (cinemaArray.length + 1 > MAX_CINEMA_FAVORITES_NUMBER) {
                logger.warn(LogMsg.CINEMA_ID_NUM_EXCEED, req.getRequestId(), MAX_CINEMA_FAVORITES_NUMBER);
                throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E20005, Integer.toString(MAX_CINEMA_FAVORITES_NUMBER));
            }
            CollectionUtils.addAll(cinemaSet, cinemaArray);
            // id 重复
            if (cinemaSet.contains(Integer.toString(req.getCinemaID()))) {
                result = false;
                logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMessageFormatter.format(LogMsg.DUPLICATED_CINEMA_ID_DETAIL, req.getCinemaID()));
                throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10008, LogMsg.DUPLICATED_CINEMA_ID);
            }
        }
        return result;
    }

    /**
     * 移除影院id
     * @param cinemaFavorites 原始的 favorite cinema
     * @param req 移除请求，包含requestId和影院id
     * @return 移除影院id后的favorite cinema
     */
    private String removeCinemaFromFavorites(String cinemaFavorites, CinemaFavoriteRemoveReq req) throws BaseRestException {
        String newCinemaFavorites = null;
        if (cinemaFavorites == null) {
            logger.error(LogMsg.BASE_MSG, req.getRequestId(), LogMsg.CINEMA_LIST_EMPTY);
            throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10005);
        } else {
            HashSet<String> cinemaSet = new HashSet<String>();
            String[] cinemaArray = cinemaFavorites.split(",");
            CollectionUtils.addAll(cinemaSet, cinemaArray);
            // 无此影院id
            if (!cinemaSet.remove(Integer.toString(req.getCinemaID()))) {
                logger.warn(LogMsg.BASE_MSG, req.getRequestId(), LogMessageFormatter.format(LogMsg.CINEMA_ID_NOT_SAVED_DETAIL, req.getCinemaID()));
                throw new BaseRestException(req.getRequestId(), ResponseInfoEnum.E10005);
            }
            Iterator<String> i = cinemaSet.iterator();
            while (i.hasNext()) {
                if (newCinemaFavorites == null) {
                    newCinemaFavorites = "";
                }
                newCinemaFavorites += i.next() + ",";
            }
            if (newCinemaFavorites != null) {
                newCinemaFavorites = newCinemaFavorites.substring(0, newCinemaFavorites.length() - 1);
            }
        }
        return newCinemaFavorites;
    }
}
