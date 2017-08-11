package com.wepiao.service;

public interface UserService {
    /**
     * 通过memberId得到用户基本信息
     * @param req
     * @return
     * @throws BaseRestException
     *
     */
    public void getUserByMemberId(Integer memberId);

}
