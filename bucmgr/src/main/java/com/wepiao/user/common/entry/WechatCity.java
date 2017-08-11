package com.wepiao.user.common.entry;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.wepiao.user.common.entry.enumeration.CityType;

/**
 * @description  微信城市代码--13年国标
 * @author sxk
 * @date 2016年12月27日
 */
public class WechatCity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer           id;
    private String            nationalCode;
    private CityType          cityType;
    private String            name;
    private String            parentCode;
    private String            zipCode;
    private Integer           provinceId;
    private Integer           cityId;
    private Integer           districtId;
    private String            districtIdOther;

    public WechatCity() {
    }

    public WechatCity(String nationalCode, CityType cityType, String name, String parentCode) {
        super();
        this.nationalCode = nationalCode;
        this.cityType = cityType;
        this.name = name;
        this.parentCode = parentCode;
    }

    public WechatCity(Integer id, String nationalCode, CityType cityType, String name, String parentCode, String zipCode, Integer provinceId,
            Integer cityId, Integer districtId, String districtIdOther) {
        super();
        this.id = id;
        this.nationalCode = nationalCode;
        this.cityType = cityType;
        this.name = name;
        this.parentCode = parentCode;
        this.zipCode = zipCode;
        this.provinceId = provinceId;
        this.cityId = cityId;
        this.districtId = districtId;
        this.districtIdOther = districtIdOther;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public CityType getCityType() {
        return cityType;
    }

    public void setCityType(CityType cityType) {
        this.cityType = cityType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public String getDistrictIdOther() {
        return districtIdOther;
    }

    public void setDistrictIdOther(String districtIdOther) {
        this.districtIdOther = districtIdOther;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
