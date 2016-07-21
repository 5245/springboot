package com.sxk.constants;

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
    public static final String BASE_HEADER                              = "{}-requestId: {} ";

    /** 基本消息模板**/
    public static final String BASE_MSG                                 = "requestId: {} - {}";
    public static final String BASE_MSG_NO_REQ_ID                       = "Internal API Request(No RequestId): {}";

    /** debug 消息 **/
    public static final String WS_REQ_NO_REQ_ID                         = "HTTP Request Body# {}";
    public static final String WS_RES_NO_REQ_ID                         = "HTTP Response Body# {}";
    public static final String WS_REQ                                   = BASE_HEADER + WS_REQ_NO_REQ_ID;
    public static final String WS_RES                                   = BASE_HEADER + WS_RES_NO_REQ_ID;

    /** info 消息 **/
    public static final String INSERT_DB_FOR_MOBILE2UID                 = "Insert user mobile number mapping data to db# {}";
    public static final String DELETE_DB_FOR_MOBILE2UID                 = "delete user mobile number mapping data to db# {}";
    public static final String INSERT_DB_FOR_USER                       = "Insert user data to db# {}";
    public static final String UPDATE_DB_FOR_USER                       = "Update user data to db# {}";
    public static final String INSERT_DB_FOR_OPENID                     = "Insert openid data to db# {}";
    public static final String UPDATE_DB_FOR_OPENID                     = "Update openid data to db# {}";
    public static final String DELETE_DB_FOR_OPENID                     = "Delete openid data from db: {}";
    public static final String INSERT_DB_FOR_USER_DELIVERY_ADDR         = "Insert user delivery address data to db# {}";
    public static final String UPDATE_DB_FOR_USER_DELIVERY_ADDR         = "Update user delivery address data to db# {}";
    public static final String DELETE_DB_FOR_USER_DELIVERY_ADDR         = "Delete user delivery address data from db: {}";
    public static final String INSERT_REDIS_FOR_USER_TAG                = "Insert user tag data to redis: id:{}, idType:{}, tag:{}, time:{}";
    public static final String INSERT_DB_FOR_USER_TAG                   = "Insert user tag data to db# {}";
    public static final String UPDATE_DB_FOR_USER_TAG                   = "Update user tag data to db# {}";
    public static final String DELETE_DB_FOR_USER_TAG                   = "Delete user tag data from db# {}";
    public static final String INSERT_DB_FOR_USER_DEVICE                = "Insert user device data to db# {}";
    public static final String INSERT_REDIS_FOR_ID_RELATION             = "Insert id relation data to redis: childId:{}, childIdType:{}, parentId:{}, parentIdType:{},time:{}";
    public static final String INSERT_DB_FOR_ID_RELATION                = "Insert id relation data to db# {}";
    public static final String UPDATE_DB_FOR_ID_RELATION                = "Update id relation data to db# {}";
    public static final String DELETE_DB_FOR_ID_RELATION                = "Delete id relation data from db# {}";
    public static final String INSERT_REDIS_FOR_ID_RELATION_HISTORY     = "Insert id relation history data to redis: parentId:{}, parentIdType:{}, childId:{}, childIdType:{}, bindingStatus:{}, time:{}";
    public static final String INSERT_DB_FOR_ID_RELATION_HISTORY        = "Insert id relation history data to db# {}";
    public static final String UPDATE_DB_FOR_ID_RELATION_HISTORY        = "Update id relation history data to db# {}";
    public static final String UPDATE_DB_FOR_EXTUSERSMAPPING            = "Update extusersmapping data to db# {}";
    public static final String INSERT_DB_FOR_USER_DYNAMIC_TAG           = "Insert user dynamic tag data to db# {}";
    public static final String UPDATE_DB_FOR_USER_DYNAMIC_TAG           = "Update user dynamic tag data to db# {}";
    public static final String DELETE_DB_FOR_USER_DYNAMIC_TAG           = "Delete user dynamic tag data from db# {}";
    public static final String INSERT_OR_UPDATE_DB_FOR_USER_DYNAMIC_TAG = "Insert or update user dynamic tag data to db# {}";

    /** Warning 和 Error 消息 **/
    public static final String NO_REQ_ID                                = "RequestId is missing!";
    public static final String NO_MEMBER_ID                             = "MemberId is missing!";
    public static final String NO_UNION_ID                              = "Union ID is missing!";
    public static final String NO_OPEN_ID                               = "OpenID is missing!";
    public static final String NO_OTHER_ID                              = "OtherID is missing!";
    public static final String NO_ID                                    = "The attribute id is missing!";
    public static final String NO_ID_TYPE                               = "The attribute idType is missing!";
    public static final String NO_TAG_ID                                = "The attribute tag is missing!";
    public static final String NO_TAG_VAL                               = "The attribute tagval is missing!";
    public static final String NO_TAG_LIST                              = "The attribute tag list is missing!";
    public static final String NO_MOBILE_NO                             = "Mobile phone number is missing!";
    public static final String NO_NEW_MOBILE_NO                         = "New Mobile phone number is missing!";
    public static final String NO_OLD_MOBILE_NO                         = "Old Mobile phone number is missing!";
    public static final String NO_PASSWORD                              = "The attribute password is missing!";
    public static final String NO_NEW_PASSWORD                          = "The attribute newpassword is missing!";
    public static final String NO_OLD_PASSWORD                          = "The attribute oldpassword is missing!";
    public static final String NO_USER_IP                               = "User IP is missing!";
    public static final String NO_OPTYPE                                = "The attribute opType is missing!";
    public static final String ERR_OPTYPE                               = "Wrong operation type：{}";
    public static final String NO_CINEMA_ID                             = "The attribute cinema id is missing!";
    public static final String NO_CODEC_TEXT                            = "Codec text is missing!";
    public static final String NO_ACCESS_TOKEN                          = "Access-Token is missing!";
    public static final String NO_OAUTH_CONSUMER_KEY                    = "Oauth-Consumer-Key is missing!";
    public static final String ERR_TAG_VAL_NUMERIC                      = "The attribute tagVal should be a number!";
    public static final String ERR_TAG_VAL_NUMERIC_DETAIL               = "The attribute tagVal: {} should be a number!";
    public static final String ERR_CODEC_MODE                           = "Wrong codec mode!";
    public static final String ENCODE_FAILED                            = "Failed to encode, pls check the encoded text";
    public static final String DECODE_FAILED                            = "Failed to decode, pls check the decoded text";
    public static final String CHARSET_UNSUPPORTED                      = "Charset {} not supported,caused by: {}";
    public static final String ERR_USER_TAG_NOTIFICATION                = "Wrong user tag notification：{},caused by: {}";
    public static final String ERR_OTHER_ID_TYPE                        = "Wrong OtherID enumation：{}";
    public static final String ERR_DEVICE_ID_TYPE                       = "Wrong Device Id Type enumation：{}";
    public static final String ERR_INTERNAL_SERVICE_TYPE                = "Wrong Internal Service Type enumation：{}";
    public static final String ERR_USER_TAG_TYPE                        = "Wrong user tag enumation：{}";
    public static final String MOBILE_NO_FORMAT_ERR                     = "Wrong mobile phone number format!";
    public static final String MOBILE_NO_FORMAT_ERR_DETAIL              = "Wrong format found in mobile phone number: {} !";
    public static final String JSON_PARSE_FAILED                        = "Failed to parse json!";
    public static final String JSON_PARSE_FAILED_DETAIL                 = "Failed to parse json:{}";
    public static final String HTTP_METHOD_FORBIDDEN                    = "Method is forbidden(request wrong HTTP method or API)";
    public static final String HTTP_REQ_TIMEOUT                         = "HTTP Req time out!";
    public static final String URL_NOT_FOUND                            = "Url is not found!";
    public static final String MEDIA_TYPE_NOT_VALID                     = "Media type is invalid!";
    public static final String MEMBER_ID_NOT_FOUND_DETAIL               = "MemberId: {} is not found!";
    public static final String DELIVERY_ADDR_NOT_FOUND                  = "User delivery Address is not found!";
    public static final String OPEN_ID_NOT_REGISTERED                   = "OpenId {} is not registered!";
    public static final String OPEN_ID_NOT_FOUND_DETAIL                 = "OpenID:{} is not found!";
    public static final String OPEN_ID_ILLEGAL                          = "OpenID is illegal!";
    public static final String OPEN_ID_ILLEGAL_DETAIL                   = "OpenID {} OtherID {} is illegal!";
    public static final String CINEMA_ID_NOT_SAVED_DETAIL               = "Cinema id:{} is not in favorite list!";
    public static final String CINEMA_ID_NUM_EXCEED                     = BASE_HEADER
                                                                                + "Cinema favorite list exceed the limitation,the max value permitted is {}";
    public static final String DUPLICATED_CINEMA_ID                     = "The same cinema id is already in favorite list";
    public static final String DUPLICATED_CINEMA_ID_DETAIL              = "The same cinema id:{} is already in favorite list";
    public static final String CINEMA_LIST_EMPTY                        = "Favorite list is already empty!";
    public static final String OPENID2UID_NOT_FOUND                     = "The relation OpenID-UID is not existed!";
    public static final String MOBILE2UID_NOT_FOUND                     = "The mobile phone number : {} is not existed!";
    public static final String OCCUPIED_MOBILE_BINDING                  = "The mobile phone number is already bound by another user!";
    public static final String OCCUPIED_MOBILE_BINDING_DETAIL           = "The mobile phone number:{} is already bound by another user:{}";
    public static final String DUPLICATED_MOBILE_BINDING                = "The mobile phone number is already bound by current user!";
    public static final String DUPLICATED_MOBILE_BINDING_DETAIL         = "The mobile phone number:{} is already bound by current user!";
    public static final String DUPLICATED_MOBILE_REGISTRY               = "The mobile phone number is already registed by current user!";
    public static final String DUPLICATED_MOBILE_REGISTRY_DETAIL        = "The mobile phone number:{} is already registed by current user!";
    public static final String DUPLICATED_PLATFORM_BINDING_DETAIL       = "The mobile phone number:{} is already bound by the same platform!";
    public static final String ERR_OLD_MOBILE                           = "The old mobile phone number is wrong!";
    public static final String ERR_OLD_MOBILE_DETAIL                    = "The old mobile phone number: {} is wrong!";
    public static final String OPEN_ID_ALREADY_UNBOUND                  = "The id is already unbound!";
    public static final String OPEN_ID_ALREADY_UNBOUND_DETAIL           = "The openid:{},otherId:{} is already unbound!";
    public static final String DUPLICATED_BLACK_ADD                     = "The user is already in the black list!";
    public static final String DUPLICATED_BLACK_ADD_DETAIL              = "The user:{} is already in the black list!";
    public static final String ERR_BLACK_REMOVE                         = "The user is not in the black list, can not removed";
    public static final String ERR_BLACK_REMOVE_DETAIL                  = "The user:{} is not in the black list, can not removed";
    public static final String PASSWORD_IS_NULL_IN_DB                   = "Password is null in db!";
    public static final String PASSWORD_NOT_NULL_IN_DB                  = "Password is not null in db!";
    public static final String PASSWORD_VERIFY_FAILED                   = "Wrong password!";
    public static final String PASSWORD_VERIFY_FAILED_DETAIL            = "The mobile:{} Wrong password:{}";
    public static final String OLD_PASSWORD_VERIFY_FAILED               = "Wrong old password!";
    public static final String OLD_PASSWORD_VERIFY_FAILED_DETAIL        = "Wrong old password:{}";
    public static final String USER_MOBILENO_NOT_THE_SAME_ONE           = "User's mobile no {} and request mobile no {} are not the same one during reset password!";
    public static final String JEDIS_CONNECT_FAILED                     = "Failed to connect to Redis!";
    public static final String DB_CONNECT_FAILED                        = "Failed to connect to DB!";
    public static final String PARENT_ID_OCCUPIED                       = "The Child Id {} is already bound to Parent ID {} with the the same level as the new one {}!";
    public static final String NO_SERVICE_TYPE                          = "The attribute serviceType is missing!";
    public static final String ERR_USER_DYNAMIC_TAG_NOTIFICATION        = "Wrong user dynamic tag notification：{},caused by: {}";

    public static final String ERR_GENDER_TYPE                          = "Wrong Gender enumation：{}";
    public static final String ERR_STRING2INTEGER                       = "Wrong string can not parse int:{}";
    public static final String ERR_STRING2DATE                          = "Wrong string can not parse date:{}";
    public static final String ERR_GEN_MEMBER_ID                        = "Error happend on generate member id,member id is null!";
    public static final String MOBILE_LOGIN_FAIL_MORE                   = "The mobile phone number: {} login fail many times!";
    public static final String MEMBER_UPDATE_PW_FAIL_MORE               = "The memberId: {} update password failed many times!";

    public static final String RUSH_MOBILE_SUSPENDED_BY_TIANYU          = "The mobile phone number: {} is suspended by TianYu Security!";
    public static final String RUSH_MOBILE_SUSPENDED_BY_WEIYING         = "The mobile phone number: {} is suspended by WeiYing Security!";

    public static final String UPDATE_MOBILE_REACHED_LIMIT              = "The memberId: {} update mobile reached limit count!";
    public static final String BIND_OPENID_REACHED_LIMIT                = "The openId: {} openId bind reached limit count!";
    public static final String BIND_MEMBERID_REACHED_LIMIT              = "The memberId: {} memberId bind reached limit count!";
    public static final String UNBIND_OPENID_REACHED_LIMIT              = "The openId: {} openId unbind reached limit count!";
    public static final String UNBIND_MEMBERID_REACHED_LIMIT            = "The memberId: {} memberId unbind reached limit count!";

}
