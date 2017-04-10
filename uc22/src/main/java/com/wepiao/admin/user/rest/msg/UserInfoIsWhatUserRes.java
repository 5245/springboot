package com.wepiao.admin.user.rest.msg;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询用户标签的结果
 * @author Jin Song
 *
 */
public class UserInfoIsWhatUserRes extends BaseRes {
    private List<TagMap> result;

    public List<TagMap> getResult() {
        return result;
    }

    public void setResult(List<TagMap> result) {
        this.result = result;
    }

    public void addTagResult(String tag, String tagVal) {
        if (null == result) {
            result = new ArrayList<TagMap>();
        }
        result.add(new TagMap(tag, tagVal));
    }

    public UserInfoIsWhatUserRes() {
    }

    public UserInfoIsWhatUserRes(List<TagMap> result) {
        super();
        this.result = result;
    }
}

class TagMap {
    private String tag;
    private String tagVal;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTagVal() {
        return tagVal;
    }

    public void setTagVal(String tagVal) {
        this.tagVal = tagVal;
    }

    public TagMap() {
    }

    public TagMap(String tag, String tagVal) {
        super();
        this.tag = tag;
        this.tagVal = tagVal;
    }
}
