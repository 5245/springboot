package com.wepiao.admin.user.rest.msg;

public class CodecRes extends BaseRes {
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public CodecRes(String text) {
        super();
        this.text = text;
    }

}
