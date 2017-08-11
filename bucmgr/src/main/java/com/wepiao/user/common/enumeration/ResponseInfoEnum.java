package com.wepiao.user.common.enumeration;

/**
 * REST API返回Response的枚举
 * @author Jin Song
 *
 */
public enum ResponseInfoEnum {
    // 正确响应
    CORRECT(0, 0, 200, "success"), //
    FAIL(-1, -1, 200, "failed"),
    // 错误响应
    E10000(-1, -10000, 500, "Internal Server Error 服务器内部错误"), //
    E10001(-1, -10001, 405, "Method Not Allowed 不允许的操作, %s"), //
    E10002(-1, -10002, 400, "Request Params Not Valid 请求参数非法, %s"), //
    E10003(-1, -10003, 403, "Authentication Failed 权限校验错误"), //
    E10004(-1, -10004, 402, "Quota Use Up Payment Required 无quota"), //
    E10005(-1, -10005, 404, "Data Required Not Found 请求数据不存在"), //
    E10006(-1, -10006, 408, "Request Time Expires Timeout 请求已超时"), //
    E10007(-1, -10007, 408, "App Token Timeout appToken已经过期"), //
    E10008(-1, -10008, 409, "Duplicate Operation 重复操作"), //
    E10009(-1, -10009, 404, "Url Required Not Found 请求的Url资源不存在"), //
    E10010(-1, -10010, 400, "Media Type Not Valid 请求的媒体类型(Content-Type)非法"), //
    //20001
    E20001(-1, -20001, 411, "参数格式错误"), //
    E20002(-1, -20002, 412, "手机号码已存在, %s"), //
    E20003(-1, -20003, 412, "手机号码已被当前账号绑定, %s"), //
    E20004(-1, -20004, 412, "手机号码已被其他账号占用, %s"), //
    E20005(-1, -20005, 500, "影院收藏数超过最大限制，最大限制数为%s"), //
    E20006(-1, -20006, 404, "数据链损坏"), //
    E20007(-1, -20007, 500, "数据异常, %s"), //
    E20008(-1, -20008, 404, "手机号码: %s 不存在"), //
    E20009(-1, -20009, 404, "当前账号已被平台id为 %s 的用户绑定过一次"), //
    E20010(-1, -20010, 404, "旧手机号码不一致, %s"), //
    E20011(-1, -20011, 405, "OpenId: %s 未注册"), //
    E20012(-1, -20012, 400, "旧密码验证失败"), //

    E20013(-1, -20013, 400, "手机号码: %s 登录失败次数过多"), //
    E20014(-1, -20014, 400, "用户的注册手机号与请求手机号不一致"), //
    E20015(-1, -20015, 400, "memberId: %s 重置或者修改密码次数过多"), //
    E20016(-1, -20016, 400, "手机被禁止用户相关操作, 原因是: %s"), //
    E20017(-1, -20017, 400, "禁止初始化密码, 原因是:%s"), //

    E20018(-1, -20018, 400, "您%s天内修改手机号码次数已达上限"), //
    E20019(-1, -20019, 400, "您%s天内绑定第三方账号次数已达上限"), //
    E20020(-1, -20020, 400, "您的号码%s天内解绑次数已达上限"), //
    E20021(-1, -20021, 400, "您的收货地址数量已达上限"), //

    E30001(-1, -30001, 412, "会员卡：%s 已被当前账号绑定"), //
    E30002(-1, -30002, 404, "会员卡：%s 未被当前账号绑定"), //
    E30003(-1, -30003, 404, "会员卡：%s 总库存不足"), //
    E30004(-1, -30004, 404, "会员卡：%s 时段内库存不足"), //
    E30005(-1, -30005, 404, "订单号：%s 未找到相关的资格锁定记录"), //
    E30006(-1, -30006, 404, "订单号：%s 当前资格锁定状态不匹配"), //
    E30007(-1, -30007, 404, "订单号：%s 入参卡号错误"), //
    E30008(-1, -30008, 412, "会员卡：%s 已被其他账号绑定"), //
    E30009(-1, -30009, 404, "用户已绑定该会员卡类型：%s "), //
    E30010(-1, -30010, 404, "用户未绑定会员卡"), //
    E30011(-1, -30011, 404, "会员卡：%s 不存在"), //
    E30012(-1, -30012, 404, "手机号：%s 和会员卡号：%s 不匹配"), //
    E30013(-1, -30013, 412, "会员卡：%s 已经消费 %s 次，拒绝操作"), //
    E30014(-1, -30014, 412, "旧会员卡：%s 未被当前账号绑定"), //
    E30015(-1, -30015, 412, "折扣卡：%s 新旧折扣卡类型不一致"), //

    E40001(-1, -40001, 400, "用户未开通私有vip"), //
    E40002(-1, -40002, 400, "用户已开通私有vip"), //

    //50001
    E50001(-1, -50001, 500, "MySQL异常"), //
    E50002(-1, -50002, 500, "Redis异常"), //
    E50003(-1, -50003, 503, "请求超时"), //

    //格瓦拉错误码
    E60001(-1, -60001, 404, "用户未设置密码,%s,请设置密码"), //
    E60002(-1, -60002, 404, "用户: %s 不存在"), //
    E60003(-1, -60003, 500, "用户: %s 登录失败次数过多"), //
    E60004(-1, -60004, 404, "密码错误:%s"), //

    E60005(-1, -60005, 400, "Request Params Not Valid 请求参数非法, Mobile phone number is missing!"), //
    E60006(-1, -60006, 400, "Request Params Not Valid 请求参数非法, The attribute password is missing!"), //
    E60007(-1, -60007, 400, "Request Params Not Valid 请求参数非法, The attribute opType is missing!"), //
    E60008(-1, -60008, 400, "Request Params Not Valid 请求参数非法, Gewara UserId or mobileNo is missing!"), //
    E60009(-1, -60009, 400, "Request Params Not Valid 请求参数非法, Gewara UserId is missing!"), //

    E60010(-1, -60010, 404, "手机号码: %s 不存在"), //
    E60011(-1, -60011, 404, "数据异常，memberId %s 不存在"), //
    E60012(-1, -60012, 500, "密码验证异常"),
    E60013(-1, -60013, 400, "Request Params Not Valid 请求参数非法, deviceCode or deviceType is missing!"),
    E60014(-1, -60014, 400, "Request Params Not Valid 请求参数非法, unsupported device type!"),
    E60015(-1, -60015, 400, "userId没有跟该手机号  %s 绑定!"); //

    /**
     * 未定义的request id
     */
    public static final long UNKNOWN_REQUEST_ID = -1L;
    /**
     * 业务执行结果，正确响应则填写0，否则-1
     */
    private int              ret;
    /**
     * 业务的错误码，正确响应则填写0
     */
    private int              sub;
    /**
     * HTTP状态码，正确响应则填写200
     */
    private int              statusCode;
    /**
     * 业务的错误信息，正确响应则填写‘success’
     */
    private String           msg;

    private ResponseInfoEnum(int ret, int sub, int statusCode, String msg) {
        this.ret = ret;
        this.sub = sub;
        this.statusCode = statusCode;
        this.msg = msg;
    }

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getSub() {
        return sub;
    }

    public void setSub(int sub) {
        this.sub = sub;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 仅用于转换JSON格式
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("{\n");
        sb.append("\"ret\":").append(ret).append(",\n");
        sb.append("\"sub\":").append(sub).append(",\n");
        sb.append("\"msg\":").append("\"").append(msg).append("\"\n");
        sb.append("}");
        return sb.toString();
    }
}
