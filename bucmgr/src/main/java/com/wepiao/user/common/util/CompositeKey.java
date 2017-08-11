package com.wepiao.user.common.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 
 * @author sxk
 * @date 2016年12月27日
 */

public class CompositeKey {

    public static class Separator {
        private String separator;

        private Separator(String separator) {
            this.separator = separator;
        }
    }

    public static Separator sep(String separator) {
        return new Separator(separator);
    }

    private List<Object> keyList   = new ArrayList<Object>();

    private Separator    separator = new Separator("-");

    public CompositeKey(Object... keys) {
        if (keys == null) {
            return;
        }
        for (Object key : keys) {
            keyList.add(key);
        }
    }

    public CompositeKey(Separator separator, Object... keys) {
        if (keys == null) {
            return;
        }
        for (Object key : keys) {
            keyList.add(key);
        }
        this.separator = separator;
    }

    public void addKey(Object key) {
        keyList.add(key);
    }

    public String getKey() {
        StringBuilder buf = new StringBuilder();
        for (Object key : keyList) {
            if (buf.length() > 0) {
                buf.append(separator.separator);
            }
            buf.append(key.toString());

        }
        return buf.toString();
    }

    public Object getKey(int index) {
        return keyList.get(index);
    }

    public String getId() {
        return getKey();
    }

    public Object getKey1() {
        return getKey(0);
    }

    public Object getKey2() {
        return getKey(1);
    }

    public Object getKey3() {
        return getKey(3);
    }

    @Override
    public String toString() {
        return getKey();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof CompositeKey))
            return false;
        if (((CompositeKey) obj).getKey().equals(this.getKey())) {
            return true;
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return getKey().hashCode();
    }
}
