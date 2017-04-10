package com.wepiao.admin.user.rest.msg;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wepiao.user.common.enumeration.ResponseInfoEnum;

public class BaseResWrapper {

    /**
     * 构造一般响应消息，无requestId返回
     * @param reqId
     * @param res
     * @return
     */
    public static String wrapToJSONString(Object res) {
        BaseResArray baseResArray = null;
        if (res instanceof SingleResultRes) {
            SingleResultRes newRes = (SingleResultRes) res;
            if (0 == newRes.getResult()) {
                baseResArray = new BaseResArray(ResponseInfoEnum.CORRECT.getRet(), ResponseInfoEnum.CORRECT.getSub(),
                        ResponseInfoEnum.CORRECT.getMsg(), null);
            } else if (1 == newRes.getResult()) {
                baseResArray = new BaseResArray(ResponseInfoEnum.FAIL.getRet(), ResponseInfoEnum.FAIL.getSub(), ResponseInfoEnum.FAIL.getMsg(), null);
            } else {
                // 不存在此case
            }
            return JSON.toJSONString(baseResArray);
        } else {
            baseResArray = new BaseResArray(ResponseInfoEnum.CORRECT.getRet(), ResponseInfoEnum.CORRECT.getSub(), ResponseInfoEnum.CORRECT.getMsg(),
                    res);
            return JSON.toJSONString(baseResArray, SerializerFeature.WriteEnumUsingToString, SerializerFeature.WriteMapNullValue,
                    SerializerFeature.WriteNullStringAsEmpty);
        }
    }

    /**
     * 构造一般的响应消息, 有requestId返回
     * @param reqId
     * @param res
     * @return
     */
    public static String wrapToJSONString(String reqId, Object res) {
        BaseResArrayWithReqId baseResArray = null;
        if (res instanceof SingleResultRes) {
            SingleResultRes newRes = (SingleResultRes) res;
            if (0 == newRes.getResult()) {
                baseResArray = new BaseResArrayWithReqId(ResponseInfoEnum.CORRECT.getRet(), ResponseInfoEnum.CORRECT.getSub(),
                        ResponseInfoEnum.CORRECT.getMsg(), reqId, null);
            } else if (1 == newRes.getResult()) {
                baseResArray = new BaseResArrayWithReqId(ResponseInfoEnum.FAIL.getRet(), ResponseInfoEnum.FAIL.getSub(),
                        ResponseInfoEnum.FAIL.getMsg(), reqId, null);
            } else {
                // 不存在此case
            }
            return JSON.toJSONString(baseResArray);
        } else {
            baseResArray = new BaseResArrayWithReqId(ResponseInfoEnum.CORRECT.getRet(), ResponseInfoEnum.CORRECT.getSub(),
                    ResponseInfoEnum.CORRECT.getMsg(), reqId, res);
            return JSON.toJSONString(baseResArray, SerializerFeature.WriteEnumUsingToString, SerializerFeature.WriteMapNullValue,
                    SerializerFeature.WriteNullStringAsEmpty);
        }
    }
}
