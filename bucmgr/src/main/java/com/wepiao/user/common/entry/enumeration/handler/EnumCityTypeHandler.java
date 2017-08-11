package com.wepiao.user.common.entry.enumeration.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.wepiao.user.common.entry.enumeration.CityType;

/**
 * @description cityType转类型 
 * @author sxk
 * @date 2016年12月29日
 *
 */
public class EnumCityTypeHandler extends BaseTypeHandler<CityType> {

    public EnumCityTypeHandler() {
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, CityType parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getIntVal());

    }

    @Override
    public CityType getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int i = rs.getInt(columnName);
        if (rs.wasNull()) {
            return null;
        } else {
            // 根据数据库中的值，定位OpType子类
            return locateEnum(i);
        }
    }

    @Override
    public CityType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int i = rs.getInt(columnIndex);
        if (rs.wasNull()) {
            return null;
        } else {
            // 根据数据库中的值，定位OpType子类
            return locateEnum(i);
        }
    }

    @Override
    public CityType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int i = cs.getInt(columnIndex);
        if (cs.wasNull()) {
            return null;
        } else {
            // 根据数据库中的值，定位OpType子类
            return locateEnum(i);
        }
    }

    /**
     * 枚举类型转换，由于构造函数获取了枚举的子类enums，让遍历更加高效快捷
     * @param enumVal 数据库中存储的自定义enumVal属性
     * @return enumVal对应的枚举类
     */
    private CityType locateEnum(int enumVal) {
        for (CityType opType : CityType.class.getEnumConstants()) {
            if (opType.getIntVal() == enumVal) {
                return opType;
            }
        }
        throw new IllegalArgumentException("错误的OpType枚举类型：" + enumVal + ",请核对" + CityType.class.getSimpleName());
    }
}
