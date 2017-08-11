package com.wepiao.user.common.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import com.alibaba.fastjson.JSONObject;

/**
 * ClassName: LogUtil <br/>
 * Function: 日志记录工具类 <br/>
 * date: 2017年5月2日 下午4:24:20 <br/>
 *
 * @author liujie
 * @version
 */
public class LogUtil {
	
	/**
	 * errInfo: <br/>
	 * 错误堆栈信息输出 <br/>
	 * @author liujie
	 * @param e
	 * @return
	 */
	public static String errInfo(Exception e) {  
        StringWriter sw = null;  
        PrintWriter pw = null;  
        try {  
            sw = new StringWriter();  
            pw = new PrintWriter(sw);  
            // 将出错的栈信息输出到printWriter中  
            e.printStackTrace(pw);  
            pw.flush();  
            sw.flush();  
        } finally {  
            if (sw != null) {  
                try {  
                    sw.close();  
                } catch (IOException e1) {  
                    e1.printStackTrace();  
                }  
            }  
            if (pw != null) {  
                pw.close();  
            }  
        }  
        return sw.toString();  
    }
	
	/**
	 * getLogJson: <br/>
	 * 获得json格式日志 <br/>
	 * @author liujie
	 * @param reason
	 * @param body
	 * @return
	 */
	public static JSONObject getLogJson(String reason,Object body){
    	JSONObject logMsg = new JSONObject();
    	logMsg.put("reason", reason);
    	logMsg.put("serverIp", NetworkUtils.getLinuxLocalIP());
    	logMsg.put("body", body);
    	return logMsg;
	}
	
	public static JSONObject getMsgLogJson(String msgKey,Object body){
    	JSONObject logMsg = new JSONObject();
    	logMsg.put("serverIp", NetworkUtils.getLinuxLocalIP());
    	logMsg.put("msgkey", msgKey);
    	logMsg.put("body", body);
    	return logMsg;
	}

}
