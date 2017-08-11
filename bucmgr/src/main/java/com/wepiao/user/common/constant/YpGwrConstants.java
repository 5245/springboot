package com.wepiao.user.common.constant;

public class YpGwrConstants {
	
	public static final String ARG_MSG_SN = "msgSn";
	public static final String ARG_MOBILE_NO = "mobileNo";
	public static final String ARG_OLD_MOBILE_NO = "oldMobileNo";
	public static final String ARG_NEW_MOBILE_NO = "newMobileNo";
    public static final String ARG_USER_ID = "userId";
    public static final String ARG_SOURCE_MSG_SN = "srMsgsn";
    public static final String ARG_PASSWD = "passwd";
    public static final String ARG_ERR_INFO = "errInfo";
    public static final String ARG_DEVICE_CODE = "deviceCode";
    public static final String ARG_DEVICE_TYPE = "deviceType";

	public static final String OPS_TYPE_MOBILE_REGISTER = "01";
	public static final String OPS_TYPE_BIND_MOBILE = "02";
	public static final String OPS_TYPE_UPDATE_PASSWD = "03";
	public static final String OPS_TYPE_UPDATE_MOBILE_NO = "04";
	public static final String OPS_TYPE_LOGIN_PATCH_REGISTER = "05";

	public static final String MSG_CONF_SEND_MOBILE_NO_REGISTER = "rabbitmq.topic.send.mobileNoRegister";
	public static final String MSG_CONF_SEND_BIND_MOBILE_NO = "rabbitmq.topic.send.bindMobileNo";
	public static final String MSG_CONF_SEND_UPDATE_MOBILE_NO = "rabbitmq.topic.send.updateMobileNo";
	
	public static final String LOG_MSG_RECEIVE_MSG = "Receive msg:{}";
	public static final String LOG_MSG_ERROR_MSG = "Handler gwr msg ERROR,info:{}";
	public static final String LOG_MSG_WARN_MSG = "Handler gwr msg WARN,info:{}";

}
