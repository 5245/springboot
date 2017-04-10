package com.sxk.model;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "user_index", type = "user", shards = 5, replicas = 1, indexStoreType = "fs", refreshInterval = "-1")
//@Document(indexName = "user_index", type = "user", shards = 1, replicas = 0)
public class ESUser {
    @Id
    private String id;
    private String userName;
    private String password;
    private String mobileNo;
    private int    age;
    private String email;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ESUser() {
        super();
    }

    public ESUser(String id, String userName, String password, String mobileNo, int age, String email) {
        super();
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.mobileNo = mobileNo;
        this.age = age;
        this.email = email;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
