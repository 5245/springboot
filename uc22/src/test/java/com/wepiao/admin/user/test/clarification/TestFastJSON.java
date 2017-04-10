package com.wepiao.admin.user.test.clarification;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import org.apache.commons.collections.CollectionUtils;

import com.alibaba.fastjson.JSON;

public class TestFastJSON {
    public static void main(String[] args) {
        User usr0 = new User();
        usr0.setMobileNo("139000000");
        usr0.setType(User.MOBILE.CMCC);
        System.out.println(JSON.toJSONString(usr0));
        String object = "{\"mobileNo\": \"19191919\", \"i\":\"\", \"test\":\"test\"}";
        User u = JSON.parseObject(object, User.class);
        String object1 = "{\"mobileNo\": \"1eeee\", \"test\":\"eee\"}";
        User u1 = JSON.parseObject(object1, User.class);
        BaseResArray res = new BaseResArray(1234567);
        res.getResponseParams().add(u);
        res.getResponseParams().add(u1);
        System.out.println(res);
        HashSet<String> mergedCinemaSet = new HashSet<String>();
        String mergedCinema = "";
        String cinemaOfUser = "11";
        String cinemaOf3rdPartyUser = "22,44,55";
        String removed = "99";
        String[] cinemaArrayOfUser = cinemaOfUser.split(",");
        String[] cinemaArrayOf3rdPartyUser = cinemaOf3rdPartyUser.split(",");
        CollectionUtils.addAll(mergedCinemaSet, cinemaArrayOfUser);
        CollectionUtils.addAll(mergedCinemaSet, cinemaArrayOf3rdPartyUser);
        System.out.println(mergedCinemaSet.contains(removed));
        Iterator<String> i = mergedCinemaSet.iterator();
        while (i.hasNext()) {
            mergedCinema += i.next() + ",";
        }
        System.out.println(mergedCinema.substring(0, mergedCinema.length() - 1));
        Random random = new Random();
        //		User usr = new User();
        //		usr.setMobileNo("aaaa");
        //		usr.setPassword("ddddxxx");
        //		System.out.println(JSON.toJSONString(usr));
        System.out.println("asda".equals(null));

        Map<String, User> m = new HashMap<String, User>();
        String key = "h1";
        String xxx = JSON.toJSONString(m);

        Map<String, User> m2 = (Map<String, User>) JSON.parse(xxx);
        System.out.println(m2);

    }

}

class BaseResArray {
    private long            requestId;
    private ArrayList<User> responseParams;

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public ArrayList<User> getResponseParams() {
        return responseParams;
    }

    public BaseResArray(long requestId) {
        super();
        responseParams = new ArrayList<User>();
        this.requestId = requestId;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        Class<?> clazz = this.getClass();
        Field[] fields = clazz.getDeclaredFields();
        sb.append(clazz.getName() + "{");
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                sb.append("\n  " + field.getName() + ":" + field.get(this));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        sb.append("\n}");
        return sb.toString();
    }
}

class User extends Father {
    enum MOBILE {
        CNC(1), CMCC(2);
        int i;

        MOBILE(int i) {
            this.i = i;
        }
    }

    MOBILE type;

    public MOBILE getType() {
        return type;
    }

    public void setType(MOBILE type) {
        this.type = type;
    }

    String mobileNo;

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    String password;
    int    i;

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public User() {
    }

    public User(String mobileNo, String password) {
        super();
        this.mobileNo = mobileNo;
        this.password = password;
    }

}

class Father {
    public Father() {
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        Class<?> clazz = this.getClass();
        Field[] fields = clazz.getDeclaredFields();
        sb.append(clazz.getName() + "{");
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                sb.append("\n  " + field.getName() + ":" + field.get(this));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        sb.append("\n}");
        return sb.toString();
    }
}
