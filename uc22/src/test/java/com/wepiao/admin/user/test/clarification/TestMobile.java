package com.wepiao.admin.user.test.clarification;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

public class TestMobile {
    final static String[] PHONENUMBER_PREFIX = { "130", "131", "132", "145", "155", "156", "185", "186", "134", "135", "136", "137", "138", "139",
            "147", "150", "151", "152", "157", "158", "159", "182", "183", "187", "188", "133", "153", "189", "180" };

    /**
     * 匹配手机号码 
     * <p>
     * 新联通</br>
     *   （中国联通+中国网通）手机号码开头数字 130,131,132,145,155,156,185,186</br> 
     * 新移动</br>
     * 　（中国移动+中国铁通）手机号码开头数字</br>
     * 134,135,136,137,138,139,147,150,151,152,157,158,159,182,183,187,188</br> 
     * 新电信</br>
     * 　（中国电信+中国卫通）手机号码开头数字 133,153,189,180</br>
     * </p>
     * @param 手机号码
     * @return  参数为null和不合法时返回false，否则返回true
     */
    public static boolean patternPhoneNumber(String number) {
        if (number != null) {
            Pattern p = Pattern.compile("^[1][0-9]{10}$");
            if (p.matcher(number).matches()) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(patternPhoneNumber("11681356520"));
        System.out.println(patternPhoneNumber("156813565"));
        System.out.println(patternPhoneNumber("1568135652033"));
        String date = "2009-01-2 00:01:02";
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-DD");
        try {
            System.out.println(sf.parse(date));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
