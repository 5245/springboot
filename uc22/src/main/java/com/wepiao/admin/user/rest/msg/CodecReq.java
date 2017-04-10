package com.wepiao.admin.user.rest.msg;

public class CodecReq extends BaseReq {
    private String text;
    private int    encode;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getEncode() {
        return encode;
    }

    public void setEncode(int encode) {
        this.encode = encode;
    }

    public CodecReq() {
        super();
    }

    public CodecReq(String text, int encode) {
        super();
        this.text = text;
        this.encode = encode;
    }

}
