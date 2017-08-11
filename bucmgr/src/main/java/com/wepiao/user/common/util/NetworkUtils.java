/**
 * Project Name:uc-common
 * File Name:NetworkUtils.java
 * Package Name:com.wepiao.user.common.util
 * Date:2016年10月20日下午4:56:52
 *
*/

package com.wepiao.user.common.util;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * ClassName:NetworkUtils <br/>
 * Function: 网络相关工具类 <br/>
 * Date:     2016年10月20日 下午4:56:52 <br/>
 * @author   liujie
 * @version  
 * @see 	 
 */
public class NetworkUtils {
	
	
	private static String ip = null;
    
    /**
     * getLinuxLocalIP: <br/>
     * linux系统下获取真实ip <br/>
     * @author liujie
     * @return
     */
    public static String getLinuxLocalIP() {
    	if(null != ip){
    		return ip;
    	}
        try {
            Enumeration<?> e1 = (Enumeration<?>) NetworkInterface.getNetworkInterfaces();
            while (e1.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) e1.nextElement();
                if (!ni.getName().equals("eth0")) {
                    continue;
                } else {
                    Enumeration<?> e2 = ni.getInetAddresses();
                    while (e2.hasMoreElements()) {
                        InetAddress ia = (InetAddress) e2.nextElement();
                        if (ia instanceof Inet6Address)
                            continue;
                        ip = ia.getHostAddress();
                    }
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ip;
    }
    
    /**
     * getHostName: <br/>
     * 获取host名称 <br/>
     * @author liujie
     * @return
     */
    public static String getHostName(){
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

}
