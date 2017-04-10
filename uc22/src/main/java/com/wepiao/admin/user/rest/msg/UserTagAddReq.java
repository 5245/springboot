package com.wepiao.admin.user.rest.msg;

public class UserTagAddReq extends BaseReq {
    /**
     * 用户id
     */
    private String  id;
    /**
     * 用户的id类型
     */
    private int     idType;
    /**
     * 用户的标签
     */
    private String  tag;
    /**
     * 用户的标签值
     */
    private String  tagVal;
    /** 
     * 标签值的数据同步模式（0:对tagVal覆盖， 1:对tagVal的做数值累加）默认是0
     */
    private int     syncMode    = 0;
    /**
     * 是否需要入库，默认值为true（需要入库）
     */
    private boolean needPersist = true;
    /**
     * 标签的过期时刻，为EPOCH的偏移量，单位为秒
     */
    private long    cacheExpire;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

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

    public int getSyncMode() {
        return syncMode;
    }

    public void setSyncMode(int syncMode) {
        this.syncMode = syncMode;
    }

    public boolean isNeedPersist() {
        return needPersist;
    }

    public void setNeedPersist(boolean needPersist) {
        this.needPersist = needPersist;
    }

    public long getCacheExpire() {
        return cacheExpire;
    }

    public void setCacheExpire(long cacheExpire) {
        this.cacheExpire = cacheExpire;
    }

    public UserTagAddReq() {
        super();
    }

    public UserTagAddReq(String id, int idType, String tag, String tagVal, int syncMode, boolean needPersist, long cacheExpire) {
        super();
        this.id = id;
        this.idType = idType;
        this.tag = tag;
        this.tagVal = tagVal;
        this.syncMode = syncMode;
        this.needPersist = needPersist;
        this.cacheExpire = cacheExpire;
    }
}
