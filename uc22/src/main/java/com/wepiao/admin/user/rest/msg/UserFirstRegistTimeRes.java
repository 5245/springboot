/**
 * Project Name:uc
 * File Name:UserFirstRegistTimeRes.java
 * Package Name:com.wepiao.admin.user.rest.msg
 * Date:2016年3月25日上午11:36:21
 *
*/

package com.wepiao.admin.user.rest.msg;

/**
 * ClassName:UserFirstRegistTimeRes <br/>
 * Function: 用户首次注册时间返回结构 <br/>
 * Date:     2016年3月25日 上午11:36:21 <br/>
 * @author   liujie
 * @version  
 * @see 	 
 */
public class UserFirstRegistTimeRes extends BaseRes {

    private Integer registTime;

    public Integer getRegistTime() {
        return registTime;
    }

    public void setRegistTime(Integer registTime) {
        this.registTime = registTime;
    }

    public UserFirstRegistTimeRes() {
        super();
    }

    public UserFirstRegistTimeRes(Integer registTime) {
        super();
        this.registTime = registTime;
    }

}
