package com.wepiao.admin.user.rest.controller;

import io.swagger.annotations.Api;

import java.util.Map;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.wepiao.admin.user.rest.msg.BaseResWrapper;
import com.wepiao.user.common.constant.LogMsg;
import com.wepiao.user.common.service.SystemStatusService;

@Api(value = "/jedispoolmonitor")
@Path("/jedispoolmonitor")
public class SystemJedisPoolController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(SystemJedisPoolController.class);

    @Autowired
    private SystemStatusService systemStatusService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getsystemjedispoolstatus")
    public Response getQueuesSize() {
        Map<String, Map<String, Integer>> resObj = systemStatusService.getSystemJedisPollStatus();
        String res = BaseResWrapper.wrapToJSONString(resObj);
        logger.debug(LogMsg.WS_RES_NO_REQ_ID, res);
        return Response.ok().entity(res).build();
    }

}
