package com.wepiao.admin.user.service;

import com.wepiao.admin.user.rest.msg.CinemaFavoriteAddReq;
import com.wepiao.admin.user.rest.msg.CinemaFavoriteListReq;
import com.wepiao.admin.user.rest.msg.CinemaFavoriteListRes;
import com.wepiao.admin.user.rest.msg.CinemaFavoriteRemoveReq;
import com.wepiao.admin.user.rest.msg.SingleResultRes;
import com.wepiao.user.common.rest.exception.BaseRestException;

public interface CinemaFavoriteService {

    public SingleResultRes addCinemaToFavorites(CinemaFavoriteAddReq req) throws BaseRestException;

    public SingleResultRes removeCinemaFromFavorites(CinemaFavoriteRemoveReq req) throws BaseRestException;

    public CinemaFavoriteListRes getCinemaFavoritesByOpenId(CinemaFavoriteListReq req) throws BaseRestException;
}
