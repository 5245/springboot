package com.wepiao.user.common.util;

public class TianYuBaseUtils {
	
	private static final ConfigPropertiesUtil configUtil = ConfigPropertiesUtil.getInstance();
	/* 天御防弊签名url */
    public static final String TIANYU_SIGN_URL = configUtil.get("tianyu.protect.signature.url") + "/v2/index.php";
    
    /* 天御防弊服务url */
    public static final String TIANYU_SERVICE_URL = configUtil.get("tianyu.protect.service.url") + "/v2/index.php";
}
