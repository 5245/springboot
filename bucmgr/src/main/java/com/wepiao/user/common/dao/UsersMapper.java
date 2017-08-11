package com.wepiao.user.common.dao;

import com.wepiao.user.common.entry.Users;
import com.wepiao.user.common.entry.enumeration.Status;

public interface UsersMapper {
    public int insert(Users user);

    public int update(Users user);

    public int delete(int uid);
    
    public int updatePwd(int uid, String password);

    public int updateStatus(int uid, Status status);

    public int updateCinemaFavorites(int uid, String cinemaFavorites);

    public Users queryOneByUid(int uid);

    @Deprecated
    public Users queryOneByMobileNo(String mobileNo);
}
