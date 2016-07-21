package com.sxk.enumeration.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.sxk.enumeration.Gender;

/**
 * 自定义枚举Status转换类
 * JDBCType：Integer
 * @author jin song
 */
public class EnumGenderHandler extends BaseTypeHandler<Gender> {

    public EnumGenderHandler() {
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Gender parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getIntVal());

    }

    @Override
    public Gender getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int i = rs.getInt(columnName);
        if (rs.wasNull()) {
            return null;
        } else {
            // 根据数据库中的值，定位Gender子类
            return locateEnum(i);
        }
    }

    @Override
    public Gender getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int i = rs.getInt(columnIndex);
        if (rs.wasNull()) {
            return null;
        } else {
            // 根据数据库中的值，定位Gender子类
            return locateEnum(i);
        }
    }

    @Override
    public Gender getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int i = cs.getInt(columnIndex);
        if (cs.wasNull()) {
            return null;
        } else {
            // 根据数据库中的值，定位Gender子类
            return locateEnum(i);
        }
    }

    /**
     * 枚举类型转换，由于构造函数获取了枚举的子类enums，让遍历更加高效快捷
     * @param enumVal 数据库中存储的自定义enumVal属性
     * @return enumVal对应的枚举类
     */
    private Gender locateEnum(int enumVal) {
        for (Gender gender : Gender.class.getEnumConstants()) {
            if (gender.getIntVal() == enumVal) {
                return gender;
            }
        }
        throw new IllegalArgumentException("未知的性别枚举类型：" + enumVal + ",请核对" + Gender.class.getSimpleName());
    }
}
