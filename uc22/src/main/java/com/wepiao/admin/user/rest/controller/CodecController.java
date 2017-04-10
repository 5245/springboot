package com.wepiao.admin.user.rest.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wepiao.admin.user.rest.msg.BaseResWrapper;
import com.wepiao.admin.user.rest.msg.CodecReq;
import com.wepiao.admin.user.rest.msg.CodecRes;
import com.wepiao.user.common.constant.LogMsg;
import com.wepiao.user.common.enumeration.ResponseInfoEnum;
import com.wepiao.user.common.rest.exception.BaseRestException;
import com.wepiao.user.common.util.Base64Utils;
import com.wepiao.user.common.util.TEAUtils;

/**
 * 
 * @author jin Song
 * 该调用不需要Request-Id
 *
 */
@Path("/codec")
public class CodecController extends BaseController {
    private static final Logger logger  = LoggerFactory.getLogger(CodecController.class);

    /**
     * 加密模式
     */
    private static final int    ENCRYPT = 0;

    /**
     * 解密模式
     */
    private static final int    DECRYPT = 1;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Deprecated
    public Response codec(String req) {
        logger.debug(LogMsg.WS_REQ_NO_REQ_ID, req);
        CodecReq reqJava = parseObject(req, CodecReq.class);
        checkParam(reqJava);

        // Base64编解码，encode=0为编码，1为解码
        CodecRes resObj = null;
        if (reqJava.getEncode() == ENCRYPT) {
            resObj = encode(reqJava.getText());
        } else if (reqJava.getEncode() == DECRYPT) {
            resObj = decode(reqJava.getText());
        } else {
            logger.warn(LogMsg.ERR_CODEC_MODE);
            throw new BaseRestException(ResponseInfoEnum.E10002, LogMsg.ERR_CODEC_MODE);
        }

        String res = BaseResWrapper.wrapToJSONString(resObj);
        logger.debug(LogMsg.WS_RES_NO_REQ_ID, res);
        return Response.ok().entity(res).build();
    }

    private void checkParam(CodecReq req) {
        if (req.getText() == null || req.getText().length() == 0) {
            logger.warn(LogMsg.NO_CODEC_TEXT);
            throw new BaseRestException(ResponseInfoEnum.E10002, LogMsg.NO_CODEC_TEXT);
        }
    }

    private CodecRes encode(String text) {
        try {
            byte[] encodedAES;
            encodedAES = TEAUtils.encryptByTea(text);
            String encodedBase64 = Base64Utils.encode(encodedAES);
            return new CodecRes(new String(encodedBase64));
        } catch (Throwable th) {
            logger.warn(LogMsg.ENCODE_FAILED, th);
            throw new BaseRestException(ResponseInfoEnum.E10002, LogMsg.ENCODE_FAILED);
        }

    }

    private CodecRes decode(String text) {
        try {
            byte[] decodedBase64 = Base64Utils.decode(text);
            String decodedAES = TEAUtils.decryptByTea(decodedBase64);
            return new CodecRes(decodedAES);
        } catch (Throwable th) {
            logger.warn(LogMsg.DECODE_FAILED, th);
            throw new BaseRestException(ResponseInfoEnum.E10002, LogMsg.DECODE_FAILED);
        }
    }
}
