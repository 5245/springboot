package com.wepiao.user.common.redis;

/**
 * Redis Key constant
 * @author jin song
 *
 */
public class RedisKey {

    /**
     * 用于Redis Key模板的通用分隔符
     */
    public static final String COMMON_SEPARATOR                         = ":";

    /**
     * 用户静态标签的key模板,key前缀为utag:；
     * 用户静态标签Redis使用Hash类型，key存储id+idType，field存储tag, value存储tagVal+updatetime
     */
    public static final String USER_TAG_KEY                             = "utag" + COMMON_SEPARATOR + "%s" + COMMON_SEPARATOR + "%s";
    public static final String USER_TAG_VALUE                           = "%s" + COMMON_SEPARATOR + "%s";

    /**
     * 用户动态标签的key模板,key前缀为dtag:；
     * 用户动态标签Redis使用Hash类型，key存储id+idType，field存储tag, value存储tagVal+updatetime
     */
    public static final String USER_DYNAMIC_TAG_KEY                     = "dtag" + COMMON_SEPARATOR + "%s" + COMMON_SEPARATOR + "%s";
    public static final String USER_DYNAMIC_TAG_VALUE                   = "%s" + COMMON_SEPARATOR + "%s";

    /**
     * 映射关系表‘用户手机号-用户’缓存的key，格式为mp@uid:{用户手机号}
     */
    public static final String MOBILE_PHONE_NO_KEY                      = "mp@uid" + COMMON_SEPARATOR + "%s";

    /**
     * 映射关系表‘第三方用户-用户’缓存的key，格式为oid@uid:{第三方用户的openid}:{第三方用户的otherId}
     */
    public static final String THIRD_PARTY_OID_KEY                      = "oid@uid" + COMMON_SEPARATOR + "%s" + COMMON_SEPARATOR + "%s";

    /**
     * 用户关系的key模板,格式为idrlat:%s:%s使用子结点的id替换；
     * 用户关系的Redis使用Hash类型，key存储子节点的id，field存储父节点的id+idType,
     * value 置为插入时刻的时间戳(单位S)
     */
    public static final String ID_RELATION_KEY                          = "idrlat" + COMMON_SEPARATOR + "%s";
    public static final String ID_RELATION_FIELD                        = "%s" + COMMON_SEPARATOR + "%s";

    /**
     * 用户关系的key模板,格式为idrlat-h:%s:%s使用父结点的id替换；
     * 用户关系的Redis使用Hash类型，key存储父节点的id，field存储子节点的id+idType,
     * value 置为bindingStauts+Update时刻的时间戳(单位S)
     */
    public static final String ID_RELATION_HISTORY_KEY                  = "idrlat-h" + COMMON_SEPARATOR + "%s";
    public static final String ID_RELATION_HISTORY_FIELD                = "%s" + COMMON_SEPARATOR + "%s";
    public static final String ID_RELATION_HISTORY_VALUE                = "%s" + COMMON_SEPARATOR + "%s";

    /**
     * 用户表key,user_uid,key=uid
     */
    public static final String USER_KEY                                 = "user" + COMMON_SEPARATOR + "%s";

    /**
     * openid表key,openid_openid,key=openid
     */
    public static final String OPENID_KEY                               = "openid" + COMMON_SEPARATOR + "%s";

    /**
     * 用户最近一次收货手机号缓存(string); 
     * key:   openid+servicetype, 
     * value: mobile+isVerified
     */
    public static final String USER_LATEST_RECEIVER_MOBILE_KEY          = "urecv-m" + COMMON_SEPARATOR + "%s" + COMMON_SEPARATOR + "%s";
    public static final String USER_LATEST_RECEIVER_MOBILE_VALUE        = "%s" + COMMON_SEPARATOR + "%s";

    /**
     * openid2用户收货手机号缓存(Hash); 
     * key:   openid+servicetype, 
     * field: receiver mobile
     * value: isVerified
     */
    public static final String OPENID_2_USER_RECEIVER_MOBILE_KEY        = "o2recv-m" + COMMON_SEPARATOR + "%s" + COMMON_SEPARATOR + "%s";

    /**
     * 用户收货手机号2openid缓存(Hash); 
     * key:   receiver mobile, 
     * field: openid,
     * value: ""
     */
    public static final String USER_RECEIVER_MOBILE_2_OPENID_KEY        = "recv-m2o" + COMMON_SEPARATOR + "%s";

    /**
     * 新用户中心openId2guid缓存(String类型); 
     * key:   openid 
     * value: guid
     */
    public static final String OPENID_2_GUID_KEY                        = "o2guid" + COMMON_SEPARATOR + "%s";

    /**
     * 新用户中心guid2openId缓存(hash类型); 
     * key:   guid 
     * field: openid
     * value: createTime
     */
    public static final String GUID_2_OPENID_KEY                        = "guid2o" + COMMON_SEPARATOR + "%s";

    /**
     * 新用户中心mobileNo2guid缓存(String类型); 
     * key:   mobileNo 
     * value: guid
     */
    public static final String MOBILE_2_GUID_KEY                        = "m2guid" + COMMON_SEPARATOR + "%s";

    /**
     * 新用户中心guid2mobileNo缓存(String类型); 
     * key:   guid
     * value: mobileNo + isMember
     */
    public static final String GUID_2_MOBILE_KEY                        = "guid2m" + COMMON_SEPARATOR + "%s";
    public static final String GUID_2_MOBILE_VALUE                      = "%s" + COMMON_SEPARATOR + "%s";

    /**
     * 用户收货地址列表key
     */
    public static final String USER_ADDR_KEY                            = "uaddrs" + COMMON_SEPARATOR + "%s";
    /**
     * 用户默认收货地址key
     */
    public static final String USER_ADDR_DEFAULT_KEY                    = "uaddr-d" + COMMON_SEPARATOR + "%s";

    /**
     * ========账号中心登录、绑定、修改密码限制key只存缓存==================
     */

    /**
     * 自有虚假手机号的缓存(String类型) 
     * key:   fake-m:<手机号>
     * value: ""
     */
    public static final String FAKE_MOBILE_KEY                          = "fake-m" + COMMON_SEPARATOR + "%s";

    /**
     * 记录用户登录频次的key,格式为logfreq-手机号
     */
    public static final String USER_LOGIN_FREQUENCY_KEY                 = "logfreq" + COMMON_SEPARATOR + "%s";

    /**
     * 记录用户修改密码或重置密码频次的key,格式为upwfreq:用户的memberId
     */
    public static final String USER_UPDATE_PW_FREQUENCY_KEY             = "upwfreq" + COMMON_SEPARATOR + "%s";

    /**
     * 受限登录的用户key,格式为limit-log:用户的手机号
     */
    public static final String USER_LOGIN_LIMITED_KEY                   = "limit-log" + COMMON_SEPARATOR + "%s";

    /**
     * 受限修改重置密码的用户key,格式为limit-upw:用户的memberId
     */
    public static final String USER_UPDATE_PW_LIMITED_KEY               = "limit-upw" + COMMON_SEPARATOR + "%s";

    /**
     * 受限修改手机号操作过多的用户key,格式为limit-upmn:用户的memberId
     */
    public static final String USER_UPDATE_MOBILE_NO_LIMITED_KEY        = "limit-upmn" + COMMON_SEPARATOR + "%s";
    /**
     * 受限openid绑定操作过多的用户key,格式为limit-oibd:用户的openId
     */
    public static final String USER_OPENID_BIND_LIMITED_KEY             = "limit-oibd" + COMMON_SEPARATOR + "%s";
    /**
     * 受限被绑定操作过多的memberId用户key,格式为limit-mibd:用户的memberId
     */
    public static final String USER_MEMBERID_BIND_LIMITED_KEY           = "limit-mibd" + COMMON_SEPARATOR + "%s";
    /**
     * 受限openid解绑操作过多的用户key,格式为limit-oiubd:用户的openId
     */
    public static final String USER_OPENID_UNBIND_LIMITED_KEY           = "limit-oiubd" + COMMON_SEPARATOR + "%s";
    /**
     * 受限被解绑操作过多的memberId用户key,格式为limit-miubd:用户的memberId
     */
    public static final String USER_MEMBERID_UNBIND_LIMITED_KEY         = "limit-miubd" + COMMON_SEPARATOR + "%s";

    /**
     * =====================大数据共享redis的标签=========================
     */
    /**
     * 大数据标签前缀
     */
    public static final String BIG_DATA_TAG_PREFIX                      = "bdt" + COMMON_SEPARATOR + "%s";
    /**
     * 大数据自增标签前缀
     */
    public static final String BIG_DATA_INCR_TAG_PREFIX                 = "bdit" + COMMON_SEPARATOR + "%s";
    /**
     * 大数据存放老用户手机号的set的key
     */
    public static final String BIG_DATA_MOBILE_SET_KEY                  = "mobile_set";
    /**
     * 大数据openId标识电影票老用户的标签的key
     */
    public static final String BIG_DATA_OLD_USER_FOR_MOVIE_KEY          = "old_user";
    /**
     * 大数据openId标识电影票老用户的标签的key
     */
    public static final String BIG_DATA_TMP_OLD_USER_FOR_MOVIE_KEY      = "tmp_old_user";
    /**
     * 大数据openId标识演出票老用户的标签的key
     */
    public static final String BIG_DATA_OLD_USER_FOR_SHOW_KEY           = "show_old_user";
    /**
     * 大数据标签字典key对应code的映射map
     */
    public static final String BIG_DATA_KEY_2_CODE_KEY                  = "bd_key_2_code";
    /**
     * 大数据标签字典code对应key的映射map
     */
    public static final String BIG_DATA_CODE_2_KEY_KEY                  = "bd_code_2_key";
    /**
     * 大数据标签字典编号对应不传染关系树的map
     */
    public static final String BIG_DATA_CODE_2_INFECT_KEY               = "bd_code_2_infect";
    /**
     * 大数据存放电影票老用户手机号的set
     */
    public static final String OLD_USER_MOBILE_FOR_MOVIE_KEY            = "mobile_set";
    /**
     * 大数据存放演出票老用户手机号的set
     */
    public static final String OLD_USER_MOBILE_FOR_SHOW_KEY             = "show_mobile_set";
    /**
     * 大数据针对2016年9月10-20日活动存放的购票用户set
     */
    public static final String IPHONE7_MARKETING                        = "iphone7_marketing";

    /**
     * 对于购买指定影片按用户openid计数的hash的key(key的格式为mov_by_openid:<openId>
     * field为<activityId>(未来可扩展activity字段), value为活动有效期截止时间，精确到s)
     *  
     */
    public static final String USER_TAG_4_MOVIE_TICKETS_CONSUMPTION_KEY = "mov_consume" + COMMON_SEPARATOR + "%s";

    /**
     * =========================以下为会员卡相关key常量========================
     */
    //会员卡信息只存缓存的数据
    /**
     * 锁定操作
     */
    public static final String VIP_CARD_OPERATE_LK_KEY                  = "lk" + COMMON_SEPARATOR + "%s";
    /**
     * 释放操作
     */
    public static final String VIP_CARD_OPERATE_RL_KEY                  = "rl" + COMMON_SEPARATOR + "%s";
    /**
     * 消费操作
     */
    public static final String VIP_CARD_OPERATE_CM_KEY                  = "cm" + COMMON_SEPARATOR + "%s";
    /**
     * 回退操作
     */
    public static final String VIP_CARD_OPERATE_RF_KEY                  = "rf" + COMMON_SEPARATOR + "%s";

    /**
     * 会员卡释放任务列表,已执行的将会被移除(hash类型)
     */
    public static final String VIP_CARD_JOB_KEY                         = "vipjob" + COMMON_SEPARATOR + "%s";

    /**
     * 会员卡在规定时限区间内已使用的次数，格式为iused:会员卡号:限定起始时间:限定结束时间
     */
    public static final String VIP_CARD_USED_INTERVAL_COUNT_KEY         = "iused" + COMMON_SEPARATOR + "%s" + COMMON_SEPARATOR + "%s"
                                                                                + COMMON_SEPARATOR + "%s";

    //会员卡信息落库的数据
    /**
     * 会员卡已使用次数，格式为tused:会员卡号
     */
    public static final String VIP_CARD_USED_TOTAL_COUNT_KEY            = "tused" + COMMON_SEPARATOR + "%s";

    /**
     * 会员卡信息，格式为card:会员卡号
     */
    public static final String VIP_CARD_INFO_KEY                        = "card" + COMMON_SEPARATOR + "%s";
    /**
     * 会员卡订单信息，格式为order:订单号
     */
    public static final String VIP_CARD_ORDER_INFO_KEY                  = "order" + COMMON_SEPARATOR + "%s";

    /**
     * 私有会员配置信息redis前缀
     */
    public static final String PRIVATE_VIP_CONFIG_PREFIX                = "pvipconf" + COMMON_SEPARATOR + "%s";

    /**
     * 本地队列大小状态监控key,存储格式为hash
     */
    public static final String LOCAL_QUEUE_STATUS                       = "local_queue_status";

    /**
     * 会员折扣卡历史信息，格式为vchistory:memberId
     */
    public static final String VIP_CARD_OPS_HISTORY_KEY                 = "vchistory" + COMMON_SEPARATOR + "%s";

    /**
     * 娱票2格瓦拉id映射
     */
    public static final String YP2GWR_IDMAP                             = "yp2gwridmap" + COMMON_SEPARATOR + "%s";
    /**
     * 格瓦拉2娱票id映射
     */
    public static final String GWR2YP_IDMAP                             = "gwr2ypidmap" + COMMON_SEPARATOR + "%s";

    /**
     * 图片上传每小时创建文件夹使用的key
     */
    public static final String UCIMG_COS_SET                            = "ucimg_cos_set";

    /**
     * 格瓦拉用户信息昵称头像缓存
     */
    public static final String GEWARA_OPENID_KEY                        = "gopenid" + COMMON_SEPARATOR + "%s";

    /**
     * 绑定手机号操作
     */
    public static final String BIND_MOBILE_NO_OPERATE_KEY               = "bd" + COMMON_SEPARATOR + "%s";
}
