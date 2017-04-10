package com.wepiao.admin.user.rest.msg;

import java.util.ArrayList;
import java.util.List;

import com.wepiao.user.common.entry.OpenId;

public class BlackListRetrieveRes extends BaseRes {
    private List<OpenId> OpenIDList;

    public List<OpenId> get_OpenIDList() {
        return OpenIDList;
    }

    public void set_OpenIDList(List<OpenId> openIDList) {
        OpenIDList = openIDList;
    }

    public BlackListRetrieveRes() {
        super();
    }

    public BlackListRetrieveRes(List<OpenId> openIDList) {
        super();
        OpenIDList = openIDList;
    }

    public void addOpenId(String openId, int otherId) {
        if (OpenIDList == null) {
            OpenIDList = new ArrayList<OpenId>();
        }
        OpenId ele = new OpenId(openId, otherId);
        OpenIDList.add(ele);
    }
}
