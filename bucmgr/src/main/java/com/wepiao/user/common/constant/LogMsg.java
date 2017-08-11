package com.wepiao.user.common.constant;

/**
 * 日志消息及模板,亦用于填充异常的ErrorMessage
 * 仅用于日志消息的常量,前部均附加一个BASE_HEADER(用于标记RequestId),如WS_REQ
 * 既用于日志消息又用于填充异常的常量,用于日志消息时需自行格式化BASE_MSG
 * Note:为了兼容logwatch做结构化数据分析， 将所有的json字符串前加上#前缀做为标识
 * @author Jin Song
 *
 */
public class LogMsg {
    /** 基本消息头(携带RequestId),此常量应附加于log消息的前部 **/
    public static final String BASE_HEADER                                = "{}-requestId: {} ";

    /** 基本消息模板**/
    public static final String BASE_MSG                                   = "requestId: {} - {}";
    public static final String BASE_MSG_NO_REQ_ID                         = "Internal API Request(No RequestId): {}";

    /** debug 消息 **/
    public static final String WS_REQ_NO_REQ_ID                           = "HTTP Request Body# {}";
    public static final String WS_RES_NO_REQ_ID                           = "HTTP Response Body# {}";
    public static final String WS_REQ                                     = BASE_HEADER + WS_REQ_NO_REQ_ID;
    public static final String WS_RES                                     = BASE_HEADER + WS_RES_NO_REQ_ID;

    /** info 消息 **/
    public static final String INSERT_DB_FOR_MOBILE2UID                   = "Insert user mobile number mapping data to db# {}";
    public static final String DELETE_DB_FOR_MOBILE2UID                   = "delete user mobile number mapping data to db# {}";
    public static final String INSERT_DB_FOR_USER                         = "Insert user data to db# {}";
    public static final String UPDATE_DB_FOR_USER                         = "Update user data to db# {}";
    public static final String DELETE_DB_FOR_USER                         = "Delete user data to db# {}";
    public static final String INSERT_DB_FOR_OPENID                       = "Insert openid data to db# {}";
    public static final String UPDATE_DB_FOR_OPENID                       = "Update openid data to db# {}";
    public static final String DELETE_DB_FOR_OPENID                       = "Delete openid data from db: {}";
    public static final String INSERT_DB_FOR_USER_DELIVERY_ADDR           = "Insert user delivery address data to db# {}";
    public static final String UPDATE_DB_FOR_USER_DELIVERY_ADDR           = "Update user delivery address data to db# {}";
    public static final String DELETE_DB_FOR_USER_DELIVERY_ADDR           = "Delete user delivery address data from db: {}";
    public static final String INSERT_REDIS_FOR_USER_TAG                  = "Insert user tag data to redis: id:{}, idType:{}, tag:{}, time:{}";
    public static final String INSERT_DB_FOR_USER_TAG                     = "Insert user tag data to db# {}";
    public static final String UPDATE_DB_FOR_USER_TAG                     = "Update user tag data to db# {}";
    public static final String DELETE_DB_FOR_USER_TAG                     = "Delete user tag data from db# {}";
    public static final String INSERT_DB_FOR_USER_DEVICE                  = "Insert user device data to db# {}";
    public static final String INSERT_DB_FOR_MEMBER_OP_HIS                = "Insert member operation history data to db# {}";
    public static final String INSERT_REDIS_FOR_ID_RELATION               = "Insert id relation data to redis: childId:{}, childIdType:{}, parentId:{}, parentIdType:{},time:{}";
    public static final String INSERT_DB_FOR_ID_RELATION                  = "Insert id relation data to db# {}";
    public static final String UPDATE_DB_FOR_ID_RELATION                  = "Update id relation data to db# {}";
    public static final String DELETE_DB_FOR_ID_RELATION                  = "Delete id relation data from db# {}";
    public static final String INSERT_REDIS_FOR_ID_RELATION_HISTORY       = "Insert id relation history data to redis: parentId:{}, parentIdType:{}, childId:{}, childIdType:{}, bindingStatus:{}, time:{}";
    public static final String INSERT_DB_FOR_ID_RELATION_HISTORY          = "Insert id relation history data to db# {}";
    public static final String UPDATE_DB_FOR_ID_RELATION_HISTORY          = "Update id relation history data to db# {}";
    public static final String DELETE_DB_FOR_ID_RELATION_HISTORY          = "Delete id relation history data to db# {}";
    public static final String UPDATE_DB_FOR_EXTUSERSMAPPING              = "Update extusersmapping data to db# {}";
    public static final String INSERT_DB_FOR_USER_DYNAMIC_TAG             = "Insert user dynamic tag data to db# {}";
    public static final String UPDATE_DB_FOR_USER_DYNAMIC_TAG             = "Update user dynamic tag data to db# {}";
    public static final String DELETE_DB_FOR_USER_DYNAMIC_TAG             = "Delete user dynamic tag data from db# {}";
    public static final String INSERT_OR_UPDATE_DB_FOR_USER_DYNAMIC_TAG   = "Insert or update user dynamic tag data to db# {}";
    public static final String INSERT_DB_FOR_OPENID2RECVMOBILE            = "Insert openid2receivermobile data to db# {}";
    public static final String UPDATE_DB_FOR_OPENID2RECVMOBILE            = "Update openid2receivermobile data to db# {}";
    public static final String DELETE_DB_FOR_OPENID2RECVMOBILE            = "Delete openid2receivermobile data from db: {}";
    public static final String INSERT_DB_FOR_RECVMOBILE2OPENID            = "Insert receivermobile2openid data to db# {}";
    public static final String INSERT_DB_FOR_OPENID2GUID                  = "Insert openid2guid data to db# {}";
    public static final String INSERT_DB_FOR_GUID2OPENID                  = "Insert guid2openid data to db# {}";
    public static final String INSERT_DB_FOR_MOBILE2GUID                  = "Insert mobile2guid data to db# {}";
    public static final String DELETE_DB_FOR_MOBILE2GUID                  = "Delete mobile2guid data from db# {}";
    public static final String INSERT_DB_FOR_GUID2MOBILE                  = "Insert guid2mobile data to db# {}";
    public static final String UPDATE_DB_FOR_GUID2MOBILE                  = "Update guid2mobile data to db# {}";

    // vipcardinfo
    public static final String INSERT_DB_FOR_VIPCARDINFO                  = "Insert vipCardInfo data to db# {}";
    public static final String UPDATE_DB_FOR_VIPCARDINFO                  = "Update vipCardInfo data to db# {}";
    public static final String DELETE_DB_FOR_VIPCARDINFO                  = "Delete vipCardInfo data to db# {}";

    //vipcardorderinfo
    public static final String INSERT_DB_FOR_VIPCARDORDERINFO             = "Insert vipCardOrderInfo data to db# {}";
    public static final String UPDATE_DB_FOR_VIPCARDORDERINFO             = "Update vipCardOrderInfo data to db# {}";
    public static final String DELETE_DB_FOR_VIPCARDORDERINFO             = "Delete vipCardOrderInfo data to db# {}";
    //vipcardopshisrory
    public static final String INSERT_DB_FOR_VIPCARDOPSHISTORY            = "Insert vipCardOpsHistory data to db# {}";
    //格瓦拉和娱票儿映射实体
    public static final String INSERT_DB_FOR_YUPIAO2GEWARA_IDMAP          = "Insert yp2GwrIdMap data to db# {}";
    public static final String INSERT_DB_FOR_GEWARA2YUPIAO_IDMAP          = "Insert gwr2YpIdMap data to db# {}";
    public static final String UPDATE_DB_FOR_GEWARA2YUPIAO_IDMAP          = "update gwr2YpIdMap data to db# {}";

    /** Warning 和 Error 消息 **/
    public static final String NO_REQ_ID                                  = "RequestId is missing!";
    public static final String NO_MEMBER_ID                               = "MemberId is missing!";
    public static final String NO_GEWARA_USER_ID                          = "Gewara UserId or mobileNo is missing!";
    public static final String NO_DEVICE_INFO                             = "DeviceCode or DeviceType is missing!";
    public static final String NO_UNION_ID                                = "Union ID is missing!";
    public static final String NO_OPEN_ID                                 = "OpenID is missing!";
    public static final String NO_OTHER_ID                                = "OtherID is missing!";
    public static final String NO_ID                                      = "The attribute id is missing!";
    public static final String NO_ID_TYPE                                 = "The attribute idType is missing!";
    public static final String NO_TAG_ID                                  = "The attribute tag is missing!";
    public static final String NO_TAG_VAL                                 = "The attribute tagval is missing!";
    public static final String NO_TAG_LIST                                = "The attribute tag list is missing!";
    public static final String NO_MOBILE_NO                               = "Mobile phone number is missing!";
    public static final String NO_MOVIE_ID                                = "The attribute movieId is missing!";
    public static final String NO_ACTIVITY_ID                             = "The attribute activityId is missing!";
    public static final String NO_RECV_PHONE                              = "The attribute receiverPhoneNum is missing!";
    public static final String NO_NEW_MOBILE_NO                           = "New Mobile phone number is missing!";
    public static final String NO_OLD_MOBILE_NO                           = "Old Mobile phone number is missing!";
    public static final String NO_PASSWORD                                = "The attribute password is missing!";
    public static final String NO_NEW_PASSWORD                            = "The attribute newpassword is missing!";
    public static final String NO_OLD_PASSWORD                            = "The attribute oldpassword is missing!";
    public static final String NO_USER_IP                                 = "User IP is missing!";
    public static final String NO_OPTYPE                                  = "The attribute opType is missing!";
    public static final String ERR_OPTYPE                                 = "Wrong operation type：{}";
    public static final String NO_FROM_MEMBERID                           = "The attribute fromMemberId is missing";
    public static final String NO_TO_MEMBERID                           = "The attribute toMemberId is missing";
    public static final String NO_CINEMA_ID                               = "The attribute cinema id is missing!";
    public static final String NO_CODEC_TEXT                              = "Codec text is missing!";
    public static final String NO_ACCESS_TOKEN                            = "Access-Token is missing!";
    public static final String NO_OAUTH_CONSUMER_KEY                      = "Oauth-Consumer-Key is missing!";
    public static final String OPENID_NUM_EXCEED                          = "openId list size exceed the limitation!";
    public static final String OPENID_AND_UNIONID_DUPLICATED              = "The atttibute unionId has the same value as openId!";
    public static final String ERR_TAG_VAL_NUMERIC                        = "The attribute tagVal should be a number!";
    public static final String ERR_TAG_VAL_NUMERIC_DETAIL                 = "The attribute tagVal: {} should be a number!";
    public static final String ERR_CODEC_MODE                             = "Wrong codec mode!";
    public static final String ENCODE_FAILED                              = "Failed to encode, pls check the encoded text";
    public static final String DECODE_FAILED                              = "Failed to decode, pls check the decoded text";
    public static final String CHARSET_UNSUPPORTED                        = "Charset {} not supported,caused by: {}";
    public static final String ERR_USER_TAG_NOTIFICATION                  = "Wrong user tag notification：{},caused by: {}";
    public static final String ERR_OTHER_ID_TYPE                          = "Wrong OtherID enumation：{}";
    public static final String ERR_SUB_OTHER_ID_TYPE                      = "Wrong SubOtherID enumation：{}";
    public static final String ERR_USER_SOURCE_TYPE                       = "Wrong UserSource enumation：{}";
    public static final String ERR_DEVICE_ID_TYPE                         = "Wrong Device Id Type enumation：{}";
    public static final String ERR_DEVICE_ID_AMOUNT                       = "Wrong amount of user device info!";
    public static final String ERR_OP_TYPE                                = "Wrong OP Type enumation：{}";
    public static final String ERR_INTERNAL_SERVICE_TYPE                  = "Wrong Internal Service Type enumation：{}";
    public static final String ERR_USER_TAG_TYPE                          = "Wrong user tag enumation：{}";
    public static final String ERR_TICKETS_CONSUMPTION_COUNT              = "Wrong TicketsConsumptionCount：{} , shouldn't be negative";
    //	public static final String REQ_ID_FORMAT_ERR = "Wrong Request-Id format!";
    public static final String MOBILE_NO_FORMAT_ERR                       = "Wrong mobile phone number format!";
    public static final String MOBILE_NO_FORMAT_ERR_DETAIL                = "Wrong mobile phone number: {} format!";
    public static final String JSON_PARSE_FAILED                          = "Failed to parse json!";
    public static final String JSON_PARSE_FAILED_DETAIL                   = "Failed to parse json:{}";
    public static final String HTTP_METHOD_FORBIDDEN                      = "Method is forbidden(request wrong HTTP method or API)";
    public static final String HTTP_REQ_TIMEOUT                           = "HTTP Req time out!";
    public static final String URL_NOT_FOUND                              = "Url is not found!";
    public static final String MEDIA_TYPE_NOT_VALID                       = "Media type is invalid!";
    //	public static final String UID_NOT_FOUND = "UID is not found!";
    public static final String MEMBER_ID_NOT_FOUND_DETAIL                 = "MemberId: {} is not found!";
    public static final String OPEN_ID_NOT_REGISTERED                     = "OpenId {} is not registered!";
    public static final String MOBILE2GUID_NOT_FOUND                      = "Mobile2GUID data with this mobile number: {} is not found!";
    //	public static final String OPEN_ID_NOT_FOUND = "OpenID is not found!";
    public static final String OPEN_ID_NOT_FOUND_DETAIL                   = "OpenID:{} is not found!";
    public static final String OPEN_ID_ILLEGAL                            = "OpenID is illegal!";
    public static final String OPEN_ID_ILLEGAL_DETAIL                     = "OpenID {} OtherID {} is illegal!";
    //	public static final String CINEMA_ID_NOT_SAVED = "Cinema id is not in favorite list!";
    public static final String CINEMA_ID_NOT_SAVED_DETAIL                 = "Cinema id:{} is not in favorite list!";
    public static final String CINEMA_ID_NUM_EXCEED                       = BASE_HEADER
                                                                                  + "Cinema favorite list exceed the limitation,the max value permitted is {}";
    public static final String DUPLICATED_CINEMA_ID                       = "The same cinema id is already in favorite list";
    public static final String DUPLICATED_CINEMA_ID_DETAIL                = "The same cinema id:{} is already in favorite list";
    public static final String CINEMA_LIST_EMPTY                          = "Favorite list is already empty!";
    public static final String OPENID2UID_NOT_FOUND                       = "The relation OpenID-UID is not existed!";
    public static final String MOBILE2UID_NOT_FOUND                       = "The mobile phone number : {} is not existed!";
    public static final String MOBILE2GWRUID_NOT_BIND                     = "The mobile phone number : {} is not bind the userId!";
    public static final String OCCUPIED_MOBILE_BINDING                    = "The mobile phone number is already bound by another user!";
    public static final String OCCUPIED_MOBILE_BINDING_DETAIL             = "The mobile phone number:{} is already bound by another user:{}";
    public static final String DUPLICATED_MOBILE_BINDING                  = "The mobile phone number is already bound by current user!";
    public static final String DUPLICATED_MOBILE_BINDING_DETAIL           = "The mobile phone number:{} is already bound by current user!";
    public static final String DUPLICATED_MOBILE_REGISTRY                 = "The mobile phone number is already registed by current user!";
    public static final String DUPLICATED_MOBILE_REGISTRY_DETAIL          = "The mobile phone number:{} is already registed by current user!";
    //	public static final String DUPLICATED_PLATFORM_BINDING = "The mobile phone number is already bound by the same platform!";
    public static final String DUPLICATED_PLATFORM_BINDING_DETAIL         = "The mobile phone number:{} is already bound by the same platform!";
    public static final String ERR_OLD_MOBILE                             = "The old mobile phone number is wrong!";
    public static final String ERR_OLD_MOBILE_DETAIL                      = "The old mobile phone number: {} is wrong!";
    public static final String OPEN_ID_ALREADY_UNBOUND                    = "The id is already unbound!";
    public static final String OPEN_ID_ALREADY_UNBOUND_DETAIL             = "The openid:{},otherId:{} is already unbound!";
    public static final String DUPLICATED_BLACK_ADD                       = "The user is already in the black list!";
    public static final String DUPLICATED_BLACK_ADD_DETAIL                = "The user:{} is already in the black list!";
    public static final String ERR_BLACK_REMOVE                           = "The user is not in the black list, can not removed";
    public static final String ERR_BLACK_REMOVE_DETAIL                    = "The user:{} is not in the black list, can not removed";
    public static final String PASSWORD_IS_NULL_IN_DB                     = "Password is null in db!";
    public static final String PASSWORD_NOT_NULL_IN_DB                    = "Password is not null in db!";
    public static final String PASSWORD_VERIFY_FAILED                     = "Wrong password!";
    public static final String PASSWORD_VERIFY_FAILED_DETAIL              = "The mobile:{} Wrong password:{}";
    public static final String OLD_PASSWORD_VERIFY_FAILED                 = "Wrong old password!";
    public static final String OLD_PASSWORD_VERIFY_FAILED_DETAIL          = "Wrong old password:{}";
    public static final String USER_MOBILENO_NOT_THE_SAME_ONE             = "User's mobile no {} and request mobile no {} are not the same one during reset password!";
    public static final String JEDIS_CONNECT_FAILED                       = "Failed to connect to Redis!";
    public static final String DB_CONNECT_FAILED                          = "Failed to connect to DB!";
    public static final String PARENT_ID_OCCUPIED                         = "The Child Id {} is already bound to Parent ID {} with the the same level as the new one {}!";
    public static final String NO_SERVICE_TYPE                            = "The attribute serviceType is missing!";
    //	public static final String EXTUIDPASSWORD_VERIFY_FAILED_DETAIL = "The user:{} Wrong password:{}";
    //	public static final String EXTUIDPASSWORD_VERIFY_FAILED = "ExtuidMapping Wrong password!";
    public static final String ERR_USER_DYNAMIC_TAG_NOTIFICATION          = "Wrong user dynamic tag notification：{},caused by: {}";

    public static final String ERR_GENDER_TYPE                            = "Wrong Gender enumation：{}";
    public static final String ERR_STRING2INTEGER                         = "Wrong string can not parse int:{}";
    public static final String ERR_STRING2DATE                            = "Wrong string can not parse date:{}";
    public static final String ERR_GEN_MEMBER_ID                          = "Error happend on generate member id,member id is null!";
    public static final String EMPTY_TAG_LIST                             = "The attribute tag list have empty tag!";

    public static final String UPDATE_MOBILE_REACHED_LIMIT                = "The memberId: {} update mobile reached limit count!";
    public static final String BIND_OPENID_REACHED_LIMIT                  = "The openId: {} openId bind reached limit count!";
    public static final String BIND_MEMBERID_REACHED_LIMIT                = "The memberId: {} memberId bind reached limit count!";
    public static final String UNBIND_OPENID_REACHED_LIMIT                = "The openId: {} openId unbind reached limit count!";
    public static final String UNBIND_MEMBERID_REACHED_LIMIT              = "The memberId: {} memberId unbind reached limit count!";

    public static final String UNBIND_RECORD                              = "Unbind record ,The memberId: {} ,The openid:{} ";

    public static final String MISS_PARAM                                 = "Missing param! openId and phone are all null!";

    public static final String MOBILE_LOGIN_FAIL_MORE                     = "The mobile phone number: {} login fail many times!";
    public static final String MEMBER_UPDATE_PW_FAIL_MORE                 = "The memberId: {} update password failed many times!";

    public static final String RUSH_MOBILE_SUSPENDED_BY_TIANYU            = "The mobile phone number: {} with openid: {} with ip: {} is suspended in operation {} by TianYu Security!";
    public static final String RUSH_MOBILE_SUSPENDED_BY_WEIYING           = "The mobile phone number: {} is suspended by WeiYing Security!";
    
    public static final String UNSUPPORTED_DEVICE_TYPE                    = "Unsupported device type!";
    public static final String MEMBER_ID_DUPLICATED                       = "The toMemberId is the duplicated one of the fromMemberId!";
    public static final String OPERATE_REFUSE                             = "requestId: {} ,the operate be refuse!";


    //会员卡信息提示
    public static final String NO_CARD_NO                                 = "CardNo is missing!";
    public static final String NO_ORDER_ID                                = "OrderID is missing!";
    public static final String NO_START_COUNT_DATE                        = "StartCountDate is missing!";
    public static final String NO_END_COUNT_DATE                          = "EndCountDate is missing!";
    public static final String NO_EXPIRE_TIME                             = "ExpireTime is missing!";
    public static final String NO_LOCKED_NUM                              = "LockNum is missing!";
    public static final String NO_TOTAL_INVENTORY                         = "Total Inventory is missing!";
    public static final String NO_FREQUENCY_LIMITATION                    = "Frequency limitation is missing!";
    public static final String ERR_CARD_TYPE                              = "Wrong Card Type enumation：{}";
    public static final String ERR_SUB_CARD_TYPE                          = "Wrong Sub Card Type enumation：{}";
    public static final String DUPLICATED_VIP_CARD_NO_BINDING_OWN         = "The VIP card No {} is already bound by current user!";
    public static final String DUPLICATED_VIP_CARD_NO_BINDING_OTHER       = "The VIP card No {} is already bound by another user!";
    public static final String DUPLICATED_VIP_CARD_TYPE_BINDING           = "The VIP card type {} is already bound by current user!";
    public static final String CARDS_NOT_FOUND                            = "requestId: {} ,Any VIP Card belonging to openId: {} is not found!";
    public static final String CARD_OPERATION_DENY                        = "The VIP card No {} operation is deny";
    public static final String CARD_DATA_ERROR                            = "The VIP card No {} data is error";
    public static final String MOBILE_CARD_MISMATCH                       = "The mobileNo {} and VIP card No {} is mismatch";
    public static final String NO_OLD_CARD_NO                             = "Old cardNo is missing!";
    public static final String DUPLICATED_OLD_VIP_CARD_NO_NOT_BINDING_OWN = "The old VIP card No {} is not bound by current user!";
    public static final String NEW_CARD_TYPE_MISMATCH                     = "The old and new VIP card type is mismatch, card No {}";
    public static final String DATE_FORMAT_ERROR                          = "The Date format is error!";

    //操作受限
    public static final String VIP_CARD_OPERATE_REFUSE                    = "requestId: {} ,the operate be refuse!";
    //会员卡绑定关系错误
    public static final String VIP_CARD_BIND_ERROR                        = "requestId: {} ,card bind error,openid {},cardNo {}";
    //总库存不足
    public static final String VIP_CARD_TOTAL_INVENTORY_SHORTAGE          = "requestId: {} ,card total inventory shortage,openid {},cardNo {}";
    //时段内库存不足
    public static final String VIP_CARD_TIME_INVENTORY_SHORTAGE           = "requestId: {} ,card time inventory shortage,openid {},cardNo {}";
    //记录已经存在
    public static final String VIP_CARD_RECORD_EXISTED                    = "requestId: {} ,record is existed,orderId {}";
    //锁定记录未找到
    public static final String VIP_CARD_ORDER_NOT_FOUND_ERROR             = "requestId: {} ,order lock not found error,openid {},orderId {}";
    //订单锁定状态不匹配
    public static final String VIP_CARD_ORDER_LOCK_STATUS_ERROR           = "requestId: {} ,order lock status not match,status is {} openid {},orderId {}";
    //入参卡号不匹配
    public static final String VIP_CARD_ORDER_CARD_NO_ERROR               = "requestId: {} ,order card no error,openid {},orderId {},cardNo {}";

    public static final String NO_SIGN_TIMESTAMP                          = "Sign timestamp is missing!";
    public static final String NO_SIGN_CONTENT                            = "Sign content is missing, no need to check sign!";
    public static final String SIGN_TIMESTAMP_TIMEOUT                     = "Sign timestamp is timeout!";
    public static final String WRONG_SIGN_CONTENT                         = "Sign content is wrong!";
    public static final String NO_SIGN_ROLE_4_URI                         = "Sign Role is empty for uri: {}!";
    public static final String WRONG_SIGN_ROLE_4_URI                      = "Sign Role is wrong for uri: {}, the correct roles should be: {}!";

    // 私有vip
    public static final String INSERT_DB_FOR_PRIVATE_VIP_CONFIG           = "Insert privateVipConfig data to db# {}";
    public static final String UPDATE_DB_FOR_PRIVATE_VIP_CONFIG           = "Update privateVipConfig data to db# {}";
    public static final String INSERT_DB_FOR_PRIVATE_VIP_INFO_HISTORY     = "Insert privateVipInfoHistory data to db# {}";
    public static final String UPDATE_DB_FOR_PRIVATE_VIP_INFO_HISTORY     = "Update privateVipInfoHistory data to db# {}";
    //私有vip参数vipId丢失
    public static final String PRIVATE_VIP_NO_VIPID                       = "Private Vip VipId is miss!";
    //私有vip参数vipId不合法
    public static final String PRIVATE_VIP_ILLEGAL_VIPID                  = "Private Vip VipId is illegal!";
    //不存在的vipId
    public static final String PRIVATE_VIP_NOT_EXISTED_VIPID              = "Private Vip VipId is not existed!";
    //私有vip参数生效开始时间或结束时间丢失
    public static final String PRIVATE_VIP_NO_START_END_DATE              = "Private Vip startDate or endDate is miss!";
    //私有vip参数日期格式非法
    public static final String PRIVATE_VIP_DATE_FORMAT_ERROR              = "Private Vip startDate or endDate format is error";
    //私有vip参数手机号丢失或格式异常
    public static final String PRIVATE_VIP_MOBILE_MISS_FORMAT_ERROR       = "Private Vip mobileNo is miss or format is error";
    //私有vip手机号memberId与openid的memberId不对应
    public static final String PRIVATE_VIP_MEMBERID_NOT_MATCH             = "Private Vip memberId not match!";
    //私有vip参数vip级别丢失
    public static final String PRIVATE_VIP_NO_LEVEL                       = "Private Vip level is miss!";
    //私有vip参数小图标丢失
    public static final String PRIVATE_VIP_NO_ICON                        = "Private Vip icon is miss!";
    //私有vip参数描述丢失
    public static final String PRIVATE_VIP_NO_DESC                        = "Private Vip description is miss!";
    //私有vip可用记录未找到
    public static final String PRIVATE_VIP_NOT_FOUND_AVAILABLE_VIP_RECORD = "Private Vip the mobileNo not found available vip record!";
    //私有vip已存在可用的记录
    public static final String PRIVATE_VIP_EXISTED_AVAILABLE_VIP_RECORD   = "Private Vip the mobileNo existed available vip record!";

    /**
     * 外部渠道打通(包含M站)
     */
    public static final String NO_EXT_USER_ID                             = "External user id is missing!";
    public static final String NO_CHANNEL_ID                              = "External channelId is missing!";
    public static final String MOBILE_NO_AND_EXT_USER_ID_NOT_SAME         = "Mobile no and extUserId is not the same!";

    /**
     * 收货地址
     */
    public static final String ERR_CITY_TYPE                              = "Wrong City Type enumation：{}";
    public static final String ERR_SOURCE_CODE                            = "Wrong Source Code enumation：{}";
    public static final String DELIVERY_ADDR_NOT_FOUND                    = "User delivery Address is not found!";
    public static final String DELIVERY_ADDR_DELETE_REFUSE                = "user delivery address is only one delete is refuse";
    public static final String NO_NATIONAL_CODE                           = "nationalCode is missing!";
    public static final String NO_SOURCE_CODE                             = "sourceCode is missing!";
    public static final String NO_PROVINCE_ID                             = "provinceId is missing!";
    public static final String NO_CITY_ID                                 = "cityId is missing!";
    public static final String NO_DISTRICT_ID                             = "districtId is missing!";
    public static final String NO_DETAILED_ADDRESS                        = "detailed address is missing!";
    public static final String DETAILED_ADDRESS_TOO_LONG                  = "detailed address is too long!";
    public static final String NO_RECEIVER                                = "receiver is missing!";
    public static final String RECEIVER_TOO_LONG                          = "receiver is too long!";
    public static final String NO_RECEIVER_MOBILE                         = "receiver mobile is missing!";
    public static final String RECEIVER_MOBILE_TOO_LONG                   = "receiver mobile is too long!";
    public static final String CITY_TYPE_ERROR                            = "nationalCode type is error";
    public static final String DETAILED_ADDRESS_COUNT_IS_LIMIT            = "detailed address count is limit!";
    public static final String EXISTS_EMOJI                               = "params exists emoji!";

    public static final String GUSERID_LOGIN_FAIL_MORE                    = "The gewara userId : {} login fail many times!";
    public static final String GWR2YPID_NOT_FOUND                         = "The gewara userId : {} is not existed!";
    
    /**
     *  Tencent黑库
     */
    public static final String TENCENT_BLACKLIB_SERVICE_INVALID           = "The Tencent black lib service is invalid.";
    public static final String TENCENT_BLACKLIB_OP_FAILED                 = "The Tencent black lib operation : {} is failed.";
    public static final String TENCENT_BLACKLIB_STATUS_FAILED             = "The Tencent black lib response is failed with status : {}.";
    public static final String TENCENT_BLACKLIB_ECHOSTR_CHECK_FAILED      = "The Tencent black lib response echostr mismatch.";

}
