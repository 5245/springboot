/**
 * Project Name:uc-common
 * File Name:MsgMqConstants.java
 * Package Name:com.wepiao.user.common.constant
 * Date:2016年4月20日上午10:00:59
 *
*/

package com.wepiao.user.common.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:MsgMqConstants <br/>
 * Function: 消息队列相关常量 <br/>
 * Date:     2016年4月20日 上午10:00:59 <br/>
 * @author   liujie
 * @version  
 * @see 	 
 */
public class MsgMqConstants {

//====================================消息发布订阅渠道名(主题)===================================
    /**
     * 更新大数据标签字典的topic
     */
    public static final String MSG_CHANNEL_UPDATE_TAG_DICT = "mqpub:update_bd_tag_dict";
    /**
     * uc上报本地队列大小的topic
     */
    public static final String MSG_CHANNEL_REPORT_QUEUE_SIZE = "mqpub:report_queue_size";

//====================================消息事件类型======================================
    /**
     * 消息类型:用户绑定
     */
    public static final String MSG_EVENT_BIND = "mobile_bind";
    /**
     * 消息类型:用户解绑
     */
    public static final String MSG_EVENT_UNBIND = "mobile_unbind";
    /**
     * 消息类型:openId注册
     */
    public static final String MSG_EVENT_OPENID_REGIST = "openid_regist";
    /**
     * 消息类型:手机号注册
     */
    public static final String MSG_EVENT_MOBILE_REGIST = "mobile_regist";
    /**
     * 消息类型:手机号更改
     */
    public static final String MSG_EVENT_MOBILE_UPDATE = "mobile_update";
    
    /**
     * 消息类型:手机号更改
     */
    public static final String MSG_EVENT_USERINFO_UPDATE = "userinfo_update";
    /**
     * 消息类型:更新大数据标签字典消息事件
     */
    public static final String MSG_EVENT_UPDATE_BIG_DATA_TAG_DICT = "update_bd_tag_dict";
    
//====================================是否发送消息开关配置名称=====================================
    /**
     * 是否发送消息开关
     */
    public static final String MSG_SEND_OFFSET = "user.mq.send.msg";
    /**
     * 是否接收消息开关
     */
    public static final String MSG_RECEIVE_OFFSET = "user.mq.receive.msg";
    /**
     * 开关 关闭
     */
    public static final int OFFSET_OFF = 0;
    /**
     * 开关 打开
     */
    public static final int OFFSET_ON = 1;
    
//====================================各接收模块消息队列名称======================================
    /**
     * 标签接收消息的队列
     */
    public static final String MSG_RCV_USER_TAG = "mq:msg_rcv_user_tag";
    /**
     * 用户中心接收消息的队列
     */
    public static final String MSG_RCV_USER_CENTER = "mq:msg_rcv_user_center";
    /**
     * 用户中心微服务接收消息的队列
     */
    public static final String MSG_RCV_MICRO_SERVICE = "mq:msg_rcv_micro_service";
    
    /**
     * 获取消息时的等待时间,单位秒
     */
    public static final int BLOCK_TIME = 30;
    
    /**
     * 消息类型与接收消息的队列主题列表
     */
    public static final Map<String,List<String>> MSG_MAP = new HashMap<String,List<String>>();
    
    /**
     * 存放所有消息队列key
     */
    public static final List<String> MSG_RCV_QUEUE_KEY_LIST = new ArrayList<String>();
    
    static{
        //定义各消息队列推送的事件类型
        List<String> msgEventBindList = new ArrayList<String>();
        msgEventBindList.add(MSG_RCV_USER_TAG);
        msgEventBindList.add(MSG_RCV_MICRO_SERVICE);
//        msgEventBindList.add(MSG_RCV_USER_CENTER);
        MSG_MAP.put(MSG_EVENT_BIND, msgEventBindList);
        
        List<String> msgEventUnBindList = new ArrayList<String>();
        msgEventUnBindList.add(MSG_RCV_USER_TAG);
//        msgEventUnBindList.add(MSG_RCV_USER_CENTER);
        MSG_MAP.put(MSG_EVENT_UNBIND, msgEventUnBindList);
        
        List<String> msgEventOpenIdRegistList = new ArrayList<String>();
        msgEventOpenIdRegistList.add(MSG_RCV_USER_TAG);
        MSG_MAP.put(MSG_EVENT_OPENID_REGIST, msgEventOpenIdRegistList);
        
//        List<String> msgEventMobileUpdateList = new ArrayList<String>();
//        msgEventMobileUpdateList.add(MSG_RCV_USER_CENTER);
//        MSG_MAP.put(MSG_EVENT_MOBILE_UPDATE, msgEventMobileUpdateList);
//        
//        List<String> msgEventMobileRegistList = new ArrayList<String>();
//        msgEventMobileRegistList.add(MSG_RCV_USER_CENTER);
//        MSG_MAP.put(MSG_EVENT_MOBILE_REGIST, msgEventMobileRegistList);
        
        
        List<String> msgEventUserInfoUpdateList = new ArrayList<String>();
        msgEventUserInfoUpdateList.add(MSG_RCV_USER_CENTER);
        MSG_MAP.put(MSG_EVENT_USERINFO_UPDATE, msgEventUserInfoUpdateList);
        
        //更新大数据标签字典消息事件
        List<String> msgEventUpdateBdTagDictList = new ArrayList<String>();
        msgEventUpdateBdTagDictList.add(MSG_RCV_USER_TAG);
        MSG_MAP.put(MSG_EVENT_UPDATE_BIG_DATA_TAG_DICT, msgEventUpdateBdTagDictList);
    }

}
