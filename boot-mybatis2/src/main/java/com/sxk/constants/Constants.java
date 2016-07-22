package com.sxk.constants;

/**
 * 常量
 * @author Jin Song
 *
 */
public class Constants {
    /**
     * uid转换成openid的前缀
     */
    public static final String PREFIX_UID2OPENID_CONVERSION     = "weiying_";

    /**
     * 不存在的uid
     */
    public static final int    NOT_EXISTED_UID                  = -1;

    /**
     * Rest API的缩短的请求ID
     */
    public static final String SHORT_REQ_ID                     = "requestId";

    /**
     * 用户设备的IDFA
     */
    public static final String USER_IDFA                        = "X-User-IDFA";

    /**
     * 用户设备的IMEI
     */
    public static final String USER_IMEI                        = "X-User-IMEI";

    /**
     * 用户设备的MAC
     */
    public static final String USER_MAC                         = "X-User-MAC";

    /**
     * 用户IP
     */
    public static final String USER_IP                          = "User-Ip";

    /**
     * 第三方授权登录时由Http Header携带的Oauth token
     */
    public static final String ACCESS_TOKEN                     = "Access-Token";

    /**
     * 第三方授权登录时由Http Header携带的Oauth Consumer Key(仅仅QQ使用)
     */
    public static final String OAUTH_CONSUMER_KEY               = "Oauth-Consumer-Key";

    /**
     * extUid的长度(对应演出票的passport_user_id)
     */
    public static final int    LEN_OF_EXT_UID                   = 32;

    /**
     * userReceiverMobile表中每个(id,idtype,serviceType)可以存储的手机号的最大条数
     */
    public static final int    MAX_SIZE                         = 5;

    /**
     * 和userInfo相关业务的日志
     */
    public static final String LOG_USER_INFO                    = "api_user_info";
    /**
     * 和userTag相关业务的日志
     */
    public static final String LOG_USER_TAG                     = "api_user_tag";
    /**
     * 和安全保护相关的日志
     */
    public static final String LOG_SECURITY                     = "security";

    /**
     * 登录失败多次导致锁定用户的阈值
     */
    public static final int    LOGIN_FAIL_LOCK_COUNT            = 10;

    /**
     * 修改或者重置密码的多次导致锁定用户的阈值
     */
    public static final int    UPDATE_PW_LOCK_COUNT             = 10;

    /**
     * 修改密码操作类型
     */
    public static final int    CHANGE_PW_OP_TYPE                = 0;

    /**
     * 重置密码操作类型
     */
    public static final int    RESET_PW_OP_TYPE                 = 1;

    /**
     * 初始化密码操作类型
     */
    public static final int    INIT_PW_OP_TYPE                  = 2;

    /* 微影专用天御密钥 */
    public static final String TIANYU_SECRET_ID                 = "AKIDtXjS6xGAUO1f2TcJvLOV8F55u15AFUFC";
    public static final String TIANYU_SECRET_KEY                = "tBDakEokUDdNTyoJZ78CESDo5BNWmSRP";

    //======================config属性文件中配置的常量==========================
    /**
     * 修改手机号的时限范围
     */
    public static final String UPDATE_MOBILE_LIMIT_TIME_SCOPE   = "update.mobile.limit.time.scope";
    /**
     * 修改手机号在时限范围内的允许次数
     */
    public static final String UPDATE_MOBILE_LIMIT_COUNT        = "update.mobile.limit.count";
    /**
     * 绑定手机号针对memberid的时限范围
     */
    public static final String BIND_MEMBERID_LIMIT_TIME_SCOPE   = "bind.memberid.limit.time.scope";
    /**
     * 绑定手机号针对memberid在时限范围内的允许次数
     */
    public static final String BIND_MEMBERID_LIMIT_COUNT        = "bind.memberid.limit.count";
    /**
     * 绑定手机号针对openid的时限范围
     */
    public static final String BIND_OPENID_LIMIT_TIME_SCOPE     = "bind.openid.limit.time.scope";
    /**
     * 绑定手机号针对openid在时限范围内的允许次数
     */
    public static final String BIND_OPENID_LIMIT_COUNT          = "bind.openid.limit.count";
    /**
     * 解绑针对memberid的时限范围
     */
    public static final String UNBIND_MEMBERID_LIMIT_TIME_SCOPE = "unbind.memberid.limit.time.scope";
    /**
     * 解绑针对memberid在时限范围内的允许次数
     */
    public static final String UNBIND_MEMBERID_LIMIT_COUNT      = "unbind.memberid.limit.count";
    /**
     * 解绑针对openid的时限范围
     */
    public static final String UNBIND_OPENID_LIMIT_TIME_SCOPE   = "unbind.openid.limit.time.scope";
    /**
     * 解绑针对openid在时限范围内的允许次数
     */
    public static final String UNBIND_OPENID_LIMIT_COUNT        = "unbind.openid.limit.count";

}
